package com.lzh.lol.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName SignVo
 * @Description TODO
 * @Author lzh
 * @Date 2021/8/1 16:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignVo {

    private Long continuousSignCount;
    private Integer ranking;
    private Integer credits;

}
