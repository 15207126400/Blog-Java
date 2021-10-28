package com.ivan.blog.service.impl;

import com.ivan.blog.Exception.BizException;
import com.ivan.blog.entity.tool.EmailModel;
import com.ivan.blog.service.MailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 *  @Author: Ivan
 *  @Description:
 *  @Date: 2019/12/17 21:16
 */
@Service
@Slf4j
@AllArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    private static final String USERNAME = "15207126400@163.com";

    @Override
    public void sendSimpleMail(EmailModel emailModel){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(USERNAME);
            message.setTo(emailModel.getTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());
            if(emailModel.getCc() != null){
                message.setCc(emailModel.getCc());
            }
            mailSender.send(message);
        } catch (Exception e) {
            throw new BizException("邮件发送异常: " + e.getMessage());
        }
    }
}
