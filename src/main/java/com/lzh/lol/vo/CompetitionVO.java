package com.lzh.lol.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 志昊的刘
 * @version 1.0
 * @description: TODO 某个赛事的信息
 * @date 2021/8/23 18:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompetitionVO {
    private Integer id;

    private Integer teamId1;

    private String teamName1;

    private Integer teamId2;

    private String teamName2;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createDate;
}
