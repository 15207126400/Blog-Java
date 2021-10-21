package com.ivan.blog.model.tool;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.io.InputStream;
import java.io.Serializable;

@Data
public class EmailModel implements Serializable {
    /**
     * 序列化
     */
    private static final long serialVersionUID = -8840079326802564269L;

    /**
     * 发送者
     */
    @Value("${spring.mail.username}")
    private String from;

    /**
     * 接受者
     */
    private String to;

    /**
     * 抄送
     */
    private String[] cc;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String text;

    /**
     * 附件路径
     */
    private String path;

    private InputStream is;

}
