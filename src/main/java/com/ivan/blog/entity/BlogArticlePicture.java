package com.ivan.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 *  @Author: Ivan
 *  @Description:   文章图片
 *  @Date: 2021/10/27 14:33
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BlogArticlePicture implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private Integer articleId;

    private String filename;

    private String pictureUrl;

    private Date createTime;
}