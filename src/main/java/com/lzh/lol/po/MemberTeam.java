package com.lzh.lol.po;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberTeam {
    /**
     * 学号
     */
    private Integer userId;
    /**
     * 战队号
     */
    private Integer teamId;
}