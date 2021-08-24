package com.lzh.lol.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 志昊的刘
 * @version 1.0
 * @description: TODO 返回给前端的比赛简介信息列表 是个数组
 * @date 2021/8/23 17:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompetitionListVO {

    private Integer id;

    private String teamName1;

    private String teamName2;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createDate;




}
