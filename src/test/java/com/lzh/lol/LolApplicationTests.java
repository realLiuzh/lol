package com.lzh.lol;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzh.lol.service.CreditsService;
import com.lzh.lol.service.ScheduleTask;
import com.lzh.lol.service.SignService;
import com.lzh.lol.vo.ScoreRankVo;
import com.lzh.lol.vo.SignRankVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class LolApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SignService signService;


    @Autowired
    private CreditsService creditsService;

    @Resource
    private ScheduleTask scheduleTask;

    public static final String USER_SCORE = "USER:SCORE";


    @Test
    void contextLoads() {
       /* double incrementScore = redisTemplate.opsForZSet().incrementScore("zSetValue","C",5);
        System.out.print("通过incrementScore(K key, V value, double delta)方法修改变量中的元素的分值:" + incrementScore);
        double score = redisTemplate.opsForZSet().score("zSetValue","C");
        System.out.print(",修改后获取元素的分值:" + score);*/

        //System.out.println(redisTemplate.boundZSetOps("USER_SCORE").score("id").intValue());

//        JSONObject jsonObject = JSON.parseObject("{\"session_key\":\"ol9UtEiJbmAvrpCi0wOEdQ==\",\"openid\":\"o7eeJ574F9DBptCtYQkl1tkzVOHI\"}");
//        System.out.println((String) jsonObject.get("openid"));

        /*List<SignRankVo> topThree = signService.getTopThree();
        System.out.println(topThree);*/
//        long i = signService.getContinuousSignCount("o7eeJ574F9DBptCtYQkl1tkzVOHI", LocalDate.now());
//        System.out.println(i);


//        int o7eeJ574F9DBptCtYQkl1tkzVOHI = signService.getRanking("o7eeJ574F9DBptCtYQkl1tkzVOHI")+1;
//        System.out.println(o7eeJ574F9DBptCtYQkl1tkzVOHI);


//        boolean o7eeJ574F9DBptCtYQkl1tkzVOHI = signService.checkSign("o7eeJ574F9DBptCtYQkl1tkzVOHI", LocalDate.now());
//        System.out.println(o7eeJ574F9DBptCtYQkl1tkzVOHI);


//        signService.getTopThree();
        //redisTemplate.opsForZSet().incrementScore(USER_SCORE,"1201889129",600);
        /*Double i=redisTemplate.boundZSetOps(USER_SCORE).score("id");
        System.out.println(i);*/

        //redisTemplate.opsForHash().put("BIND_OPENID_WXNAME","code","wwwwwwwwwwwxName");


//        boolean flag = signService.isLogin("oxaes5CMTEX6jH-BznONsjx3mwIA");
//        System.out.println(flag);

        scheduleTask.redisDataToMysql();
        //scheduleTask.saveZHashDataToMysql("USER:SCORE");
    }

}
