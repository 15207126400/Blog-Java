package com.ivan.blog.service;

import com.ivan.blog.model.tool.EmailModel;

/**
 * 邮件发送
 */
public interface MailService {

    void sendSimpleMail(EmailModel emailModel);
}
