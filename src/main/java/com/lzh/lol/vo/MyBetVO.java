package com.lzh.lol.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 志昊的刘
 * @version 1.0
 * @description: TODO
 * @date 2021/8/24 14:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyBetVO {

    private String teamName1;

    private String teamName2;

    private String winTeam;

    private String betTeam;

    private Integer betScore;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date betCreate;

    private Integer userGet;

}
