package com.lzh.lol.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzh.lol.base.ResultInfo;
import com.lzh.lol.controller.OpenIdController;
import com.lzh.lol.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @ClassName OpenIdService
 * @Description TODO
 * @Author lzh
 * @Date 2021/7/27 19:27
 */
@Service
public class OpenIdService {

    Logger logger = LoggerFactory.getLogger(OpenIdController.class);

    private static final String APPID = "wx8b56e201c6ba67c8";
    private static final String APPSECRET = "5bb739c192fd790e5f3b07c2fc7ad347";

    public String getOpenId(String userId) throws Exception {
        //String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + APPID + "&secret=" + APPSECRET + "&js_code=" + userId + "&grant_type=authorization_code";
        String url = String.format("https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code", APPID, APPSECRET, userId);

        URL obj = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        logger.info("\nSending 'GET' request to URL : {}", url);
        logger.info("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //打印结果
        logger.info("return_value:{}", response.toString());
        JSONObject jsonObject = JSON.parseObject(response.toString());
        String openid = (String) jsonObject.get("openid");
        logger.info("open_id:{}", openid);
        AssertUtil.isTrue(StringUtils.isBlank(openid),"非法code");
        return openid;
    }


}
