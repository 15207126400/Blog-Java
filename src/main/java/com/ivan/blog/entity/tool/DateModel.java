package com.ivan.blog.entity.tool;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DateModel {
    private String date;
    private String count;
}
