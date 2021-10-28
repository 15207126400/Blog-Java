package com.ivan.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 *  @Author: Ivan
 *  @Description:   数据字典
 *  @Date: 2021/10/27 14:33
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysDict implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String typeCode;

    private String typeValue;

    private String dictCode;

    private String dictValue;

    private String status;

}