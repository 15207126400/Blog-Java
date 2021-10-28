package com.ivan.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ivan.blog.entity.SysDict;

import java.util.List;

/**
 *  @Author: Ivan
 *  @Description:
 *  @Date: 2019/11/16 17:03
 */
public interface SysDictService extends IService<SysDict> {

    List<SysDict> findListByType(int type);

}
