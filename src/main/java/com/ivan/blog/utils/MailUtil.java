package com.ivan.blog.utils;

import com.ivan.blog.Exception.BizException;
import lombok.extern.slf4j.Slf4j;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/*
 *  @Author: Ivan
 *  @Description:   邮件发送工具
 *  @Date: 2019/12/28 19:32
 */
@Slf4j
public class MailUtil {

    private static final String HOST = "smtp.163.com";
    private static final String USERNAME = "15207126400@163.com";
    private static final String PASSWORD = "qinoupp918";

    public static void sendMail(String subject, String text, String toUsername){
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        //smtp服务器地址
        props.put("mail.smtp.host",HOST);

        Session session = Session.getInstance(props);
        //邮件日志打印
        //session.setDebug(true);

        try {
            Message msg = new MimeMessage(session);
            msg.setSubject(subject);
            msg.setText(text);
            //发件人邮箱(我的163邮箱)
            msg.setFrom(new InternetAddress(USERNAME));
            //收件人邮箱(我的QQ邮箱)
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toUsername));
            msg.saveChanges();

            Transport transport = session.getTransport();
            //发件人邮箱,授权码(可以在邮箱设置中获取到授权码的信息)
            transport.connect(USERNAME,PASSWORD);
            transport.sendMessage(msg, msg.getAllRecipients());

            log.info("邮件发送成功!");
            transport.close();

        } catch (MessagingException e){
            throw new BizException("邮件发送失败: " + e);
        }

    }


    public static void main(String[] args) {
       sendMail("Ivan | 晏飞个人博客评论提醒","您有一封邮件,请查收!","286835776@qq.com");
    }


}

