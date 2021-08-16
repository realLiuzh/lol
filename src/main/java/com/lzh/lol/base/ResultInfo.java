package com.lzh.lol.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName ResultInfo
 * @Description TODO
 * @Author lzh
 * @Date 2021/7/27 15:02
 */
@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResultInfo<T> {
    private Integer code;

    private String message;

    private T data;


    public ResultInfo() {
        this.code = 200;
        this.message = "success";
    }

    public ResultInfo(T data) {
        this();
        this.data = data;
    }

    public ResultInfo(String message, T data) {
        this.code = 200;
        this.message = message;
        this.data = data;
    }
}
