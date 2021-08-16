package com.lzh.lol.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Team {
    /**
     * id;战队id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 战队名称;战队名
     */
    private String teamName;
    /**
     * 队长
     */
    private String captainId;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 队员id
     */
    @TableField(exist = false)
    private List<String> memberId;

    @TableLogic
    private Integer isValid;
}