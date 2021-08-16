package com.lzh.lol.utils;

import com.lzh.lol.exception.ParamException;

/**
 * @ClassName AssertUtil
 * @Description TODO
 * @Author lzh
 * @Date 2021/7/27 15:37
 */

public class AssertUtil {

    public static void isTrue(boolean flag, String message) {
        if (flag) {
            throw new ParamException(message);
        }
    }

}
