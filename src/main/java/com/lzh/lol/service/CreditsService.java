package com.lzh.lol.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzh.lol.mapper.ApplyMapper;
import com.lzh.lol.po.User;
import com.lzh.lol.utils.AssertUtil;
import com.lzh.lol.utils.GetIDByUtil;
import com.lzh.lol.vo.ScoreRankVo;
import com.lzh.lol.vo.SignRankVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.*;

/**
 * @ClassName CreditsService
 * @Description TODO
 * @Author lzh
 * @Date 2021/7/31 17:12
 */
@Service
public class CreditsService {

    public static final Integer FIRST = 600;
    public static final Integer SECOND = 500;
    public static final Integer THIRD = 450;

    public static final Integer FIRST_PART = 200;
    public static final Integer SECOND_PART = 300;
    public static final Integer THIRD_PART = 400;


    public static final String USER_SCORE = "USER:SCORE";
    private static final String BIND_OPENID_WXNAME="OPENID:WXNAME";


    @Resource
    private RedisTemplate redisTemplate;


    @Resource
    private SignService signService;

    @Resource
    private ApplyMapper applyMapper;


    @PostConstruct
    public void init() {
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);
        redisTemplate.setHashValueSerializer(redisSerializer);
    }

    /**
     * @param id
     * @Description //TODO 计算open_id为id的用户本次签到应加多少分
     * @Date 2021/7/31 17:13
     * @Return java.lang.Integer
     */
    public Integer countAddWeight(String id) {
        //1.先查看该用户今天有没有签到
        AssertUtil.isTrue(!signService.checkSign(id, LocalDate.now()), "该用户尚未签到!");
        //2.如果签到了——>先检查他是不是今日签到前三名
//        List<SignRankVo> topThree = signService.getTopThree();
//        if(topThree.size()>=1&&topThree.get(0).getName().equals(id)){
//            return FIRST;
//        }else if(topThree.size()>=2&&topThree.get(1).getName().equals(id)){
//            return SECOND;
//        }else if(topThree.size()>=3&&topThree.get(2).getName().equals(id)){
//            return THIRD;
//        }
        int ranking = signService.getRanking(id);
        if (ranking == 1) {
            return FIRST;
        } else if (ranking == 2) {
            return SECOND;
        } else if (ranking == 3) {
            return THIRD;
        }

        long continuousSignCount = signService.getContinuousSignCount(id, LocalDate.now());

        if (continuousSignCount >= 10) {
            return FIRST_PART;
        } else if (continuousSignCount >= 2) {
            return SECOND_PART;
        } else {
            return THIRD_PART;
        }
    }


    /**
     * @param id 学号
     * @Description //TODO 给用户添加积分
     * @Date 2021/8/1 14:30
     * @Return void
     */
    public void addScore(String id, Double score) {
        //2.存到redis中去,以zset的格式
        redisTemplate.opsForZSet().incrementScore(USER_SCORE, id, score);
    }


    /**
     * @param
     * @Description //TODO 获取积分总排名的前十位
     * @Date 2021/8/1 14:35
     * @Return java.util.List<com.lzh.lol.vo.ScoreRankVo>
     */
    public List<ScoreRankVo> getTopTen() {
        //Set<ZSetOperations.TypedTuple<String>> tuples = redisTemplate.opsForZSet().rangeWithScores(USER_SCORE, 0, 9);
//        Set<ZSetOperations.TypedTuple<String>> tuples = redisTemplate.opsForZSet().reverseRange(USER_SCORE, 0, 9);
//        if (tuples == null || tuples.size() == 0) return new ArrayList<>();
//        List<ScoreRankVo> list = SetToList(tuples);
//        list = OpenIdTransferWxName(list);
//        return list;
        Set<ZSetOperations.TypedTuple<String>> tuples = redisTemplate.opsForZSet().reverseRangeWithScores(USER_SCORE, 0, 9);
      /*  List<ScoreRankVo> list = new ArrayList<>(set);
        list = OpenIdTransferWxName(list);
        return list;*/
        if (tuples == null || tuples.size() == 0) return new ArrayList<>();
        List<ScoreRankVo> list = SetToList(tuples);
        list = OpenIdTransferWxName(list);
        return list;

    }


    private List<ScoreRankVo> OpenIdTransferWxName(List<ScoreRankVo> list) {
        List<ScoreRankVo> listt = new ArrayList<>();
        for (ScoreRankVo scoreRankVo : list) {
            scoreRankVo.setId((String) redisTemplate.opsForHash().get(BIND_OPENID_WXNAME,scoreRankVo.getId()));
            listt.add(scoreRankVo);
        }
        return listt;

    }

   /* private List<ScoreRankVo> idTransferName(List<ScoreRankVo> list) {
        List<ScoreRankVo> listt = new ArrayList<>();
        for (ScoreRankVo scoreRankVo : list) {
            scoreRankVo.setId(applyMapper.selectOne(new QueryWrapper<User>()
                    .eq("id", (scoreRankVo.getId()))
                    .select("name"))
                    .getName());
            listt.add(scoreRankVo);
        }
        return listt;

    }*/

    private List<ScoreRankVo> SetToList(Set<ZSetOperations.TypedTuple<String>> tuples) {
        List<ScoreRankVo> list = new ArrayList<>();
        for (ZSetOperations.TypedTuple<String> tuple : tuples) {
            list.add(new ScoreRankVo(tuple.getValue(), tuple.getScore().intValue()));
        }
        return list;
    }

    /**
     * @param id
     * @Description //TODO 获取用户的积分
     * @Date 2021/8/1 16:32
     * @Return java.lang.Integer
     */
    public Integer getCredit(String id) {
        AssertUtil.isTrue(StringUtils.isBlank(id), "参数错误openid为空!");
        return redisTemplate.boundZSetOps(USER_SCORE).score(id) == null ? 0 : redisTemplate.boundZSetOps(USER_SCORE).score(id).intValue();
    }


    private String getIDByOpenId(String openid) {
        AssertUtil.isTrue(StringUtils.isBlank(openid), "openid不能为空!");
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("id")
                .eq("open_id", openid);
        User user = applyMapper.selectOne(wrapper);
        return user.getId();
    }


}
