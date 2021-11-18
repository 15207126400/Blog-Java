package com.ivan.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ivan.blog.entity.BlogAccount;
import com.ivan.blog.mapper.BlogAccountMapper;
import com.ivan.blog.service.BlogAccountService;
import org.springframework.stereotype.Service;

/**
 * @author Ivan
 * @date 2021/11/13 19:12
 * @Description
 */
@Service
public class BlogAccountServiceImpl extends ServiceImpl<BlogAccountMapper, BlogAccount> implements BlogAccountService {
}
