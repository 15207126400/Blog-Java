package com.ivan.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ivan.blog.validation.Save;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Ivan
 * @date 2021/11/25 09:14
 * @Description 博客历史记录
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BlogHistory implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Size(min = 4, message = "标题长度不允许少于4个字符")
    @NotBlank(message = "标题不允许为空", groups = {Save.class})
    private String title;

    @Size(min = 5, message = "内容长度不允许少于4个字符")
    @NotBlank(message = "内容不允许为空", groups = {Save.class})
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
}
