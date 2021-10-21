package com.ivan.blog.config;

import com.ivan.blog.dao.*;
import javax.annotation.Resource;
import com.ivan.blog.model.SysRolePermission;
import com.ivan.blog.model.SysUser;
import com.ivan.blog.model.SysUserRole;
import com.ivan.blog.service.SysUserService;
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
import java.util.List;

/*
 *  @Author: Ivan
 *  @Description:
 *  @Date: 2019/10/31 23:04
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
        String username = (String)token.getPrincipal();
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        SysUser userInfo = userService.findUserByName(username);
        if(userInfo == null){
            return null;
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo, //用户名
                userInfo.getPassword(), //密码
                getName()  //realm name
        );

        //判断密码 ---- 加密匹配,设置盐，用来核对密码
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(userInfo.getCredentialsSalt()));

        return authenticationInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        SysUser userinfo = (SysUser)principals.getPrimaryPrincipal();
        int user_id = userinfo.getId();
        List<SysUserRole> list = userRoleMapper.getRoles(user_id);
        for(SysUserRole userrole:list){
            int rolid = userrole.getRoleId();
            authorizationInfo.addRole(roleMapper.selectByPrimaryKey(rolid).getName());
            List<SysRolePermission> list1 = rolePermissionMapper.getPermissions(rolid);
            for(SysRolePermission p:list1){
                authorizationInfo.addStringPermission(permissionMapper.selectByPrimaryKey(p.getPermissionId()).getPermission());
            }
        }
        return authorizationInfo;
    }

}