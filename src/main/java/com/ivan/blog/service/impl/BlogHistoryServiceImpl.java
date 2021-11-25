package com.ivan.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ivan.blog.entity.BlogHistory;
import com.ivan.blog.mapper.BlogHistoryMapper;
import com.ivan.blog.service.BlogHistoryService;
import org.springframework.stereotype.Service;

/**
 * @author Ivan
 * @date 2021/11/25 09:19
 * @Description
 */
@Service
public class BlogHistoryServiceImpl extends ServiceImpl<BlogHistoryMapper, BlogHistory> implements BlogHistoryService {

}
