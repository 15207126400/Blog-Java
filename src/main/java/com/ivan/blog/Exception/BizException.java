package com.ivan.blog.Exception;

import com.ivan.blog.Exception.Enum.CommonEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/*
 *  @Author: Ivan
 *  @Description:   业务异常类
 *  @Date: 2020/1/1 14:08
 */
@Data
@AllArgsConstructor
public class BizException extends RuntimeException{

    /**
     * 异常码
     */
    protected Integer code;

    public BizException() {
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(CommonEnum codeEnum, Object... args) {
        super(String.format(codeEnum.getResultMsg(), args));
        this.code = codeEnum.getResultCode();
    }

}
