package com.ivan.blog.service.impl;

import com.ivan.blog.dao.SysRoleMapper;
import com.ivan.blog.model.SysPermission;
import com.ivan.blog.model.SysRole;
import com.ivan.blog.model.SysRolePermission;
import com.ivan.blog.model.tool.TreeModel;
import com.ivan.blog.service.SysPermissionService;
import com.ivan.blog.service.SysRolePermissionService;
import com.ivan.blog.service.SysRoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 *  @Author: Ivan
 *  @Description:
 *  @Date: 2019/10/31 09:38
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysPermissionService sysPermissionService;
    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    @Override
    public List<SysRole> list() {
        return sysRoleMapper.list();
    }

    @Override
    public List<TreeModel> getTreeByRole() {
        Object result[] = new Object[1];
        //一级节点数据 (去重查询出权限类型 用户管理,角色管理等等)
        List<SysPermission> firstNode = sysPermissionService.listByDeWeight();

        //二级节点数据
        List<SysPermission> secondNode = sysPermissionService.list();
        List<TreeModel> tree = new ArrayList<TreeModel>();

        for(int i = 0; i < firstNode.size(); i++) {                     //循环给第一级节点添加子节点
            TreeModel node0 = new TreeModel();
            node0.setText(firstNode.get(i).getType());
            //设置一级节点是否勾选
            Map<String, Object> firstNodeMap = new HashMap<>();
            firstNodeMap.put("checked",false);
            node0.setState(firstNodeMap);
            for(SysPermission r : secondNode) {                      //循环第二级节点，给第一级节点添加子节点
                if((node0.getText()).equals (r.getType())) {                //比较第二级节点的父节点id是不是当前父节点的id
                    TreeModel node1 = new TreeModel();
                    node1.setTags(r.getId());
                    node1.setText(r.getName());
                    Map<String, Object> secondNodeMap = new HashMap<>();
                    secondNodeMap.put("checked",false);
                    node1.setState(secondNodeMap);
                    node1.setNodes(null);
                    node0.getNodes().add(node1);
                }
            }
            if(node0.getNodes().size()==0)
                node0.setNodes(null);
            tree.add(node0);
        }
        return tree;
    }

    @Override
    public List<TreeModel> getTreeByRole(String role_id) {
        //JSONObject result = new JSONObject();
        //一级节点数据 (去重查询出权限类型 用户管理,角色管理等等)
        List<SysPermission> firstNode = sysPermissionService.listByDeWeight();

        //二级节点数据
        List<SysPermission> secondNode = sysPermissionService.list();
        List<TreeModel> tree = new ArrayList<TreeModel>();

        for(int i = 0; i < firstNode.size(); i++) {
            TreeModel node0 = new TreeModel();
            node0.setText(firstNode.get(i).getType());

            //叠加器
            int num = 0;
            for(SysPermission r : secondNode) {
                if((node0.getText()).equals (r.getType())) {
                    TreeModel node1 = new TreeModel();
                    node1.setTags(r.getId());
                    node1.setText(r.getName());
                    //赋值勾选状态
                    if(sysPermissionService.findPermissionByUrl(r.getUrl(),role_id) != null){
                        Map<String, Object> secondNodeMap = new HashMap<>();
                        secondNodeMap.put("checked",true);
                        node1.setState(secondNodeMap);
                        num++;
                    } else {
                        Map<String, Object> secondNodeMap = new HashMap<>();
                        secondNodeMap.put("checked",false);
                        node1.setState(secondNodeMap);
                    }
                    node1.setNodes(null);
                    node0.getNodes().add(node1);
                }
                //设置一级节点是否勾选
                Map<String, Object> firstNodeMap = new HashMap<>();
                if(num > 0){
                    firstNodeMap.put("checked",true);
                    node0.setState(firstNodeMap);
                } else {
                    firstNodeMap.put("checked",false);
                    node0.setState(firstNodeMap);
                }
            }

            if(node0.getNodes().size()==0) node0.setNodes(null);
            tree.add(node0);
        }
        return tree;
    }

    @Override
    @Transactional
    public int deleteByPrimaryKey(Integer id) {
        batchDeleteRolePermissionByRole(id);

        return sysRoleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SysRole record) {
        return sysRoleMapper.insert(record);
    }

    @Override
    @Transactional
    public int insertSelective(SysRole record) {
        if(StringUtils.isNotEmpty(record.getPermission_ids())){
            batchInsertRolePermissionByRole(record.getId(),record.getPermission_ids());
        }
        return sysRoleMapper.insertSelective(record);
    }

    @Override
    public SysRole selectByPrimaryKey(Integer id) {
        return sysRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public int updateByPrimaryKeySelective(SysRole record) {
        if(StringUtils.isNotEmpty(record.getPermission_ids())){
            //遍历删除该角色之前关联的权限
            batchDeleteRolePermissionByRole(record.getId());

            //批量新增新的权限与角色关联关系
            batchInsertRolePermissionByRole(record.getId(),record.getPermission_ids());
        } else {
            //遍历删除该角色之前关联的权限
            batchDeleteRolePermissionByRole(record.getId());
        }

        return sysRoleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysRole record) {
        return sysRoleMapper.updateByPrimaryKey(record);
    }

    //根据角色id,删除所有角色权限关联信息
    public void batchDeleteRolePermissionByRole(Integer role_id){
        List<SysRolePermission> rolePermissions = sysRolePermissionService.findPermissionsByRole(role_id);
        for(SysRolePermission rp : rolePermissions){
            sysRolePermissionService.deleteByPrimaryKey(rp.getId());
        }
    }

    //根据角色id,新增所有角色权限关联信息
    public void batchInsertRolePermissionByRole(Integer role_id , String str){
        String[] ids = str.split(",");
        SysRolePermission sysRolePermission = new SysRolePermission();

        //批量新增新的权限与角色关联关系
        for(String id : ids){
            sysRolePermission.setPermissionId(Integer.valueOf(id));
            sysRolePermission.setRoleId(role_id);
            sysRolePermissionService.insert(sysRolePermission);
        }
    }
}
