package com.lzh.lol.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@TableName("openid_wxname")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OpenidWxname {

    @TableId
    private String openId;

    private String wxName;
}
