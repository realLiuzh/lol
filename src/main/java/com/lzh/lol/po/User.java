package com.lzh.lol.po;


import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User {
    /**
     * 学号
     */
    @TableId(type = IdType.INPUT)
    private String id;

    /**
     * 小程序的唯一标识
     */
    @TableField("open_id")
    private String openid;
    /**
     * 姓名
     */
    private String name;
    /**
     * 微信名
     */
    @TableField(exist = false)
    private String wxName;
    /**
     * 性别
     */
    private String gender;
    /**
     * 电话;电话
     */
    private String phone;
    /**
     * 战队;战队id
     */
    @TableField(updateStrategy=FieldStrategy.IGNORED)
    private Integer team;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否有效
     */
    private Integer isValid;

    public User(String id, Integer team) {
        this.id = id;
        this.team = team;
    }
}