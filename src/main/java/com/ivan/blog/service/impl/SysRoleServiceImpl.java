package com.ivan.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.ImmutableMap;
import com.ivan.blog.entity.SysPermission;
import com.ivan.blog.entity.SysRole;
import com.ivan.blog.entity.SysRolePermission;
import com.ivan.blog.entity.tool.TreeModel;
import com.ivan.blog.mapper.SysPermissionMapper;
import com.ivan.blog.mapper.SysRoleMapper;
import com.ivan.blog.mapper.SysRolePermissionMapper;
import com.ivan.blog.service.SysRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *  @Author: Ivan
 *  @Description:
 *  @Date: 2019/10/31 09:38
 */
@Service
@AllArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysPermissionMapper sysPermissionMapper;
    private final SysRolePermissionMapper sysRolePermissionMapper;

    @Override
    public List<TreeModel> getTreeByRole() {
        List<SysPermission> sysPermissionList = sysPermissionMapper.selectList(Wrappers.emptyWrapper());

        //分组过滤一级节点数据 (去重查询出权限类型 用户管理,角色管理等等)
        Map<String, List<SysPermission>> permissionMap = sysPermissionList.stream().collect(Collectors.groupingBy(SysPermission::getType));

        //二级节点数据
        List<SysPermission> secondNode = sysPermissionMapper.selectList(Wrappers.emptyWrapper());
        List<TreeModel> tree = new ArrayList<>();

        //遍历M
        for (Map.Entry<String, List<SysPermission>> entry : permissionMap.entrySet()) {
            TreeModel mainNode = new TreeModel();
            mainNode.setText(entry.getKey());
            mainNode.setState(ImmutableMap.of("checked", false));
            for (SysPermission r : secondNode) {//循环第二级节点，给第一级节点添加子节点
                if ((mainNode.getText()).equals(r.getType())) {//比较第二级节点的父节点id是不是当前父节点的id
                    TreeModel childNode = new TreeModel();
                    childNode.setTags(r.getId());
                    childNode.setText(r.getName());
                    childNode.setState(ImmutableMap.of("checked", false));
                    childNode.setNodes(null);
                    mainNode.getNodes().add(childNode);
                }
            }
            if (mainNode.getNodes().size() == 0)
                mainNode.setNodes(null);
            tree.add(mainNode);
        }

        return tree;
    }

    @Override
    public List<TreeModel> getTreeByRole(String roleId) {
        List<SysPermission> sysPermissionList = sysPermissionMapper.selectList(Wrappers.emptyWrapper());

        //分组过滤一级节点数据 (去重查询出权限类型 用户管理,角色管理等等)
        Map<String, List<SysPermission>> permissionMap = sysPermissionList.stream().collect(Collectors.groupingBy(SysPermission::getType));

        //二级节点数据
        List<SysPermission> secondNode = sysPermissionMapper.selectList(Wrappers.emptyWrapper());
        List<TreeModel> tree = new ArrayList<>();

        //遍历
        for (Map.Entry<String, List<SysPermission>> entry : permissionMap.entrySet()) {
            TreeModel mainNode = new TreeModel();
            mainNode.setText(entry.getKey());
            //叠加器
            int num = 0;
            for (SysPermission r : secondNode) {
                if ((mainNode.getText()).equals(r.getType())) {
                    TreeModel childNode = new TreeModel();
                    childNode.setTags(r.getId());
                    childNode.setText(r.getName());
                    //赋值勾选状态
                    if (sysPermissionMapper.findPermissionByUrl(r.getUrl(), roleId) != null) {
                        childNode.setState(ImmutableMap.of("checked", true));
                        num++;
                    } else {
                        childNode.setState(ImmutableMap.of("checked", false));
                    }
                    childNode.setNodes(null);
                    mainNode.getNodes().add(childNode);
                }
                //设置一级节点是否勾选
                if (num > 0) {
                    mainNode.setState(ImmutableMap.of("checked", true));
                } else {
                    mainNode.setState(ImmutableMap.of("checked", false));
                }
            }
            if (mainNode.getNodes().size() == 0) mainNode.setNodes(null);
            tree.add(mainNode);
        }

        return tree;
    }

    /**
     * 新增
     *
     * @param sysRole
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertByRoleId(SysRole sysRole) {
        boolean bol = baseMapper.insert(sysRole) > 0;
        if (bol) {
            String permissionIds = sysRole.getPermissionIds();
            batchInsertRolePermissionByRole(sysRole.getId(), permissionIds);
        }

        return bol;
    }

    /**
     * 修改
     *
     * @param sysRole
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateByRoleId(SysRole sysRole) {
        boolean bol = baseMapper.updateById(sysRole) > 0;
        if (bol) {
            //清除原关联权限
            batchDeleteRolePermissionByRole(sysRole.getId());

            //新增关联权限
            String permissionIds = sysRole.getPermissionIds();
            batchInsertRolePermissionByRole(sysRole.getId(), permissionIds);
        }

        return bol;
    }

    /**
     * 删除
     *
     * @param roleId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByRoleId(Integer roleId) {
        boolean bol = baseMapper.deleteById(roleId) > 0;
        if (bol) {
            batchDeleteRolePermissionByRole(roleId);
        }

        return bol;
    }


    //根据角色id,删除所有角色权限关联信息
    private void batchDeleteRolePermissionByRole(Integer roleId) {
        List<SysRolePermission> rolePermissionList = sysRolePermissionMapper.selectList(new LambdaQueryWrapper<SysRolePermission>().eq(SysRolePermission::getRoleId, roleId));
        for (SysRolePermission rolePermission : rolePermissionList) {
            sysRolePermissionMapper.deleteById(rolePermission.getId());
        }
    }

    //根据角色id,新增所有角色权限关联信息
    private void batchInsertRolePermissionByRole(Integer roleId, String str) {
        List<SysRolePermission> rolePermissionList = new ArrayList<>();

        String[] ids = str.split(",");
        //构造数据
        for (String id : ids) {
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setPermissionId(Integer.valueOf(id));
            rolePermissionList.add(sysRolePermission);
        }
        sysRolePermissionMapper.insertBatch(rolePermissionList, roleId);
    }
}
