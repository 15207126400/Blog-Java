package com.ivan.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ivan.blog.dao.SysDictMapper;
import com.ivan.blog.model.SysDict;
import com.ivan.blog.service.SysDictService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/*
 *  @Author: Ivan
 *  @Description:
 *  @Date: 2019/11/16 17:03
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper,SysDict> implements SysDictService {

    @Resource
    private SysDictMapper sysDictMapper;

    @Override
    public List<SysDict> list() {
        return sysDictMapper.list();
    }

    @Override
    public List<SysDict> findListByType(int type) {
        return sysDictMapper.findListByType(type);
    }

}
