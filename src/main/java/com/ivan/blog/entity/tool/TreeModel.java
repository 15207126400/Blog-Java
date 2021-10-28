package com.ivan.blog.entity.tool;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  @Author: Ivan
 *  @Description:   树结构
 *  @Date: 2019/11/6 20:56
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TreeModel {

    //标签
    private Integer tags;

    //文本，对应text属性，显示在节点上的文本
    private String text;

    //勾选状态
    private Map<String,Object> state;

    //保存子节点，对应插件中nodes属性
    private List<TreeModel> nodes = new ArrayList<>();

}
