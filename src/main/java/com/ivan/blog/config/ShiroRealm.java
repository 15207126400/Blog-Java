package com.ivan.blog.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ivan.blog.entity.SysRole;
import com.ivan.blog.entity.SysRolePermission;
import com.ivan.blog.entity.SysUser;
import com.ivan.blog.entity.SysUserRole;
import com.ivan.blog.mapper.SysPermissionMapper;
import com.ivan.blog.mapper.SysRoleMapper;
import com.ivan.blog.mapper.SysRolePermissionMapper;
import com.ivan.blog.mapper.SysUserRoleMapper;
import com.ivan.blog.service.SysUserService;
import com.ivan.blog.utils.SystemUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Ivan
 * @Description:
 * @Date: 2019/10/31 23:04
 */
@Slf4j
public class ShiroRealm extends AuthorizingRealm {

    @Resource
    private SysUserService userService;
    @Resource
    private SysRoleMapper roleMapper;
    @Resource
    private SysUserRoleMapper userRoleMapper;
    @Resource
    private SysRolePermissionMapper rolePermissionMapper;
    @Resource
    private SysPermissionMapper permissionMapper;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        SysUser userInfo = userService.findUserByName(username);
        if (userInfo == null) {
            return null;
        }

        ByteSource credentialsSalt = ByteSource.Util.bytes(username + SystemUserUtil.getCredentialsSalt());
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo, //用户名
                userInfo.getPassword(), //密码
                credentialsSalt, //盐值
                getName()  //realm name
        );

        return authenticationInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        SysUser userinfo = (SysUser) principals.getPrimaryPrincipal();
        SysUserRole userRole = userRoleMapper.selectOne(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userinfo.getId()));
        SysRole role = roleMapper.selectById(userRole.getRoleId());
        authorizationInfo.addRole(role.getName());
        List<SysRolePermission> rolePermissionList = rolePermissionMapper.selectList(new LambdaQueryWrapper<SysRolePermission>().eq(SysRolePermission::getRoleId, userRole.getRoleId()));
        for (SysRolePermission rolePermission : rolePermissionList) {
            authorizationInfo.addStringPermission(permissionMapper.selectById(rolePermission.getPermissionId()).getPermission());
        }
        return authorizationInfo;
    }


}