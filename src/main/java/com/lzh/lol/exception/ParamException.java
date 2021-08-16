package com.lzh.lol.exception;

import lombok.Data;
/**
 * @ClassName ParamException
 * @Description TODO
 * @Author lzh
 * @Date 2021/7/27 15:39
 */
@Data
public class ParamException extends RuntimeException{
    private Integer code=300;
    private String message="参数异常!";

    public ParamException(String message) {
        super(message);
        this.message = message;
    }
}
