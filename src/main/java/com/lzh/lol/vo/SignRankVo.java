package com.lzh.lol.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName SignRankVo
 * @Description TODO
 * @Author lzh
 * @Date 2021/7/31 16:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SignRankVo implements Comparable<SignRankVo> {

    private String name;
    private String time;
    private Integer abTime;
    private String src;

    public SignRankVo(String name, Integer abTime) {
        this.name = name;
        this.abTime = abTime;
    }


    @Override
    public int compareTo(SignRankVo o) {
        return this.abTime.compareTo(o.abTime);
    }
}
