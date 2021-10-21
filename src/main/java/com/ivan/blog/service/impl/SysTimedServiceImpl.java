package com.ivan.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ivan.blog.dao.SysTimedMapper;
import com.ivan.blog.model.SysTimed;
import com.ivan.blog.service.SysTimedService;
import org.springframework.stereotype.Service;

@Service
public class SysTimedServiceImpl extends ServiceImpl<SysTimedMapper, SysTimed> implements SysTimedService {

}
