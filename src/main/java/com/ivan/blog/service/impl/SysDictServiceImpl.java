package com.ivan.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ivan.blog.entity.SysDict;
import com.ivan.blog.mapper.SysDictMapper;
import com.ivan.blog.service.SysDictService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  @Author: Ivan
 *  @Description:
 *  @Date: 2019/11/16 17:03
 */
@Service
@AllArgsConstructor
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper,SysDict> implements SysDictService {

    private final SysDictMapper sysDictMapper;

    /**
     * 根据typeCode查询字典项
     * @param type
     * @return
     */
    @Override
    public List<SysDict> findListByType(int type) {
        return sysDictMapper.selectList(new LambdaQueryWrapper<SysDict>().eq(SysDict::getTypeCode, type));
    }

}
