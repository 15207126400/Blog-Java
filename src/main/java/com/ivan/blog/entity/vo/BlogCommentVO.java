package com.ivan.blog.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class BlogCommentVO implements Serializable {

    private Integer id;

    private String content;

    private String email;

    private String name;

    private String avatarUrl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    private Integer articleId;

    private Integer parentId;

    private String articleTitle;

    private Integer ladder;

    /**
     * 子评论集合
     */
    private List<BlogCommentVO> blogCommentList;
}
