package com.lzh.lol.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author 志昊的刘
 * @version 1.0
 * @description: TODO
 * @date 2021/8/24 15:35
 */
@Data
public class MyBetDTO {


    private Integer competitionId;

    private Integer teamId1;

    private Integer teamId2;

    private Integer winId;

    private Integer betScore;

    private Integer betTeamId;

    private Date createDate;



}
