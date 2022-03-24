package com.lzh.lol.mapper;

import com.lzh.lol.dto.MyBetDTO;
import com.lzh.lol.po.Bet;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lzh
 * @since 2021-08-23
 */
public interface BetMapper extends BaseMapper<Bet> {

    List<MyBetDTO> myBet(String openid);







}
