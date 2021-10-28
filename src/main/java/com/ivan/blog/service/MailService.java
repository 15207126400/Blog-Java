package com.ivan.blog.service;

import com.ivan.blog.entity.tool.EmailModel;

/**
 * 邮件发送
 */
public interface MailService {

    void sendSimpleMail(EmailModel emailModel);
}
