package com.ivan.blog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BlogArticlePicture implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private Integer articleId;

    private String filename;

    private String pictureUrl;

    private Date createTime;
}