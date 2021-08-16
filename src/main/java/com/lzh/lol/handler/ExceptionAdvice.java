package com.lzh.lol.handler;

import com.lzh.lol.base.ResultInfo;
import com.lzh.lol.exception.ParamException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ExceptionAdvice
 * @Description TODO
 * @Author lzh
 * @Date 2021/7/27 16:55
 */
@ControllerAdvice
@RestController
public class ExceptionAdvice {

    @ExceptionHandler(ParamException.class)
    public ResultInfo<String> paramExceptionHandler(ParamException e) {
        e.printStackTrace();
        return new ResultInfo<>(300, e.getMessage(), null);
    }


    @ExceptionHandler(Exception.class)
    public ResultInfo<String> ExceptionHandler(Exception e) {
        e.printStackTrace();
        return new ResultInfo<>(501, "error!", null);
    }
}
