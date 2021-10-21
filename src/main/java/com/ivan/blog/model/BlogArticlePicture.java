package com.ivan.blog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BlogArticlePicture {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private Integer articleId;

    private String pictureUrl;

    private Date createTime;

    private Date updateTime;

    public BlogArticlePicture(Integer id, Integer articleId, String pictureUrl, Date createTime, Date updateTime) {
        this.id = id;
        this.articleId = articleId;
        this.pictureUrl = pictureUrl;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public BlogArticlePicture() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl == null ? null : pictureUrl.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}