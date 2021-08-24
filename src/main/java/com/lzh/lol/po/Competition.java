package com.lzh.lol.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author lzh
 * @since 2021-08-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Competition  {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("team_id_1")
    private Integer teamId1;

    @TableField("team_id_2")
    private Integer teamId2;

    private Integer winId;

    private Date createDate;

}
