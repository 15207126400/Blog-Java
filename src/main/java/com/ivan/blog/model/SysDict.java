package com.ivan.blog.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysDict implements Serializable {
    private static final long serialVersionUID = -1L;

    private Integer id;

    private String typeCode;

    private String typeValue;

    private String dictCode;

    private String dictValue;

    private String status;

}