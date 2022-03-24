package com.lzh.lol.controller;

import com.lzh.lol.annotation.CodeTransformAnno;
import com.lzh.lol.base.ResultInfo;
import com.lzh.lol.po.User;
import com.lzh.lol.service.ApplyService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ApplyController
 * @Description TODO 报名参赛
 * @Author lzh
 * @Date 2021/7/27 15:28
 */
@RestController
public class ApplyController {

    @Resource
    private ApplyService applyService;


    /**
     * @Description //TODO 报名参赛
     * @Date 2021/7/27 15:29
     * @param
     * @Return com.lzh.lol.base.ResultInfo
     */
    @PostMapping("/apply")
    //@CodeTransformAnno
    public ResultInfo<String> apply(@RequestBody User user){
        applyService.apply(user);
        return new ResultInfo<>();
    }



    /**
     * @Description //TODO 是否参赛
     * @Date 2021/8/1 16:30
     * @param openid
     * @Return com.lzh.lol.base.ResultInfo<java.lang.Boolean>
     */
    @GetMapping("/isapply")
    public ResultInfo<Boolean> isApply(@RequestParam String openid){
        boolean flag = applyService.isApply(openid);
        return new ResultInfo<>(flag);
    }


    @PostMapping("/hello")
    public void hello(@RequestParam String uid){
        System.out.println(uid);
    }

}
