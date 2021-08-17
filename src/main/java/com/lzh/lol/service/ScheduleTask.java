package com.lzh.lol.service;

import com.lzh.lol.po.OpenidDate;
import com.lzh.lol.po.OpenidScore;
import com.lzh.lol.po.OpenidWxname;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static com.lzh.lol.controller.BindNameController.BIND_OPENID_WXNAME;
import static com.lzh.lol.service.CreditsService.USER_SCORE;

@Service
public class ScheduleTask {

    Logger logger = LoggerFactory.getLogger(ScheduleTask.class);

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MMdd");

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private OpenidWxnameService openidWxnameService;

    @Resource
    private OpenidScoreService openidScoreService;

    @Resource
    private OpenidDateService openidDateService;


    @Scheduled(cron = "0 0 0/1 * * ?")
    public void redisDataToMysql() {
        logger.info("time:{},开始执行Redis数据持久化到Mysql任务", LocalDateTime.now().format(formatter));

        //持久化微信名与openid的绑定
        saveHashDataToMysql(BIND_OPENID_WXNAME);

        //持久化openid与score到mysql
        saveZHashDataToMysql(USER_SCORE);

        //持久化每天的签到时间
        saveZHashDataToMysql(LocalDateTime.now().format(formatter2));


        logger.info("time:{},结束执行Redis数据持久化到Mysql任务", LocalDateTime.now().format(formatter));
    }

    public void saveZHashDataToMysql(String hashKey) {
//        Map<String, Object> map = redisTemplate.opsForHash().entries(hashKey);
        Set<ZSetOperations.TypedTuple<Object>> tuples = redisTemplate.opsForZSet().rangeWithScores(hashKey, 0, -1);
        if (tuples == null) return;

        for (ZSetOperations.TypedTuple<Object> entry : tuples) {
            if (USER_SCORE.equals(hashKey))
                synchronizeOpenIdScore(new OpenidScore((String) entry.getValue(), entry.getScore().toString()));
            else
                synchronizeOpenIdDate(new OpenidDate((String) entry.getValue(), entry.getScore().toString()));
        }
    }

    private void saveHashDataToMysql(String hashKey) {
        Map<String, Object> map = redisTemplate.opsForHash().entries(hashKey);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (BIND_OPENID_WXNAME.equals(hashKey))
                synchronizeOpenIdWxName(new OpenidWxname(entry.getKey(), (String) entry.getValue()));
        }
    }


    private void synchronizeOpenIdScore(OpenidScore openidScore) {
        openidScoreService.saveOrUpdate(openidScore);
    }

    private void synchronizeOpenIdWxName(OpenidWxname openidWxname) {
        openidWxnameService.saveOrUpdate(openidWxname);
    }

    private void synchronizeOpenIdDate(OpenidDate openidDate) {
        openidDateService.save(openidDate);
    }
}
