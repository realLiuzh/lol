package com.lzh.lol.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName ScoreRankVo
 * @Description TODO
 * @Author lzh
 * @Date 2021/8/1 14:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreRankVo implements Comparable<ScoreRankVo> {

    private String id;
    private Integer score;
    private String src;

    public ScoreRankVo(String id, Integer score) {
        this.id = id;
        this.score = score;
    }


    @Override
    public int compareTo(ScoreRankVo o) {
        return o.score-this.score;
    }
}
