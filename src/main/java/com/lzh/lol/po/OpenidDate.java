package com.lzh.lol.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@TableName("openid_date")
public class OpenidDate {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String openId;

    private String date;

    public OpenidDate(String openId, String date) {
        this.openId = openId;
        this.date = date;
    }
}
