package com.ivan.blog.utils;

import com.ivan.blog.Exception.Enum.CommonEnum;
import lombok.*;
import lombok.experimental.Accessors;
import java.io.Serializable;

/*
 *  @Author: Ivan
 *  @Description:   响应信息主体
 *  @Date: 2019/11/29 17:01
 */
@Builder
@ToString
@Accessors(chain = true)
@AllArgsConstructor
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private int code = 0;

    @Getter
    @Setter
    private String msg = "success";


    @Getter
    @Setter
    private T data;

    public R() {
        super();
    }

    public R(T data) {
        super();
        this.data = data;
    }

    public R(T data, String msg) {
        super();
        this.data = data;
        this.msg = msg;
    }

    public R(Throwable e) {
        super();
        this.msg = e.getMessage();
        this.code = 1;
    }


    public static <T> R<T> ok() {
        return restResult(null, CommonEnum.SUCCESS.getResultCode(), CommonEnum.SUCCESS.getResultMsg());
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, CommonEnum.SUCCESS.getResultCode(), CommonEnum.SUCCESS.getResultMsg());
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, CommonEnum.SUCCESS.getResultCode(), msg);
    }

    public static <T> R<T> failed() {
        return restResult(null, CommonEnum.ERROR.getResultCode(), CommonEnum.ERROR.getResultMsg());
    }

    public static <T> R<T> failed(String msg) {
        return restResult(null, CommonEnum.ERROR.getResultCode(), msg);
    }

    public static <T> R<T> failed(T data) {
        return restResult(data, CommonEnum.ERROR.getResultCode(), CommonEnum.ERROR.getResultMsg());
    }

    public static <T> R<T> failed(T data, String msg) {
        return restResult(data, CommonEnum.ERROR.getResultCode(), msg);
    }

    public static <T> R<T> failed(CommonEnum commonEnum) {
        return restResult(null, commonEnum.getResultCode(), commonEnum.getResultMsg());
    }

    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }
}
