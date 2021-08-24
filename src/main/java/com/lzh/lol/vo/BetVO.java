package com.lzh.lol.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.util.Date;

/**
 * @author 志昊的刘
 * @version 1.0
 * @description: TODO
 * @date 2021/8/23 19:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BetVO {

    private String openid;

    private Integer competitionId;

    private Integer betTeamId;

    private Integer betScore;

}
