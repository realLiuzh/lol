package com.lzh.lol.controller;

import com.lzh.lol.base.ResultInfo;
import com.lzh.lol.service.CreditsService;
import com.lzh.lol.vo.ScoreRankVo;
import com.lzh.lol.vo.SignRankVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName CreditsController
 * @Description TODO
 * @Author lzh
 * @Date 2021/8/1 16:28
 */
@RestController
public class CreditsController {

    @Resource
    private CreditsService creditsService;

    private static final String ForFront = "http://47.96.86.132:9099/";

    /**
     * @param openid
     * @Description //TODO 获取用户的分数
     * @Date 2021/8/1 16:38
     * @Return com.lzh.lol.base.ResultInfo<java.lang.Integer>
     */
    @GetMapping("/credits")
    public ResultInfo<Integer> credits(String openid) {
        return new ResultInfo<>(creditsService.getCredit(openid));
    }

    /**
     * @param
     * @Description //TODO 获取积分排名
     * @Date 2021/8/1 16:39
     * @Return com.lzh.lol.base.ResultInfo<java.util.List < com.lzh.lol.vo.ScoreRankVo>>
     */
        @GetMapping("/credits/rank")
    public ResultInfo<List<ScoreRankVo>> rank() {
        List<ScoreRankVo> list = creditsService.getTopTen();
        Collections.sort(list);
        forFrond(list);
        return new ResultInfo<>(list);
    }

    private List<ScoreRankVo> forFrond(List<ScoreRankVo> list) {
        for (int i = 0; i < Math.min(list.size(), 3); i++) {
            list.get(0+i).setSrc(ForFront + ((3 + i) + ".png"));
        }
        return list;
    }


}
