package com.ivan.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ivan.blog.model.SysDict;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface SysDictMapper extends BaseMapper<SysDict> {
    @Select("select * from sys_dict")
    List<SysDict> list();

    @Select("select * from sys_dict where type_code = #{type_code}")
    List<SysDict> findListByType(int type);
}