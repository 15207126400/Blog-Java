package com.ivan.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ivan.blog.model.SysUser;
import com.ivan.blog.model.dto.SysUserDTO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysUserMapper extends BaseMapper<SysUser> {
    //@Select("select * from sys_user where username = #{username} and password = #{password}")
    SysUser userLogin(SysUser user);

    @Select("select * from sys_user where username = #{username}")
    SysUser findUserByName(String username);

    @Select("select name from sys_role RIGHT JOIN(SELECT role_id from sys_user_role RIGHT JOIN(SELECT * from sys_user where username=#{username}) b on b.id = sys_user_role.user_id) c on c.role_id = sys_role.id")
    List<String> findRolesByUsername(String username);

    @Select("select u.*,r.name role_name from sys_user u left join sys_user_role ur on u.id = ur.user_id left join sys_role r on ur.role_id = r.id")
    List<SysUserDTO> list();

    int deleteByPrimaryKey(Integer id);

    int insert(SysUserDTO record);

    int insertSelective(SysUserDTO record);

    SysUserDTO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUserDTO record);

    int updateByPrimaryKey(SysUserDTO record);
}