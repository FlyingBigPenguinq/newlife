package com.study.boot.Data.response;

import lombok.Data;

/**
 * @ClassName BaseResponse
 * @Description: TODO:
 * @Author lxl
 * @Date 10/2/20
 * @Version V1.0
 **/
@Data
public class BaseResponse<T> {
    private Integer code;
    private String msg;
    private T data;

    public BaseResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseResponse(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public BaseResponse(StatusCode statusCode) {
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
    }
}
