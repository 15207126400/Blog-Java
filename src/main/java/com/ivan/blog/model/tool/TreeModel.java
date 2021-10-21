package com.ivan.blog.model.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 *  @Author: Ivan
 *  @Description:   树结构
 *  @Date: 2019/11/6 20:56
 */
public class TreeModel {
    private Integer tags;
    private String text;                                    //文本，对应text属性，显示在节点上的文本
    private Map<String,Object> state;
    private List<TreeModel> nodes = new ArrayList<TreeModel>();     //保存子节点，对应插件中nodes属性

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public List<TreeModel> getNodes() {
        return nodes;
    }
    public void setNodes(List<TreeModel> nodes) {
        this.nodes = nodes;
    }

    public Map<String, Object> getState() {
        return state;
    }

    public void setState(Map<String, Object> state) {
        this.state = state;
    }

    public Integer getTags() {
        return tags;
    }

    public void setTags(Integer tags) {
        this.tags = tags;
    }
}
