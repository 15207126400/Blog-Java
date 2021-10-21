package com.ivan.blog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BlogArticleCategory implements Serializable {
    private static final long serialVersionUID = -1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer categoryId;

    private Integer articleId;

}