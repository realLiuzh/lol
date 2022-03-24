package com.lzh.lol.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 *
 * </p>
 *
 * @author lzh
 * @since 2021-08-23
 */
@Data
@TableName("competition_bet")
@AllArgsConstructor
@NoArgsConstructor
public class Bet {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String openid;

    private Integer competitionId;

    private Integer betTeamId;

    private Double betScore;

    private Date createDate;
}
