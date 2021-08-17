package com.lzh.lol.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.lzh.lol.base.ResultInfo;
import com.lzh.lol.mapper.ApplyMapper;
import com.lzh.lol.po.User;
import com.lzh.lol.service.CreditsService;
import com.lzh.lol.service.SignService;
import com.lzh.lol.utils.AssertUtil;
import com.lzh.lol.vo.SignRankVo;
import com.lzh.lol.vo.SignVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName SignController
 * @Description TODO
 * @Author lzh
 * @Date 2021/7/27 18:38
 */
@RestController
public class SignController {

    private Logger logger= LoggerFactory.getLogger(SignController.class);

    @Resource
    private SignService signService;

    @Resource
    private CreditsService creditsService;

    @Resource
    private ApplyMapper applyMapper;

    private static final String ForFront = "http://47.96.86.132:9099/";

    /**
     * @Description //TODO 签到请求(签到+查询) 加积分
     * @Date 2021/8/1 16:15
     * @Return com.lzh.lol.base.ResultInfo<com.lzh.lol.vo.SignVo>
     */
    @PostMapping("/sign")
    //@CodeTransformAnno
    public ResultInfo<String> sign(@RequestBody User user) {

        AssertUtil.isTrue(!signService.isLogin(user.getOpenid()), "请先登录!");
        //openId与微信姓名绑定
        //signService.bindOpenAndName(user.getCode(),user.getWxName());
        signService.doSign(user.getOpenid(), LocalDate.now());

        Integer score = creditsService.countAddWeight(user.getOpenid());
        creditsService.addScore(user.getOpenid(),score);

        logger.info("签到:{}签到成功!增加{}积分",user.getOpenid(),score);

        //return new ResultInfo<>("签到成功!",new SignVo(signService.getContinuousSignCount(user.getOpenid(), LocalDate.now()), signService.getRanking(user.getOpenid()), creditsService.countAddWeight(user.getOpenid())));
        return new ResultInfo<>("签到成功!");
        //return new ResultInfo<>();
    }



    private String getCodeById(String code) {
        User user = applyMapper.selectOne(new QueryWrapper<User>().eq("open_id", code).select("id"));
        return user.getId();
    }



    /**
     * @param
     * @Description //TODO 获取签到前三名
     * @Date 2021/8/1 16:27
     * @Return com.lzh.lol.base.ResultInfo<java.util.List < com.lzh.lol.vo.SignRankVo>>
     */
    @GetMapping("/sign/rank")
    public ResultInfo<List<SignRankVo>> rank() {
        List<SignRankVo> list = signService.getTopThree();
        Collections.sort(list);
        forFrond(list);
        return new ResultInfo(list);

    }

    private List<SignRankVo> forFrond(List<SignRankVo> list) {
        for (int i = 0; i < Math.min(list.size(), 3); i++) {
            list.get(0+i).setSrc(ForFront + ((3 + i) + ".png"));
        }
        return list;
    }
}
