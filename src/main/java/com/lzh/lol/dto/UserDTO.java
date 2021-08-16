package com.lzh.lol.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName User
 * @Description TODO
 * @Author lzh
 * @Date 2021/7/27 18:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    /**
     * 学号
     */
    private String id;

    /**
     * userId
     */
    private String userId;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private String gender;
    /**
     * 电话;电话
     */
    private String phone;
}
