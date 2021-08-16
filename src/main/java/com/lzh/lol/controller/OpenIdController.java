package com.lzh.lol.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzh.lol.base.ResultInfo;
import com.lzh.lol.service.OpenIdService;
import com.lzh.lol.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * @ClassName OpenIdController
 * @Description TODO 获取open_id
 * @Author lzh
 * @Date 2021/7/27 14:59
 */
@RestController

public class OpenIdController {

    @Autowired
    private OpenIdService openIdService;

    /**
     * @param map
     * @Description //TODO 根据用户id去微信后台获取OpenId
     * @Date 2021/7/27 15:24
     * @Return com.lzh.lol.base.ResultInfo<java.lang.String>
     */
    @PostMapping("/openid")
    public ResultInfo<String> getOpenId(@RequestBody Map<String, String> map) throws Exception {
        String code = map.get("code");
        AssertUtil.isTrue(StringUtils.isBlank(code), "非法code!");
        String openId = openIdService.getOpenId(code);
        return new ResultInfo<>(openId);
    }

}
