package com.ivan.blog.utils;

import com.ivan.blog.Exception.Enum.CommonEnum;
import com.ivan.blog.constants.BlogConstants;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author: Ivan
 * @Description: 响应信息主体
 * @Date: 2019/11/29 17:01
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class R<T> implements Serializable {

    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private String msg;

    @Getter
    @Setter
    private T data;

    public static <T> R<T> ok() {
        return restResult(null, BlogConstants.SUCCESS, "操作成功");
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, BlogConstants.SUCCESS, "操作成功");
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, BlogConstants.SUCCESS, msg);
    }

    public static <T> R<T> failed() {
        return restResult(null, BlogConstants.FAIL, null);
    }

    public static <T> R<T> failed(String msg) {
        return restResult(null, BlogConstants.FAIL, msg);
    }

    public static <T> R<T> failed(T data) {
        return restResult(data, BlogConstants.FAIL, null);
    }

    public static <T> R<T> failed(T data, String msg) {
        return restResult(data, BlogConstants.FAIL, msg);
    }

    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public static R error() {
        return error(0, "操作失败");
    }

    public static R error(String msg) {
        R r = new R();
        r.setCode(1);
        r.setMsg(msg);
        return r;
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public static <T> R<T> failed(T data, CommonEnum commonEnum) {
        return restResult(data, commonEnum.getResultCode(), commonEnum.getResultMsg());
    }

    public static <T> R<T> failed(CommonEnum commonEnum) {
        return restResult(null, commonEnum.getResultCode(), commonEnum.getResultMsg());
    }

}
