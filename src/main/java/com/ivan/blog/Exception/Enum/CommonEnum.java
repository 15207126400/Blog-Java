package com.ivan.blog.Exception.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/*
 *  @Author: Ivan
 *  @Description:   业务枚举类
 *  @Date: 2020/1/1 14:05
 */
@Getter
@AllArgsConstructor
public enum  CommonEnum{

    // 数据操作错误定义
    SUCCESS(200, "请求成功!"),
    ERROR(-1, "请求失败!"),
    BODY_NOT_MATCH(400,"请求的数据格式不符!"),
    SIGNATURE_NOT_MATCH(401,"请求的数字签名不匹配!"),
    NOT_FOUND(404, "未找到该资源!"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误!"),
    REQUEST_TOO_FREQUENT(503,"请求过于频繁,请稍后再试!"),
    REQUEST_LOCK(505,"你存在恶意攻击行为,服务器拒绝访问!"),


    /* 博客前端 001~100 */
    ARTICLE_NULL(001,"没有查询到文章数据!"),
    CATEGORY_NULL(002,"没有查询到标签数据!"),

    COMMENT_NAME_IL(011,"博主会告你侵权哦!");


    /** 错误码 */
    private int resultCode;

    /** 错误描述 */
    private String resultMsg;

    public static CommonEnum getValueByCode(Integer code){
        for(CommonEnum commonEnum : CommonEnum.values()){
            if(code.equals(commonEnum.getResultCode())){
                return commonEnum;
            }
        }
        return  null;
    }

    public static void main(String[] args) {
        CommonEnum commonEnum = getValueByCode(001);
        System.out.println(commonEnum.getResultCode());
        System.out.println(commonEnum.getResultMsg());
    }

}
