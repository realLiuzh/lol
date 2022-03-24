package com.lzh.lol.controller;


import com.lzh.lol.base.ResultInfo;
import com.lzh.lol.po.Bet;
import com.lzh.lol.service.BetService;
import com.lzh.lol.service.CreditsService;
import com.lzh.lol.vo.BetVO;
import com.lzh.lol.vo.MyBetVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lzh
 * @since 2021-08-23
 */
@RestController
public class BetController {

    @Resource
    private BetService betService;

    @Resource
    private CreditsService creditsService;

    @PostMapping("/bet")
    public ResultInfo<Object> bet(@RequestBody BetVO betVO){
        //向下注表中增加数据
        betService.buildUserBet(betVO);
        return new ResultInfo<>(200,"下注成功",null);
    }

    @GetMapping("/mybet")
    public ResultInfo<List<MyBetVO>> myBet(@RequestParam("openid") String openid){
        return new ResultInfo<>(betService.myBat(openid));
    }

    @PostMapping("/influenceOfBetting")
    public ResultInfo<Object> influenceOfBetting(Bet bet){
        betService.influenceOfBetting(bet.getCompetitionId(),bet.getBetTeamId());
        return new ResultInfo<>(200,"下注结果修改成功",null);
    }
}

