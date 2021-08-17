package com.lzh.lol.controller;

import com.lzh.lol.base.ResultInfo;
import com.lzh.lol.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName BindNameController
 * @Description TODO
 * @Author lzh
 * @Date 2021/8/3 17:15
 */
@RestController
public class BindNameController {

    private Logger logger= LoggerFactory.getLogger(BindNameController.class);

    public static final String BIND_OPENID_WXNAME = "OPENID:WXNAME";

    @Resource
    private RedisTemplate redisTemplate;


    /**
     * @param map
     * @Description //TODO open_id与微信名绑定
     * @Date 2021/8/3 17:18
     * @Return com.lzh.lol.base.ResultInfo<java.lang.String>
     */
    @PostMapping("/bind")
    public ResultInfo<String> bind(@RequestBody Map<String, String> map) {
        String openid = map.get("openid");
        String wxName = map.get("wxName");
        AssertUtil.isTrue(StringUtils.isBlank(openid)||StringUtils.isBlank(wxName),
                "open_id或者微信名为空!");
        logger.info("微信名字与openid绑定--->{}:{}",wxName,openid);
        redisTemplate.opsForHash().put(BIND_OPENID_WXNAME, openid, wxName);
        return new ResultInfo<>();
    }

}
