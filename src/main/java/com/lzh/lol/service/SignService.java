package com.lzh.lol.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzh.lol.mapper.ApplyMapper;
import com.lzh.lol.po.User;
import com.lzh.lol.utils.AssertUtil;
import com.lzh.lol.vo.SignRankVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @ClassName SignService
 * @Description TODO 签到
 * @Author lzh
 * @Date 2021/7/27 19:36
 */
@Service
public class SignService {
    Logger logger = LoggerFactory.getLogger(SignService.class);


    private static final String BIND_OPENID_WXNAME="OPENID:WXNAME";

    @Resource
    private RedisTemplate redisTemplate;

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
     * @param date
     * @Description //TODO 用户签到 (测试通过)
     * @Date 2021/7/27 19:52
     * @Return boolean
     */
    public boolean doSign(String id, LocalDate date) {
        AssertUtil.isTrue(checkSign(id, date),"请勿重复签到!");
        int offset = date.getDayOfMonth() - 1;

        Boolean flag = redisTemplate.opsForValue().setBit(buildSignKey(id, date), offset, true);
        //将用户id和签到的时间存入redis中
        redisTemplate.opsForZSet().add(formatDate(LocalDate.now(), "MMdd"), id, transferDouble());

        return flag;
    }

    private double transferDouble() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();


        Duration between = Duration.between(Instant.ofEpochMilli(zero.getTime()), Instant.now());
        return (double) between.toMillis();
    }

    /**
     * @param id
     * @param date
     * @Description //TODO 检查用户是否签到 (测试通过)
     * @Date 2021/7/27 20:07
     * @Return boolean
     */
    public boolean checkSign(String id, LocalDate date) {
        int offset = date.getDayOfMonth() - 1;
        return redisTemplate.opsForValue().getBit(buildSignKey(id, date), offset);
    }

    /**
     * @param id
     * @param date
     * @Description //TODO 获取当月连续签到次数 (可能的错误原因:jedis没有使用格式化器
     * @Date 2021/7/27 20:19
     * @Return long
     */
    public long getContinuousSignCount(String id, LocalDate date) {
        int dayOfMonth = date.getDayOfMonth();
        String key = buildSignKey(id, date);
        BitFieldSubCommands bitFieldSubCommands = BitFieldSubCommands.create()
                .get(BitFieldSubCommands.BitFieldType.unsigned(dayOfMonth))
                .valueAt(0);
        List<Long> list = redisTemplate.opsForValue().bitField(key, bitFieldSubCommands);
        if (list == null || list.isEmpty()) {
            return 0;
        }
        int signCount = 0;
        long v = list.get(0) == null ? 0 : list.get(0);
        for (int i = dayOfMonth; i > 0; i--) {
            if (v >> 1 << 1 == v) {
                if (i != dayOfMonth) {
                    break;
                }
            } else {
                signCount++;
            }
            v >>= 1;
        }
        return signCount;
    }
   /* public long getContinuousSignCount(String id, LocalDate date) {
        int signCount = 0;
        String type = String.format("u%d", date.getDayOfMonth());
        List<Long> list = jedis.bitfield(buildSignKey(id, date), "GET", type, "0");
        if (list != null && list.size() > 0) {
            //取低位连续不为0的个数即为连续签到次数，需考虑当天尚未签到的情况
            long v = list.get(0) == null ? 0 : list.get(0);
            for (int i = 0; i < date.getDayOfMonth(); i++) {
                if (v >> 1 << 1 == v) {
                    //低位为0且非当天说明连续签到中断了
                    if (i > 0) break;
                } else {
                    signCount += 1;
                }
                v >>= 1;
            }
        }
        return signCount;
    }*/


    /**
     * @param id
     * @Description //TODO 获取签到排名
     * @Date 2021/7/31 16:11
     * @Return int
     */
    public int getRanking(String id) {
        AssertUtil.isTrue(!checkSign(id, LocalDate.now()), "您尚未签到,无法查看排名");
        //获取所有的值(签到时间)
       /* List<Long> list = redisTemplate.opsForHash().values(formatDate(LocalDate.now(), "MMdd"));
        Long[] arrays = (Long[]) list.toArray();
        Arrays.sort(arrays);
        for (int i = 0; i < arrays.length; i++) {
            if()
        }*/
        return redisTemplate.boundZSetOps(formatDate(LocalDate.now(), "MMdd")).rank(id).intValue() + 1;
    }


    /**
     * @param
     * @Description //TODO 获取签到的前三名
     * @Date 2021/7/31 17:05
     * @Return java.util.List<com.lzh.lol.vo.SignRankVo>
     */
    public List<SignRankVo> getTopThree() {
       /* Set<ZSetOperations.TypedTuple<String>> tuples = redisTemplate.boundZSetOps(formatDate(LocalDate.now(),"MMMdd")).rangeWithScores(0L, -1);
        if(tuples.size()==0) return new ArrayList<>();
        for (ZSetOperations.TypedTuple<String> tuple : tuples) {

            System.out.println(tuple.getValue() + " : " + tuple.getScore());
        }
        return new ArrayList<>();*/

        Set<ZSetOperations.TypedTuple<String>> tuples = redisTemplate.opsForZSet().rangeWithScores(formatDate(LocalDate.now(), "MMdd"), 0, 2);
        if (tuples == null || tuples.size() == 0) return new ArrayList<>();
        //目前是open_id+数字时间
        //要转换成微信名+人为时间
        List<SignRankVo> list = SetToList(tuples);
        //open_id-->微信名
        list = OpenIdTransferWxName(list);
        //时间转换
        list = abTimeTransferTime(list);
        return list;
    }

    private List<SignRankVo> OpenIdTransferWxName(List<SignRankVo> list) {
        List<SignRankVo> listt = new ArrayList<>();
        for (SignRankVo signRankVo : list) {
            signRankVo.setName((String) redisTemplate.opsForHash().get(BIND_OPENID_WXNAME,signRankVo.getName()));
            listt.add(signRankVo);
        }
        return listt;

    }

    private List<SignRankVo> abTimeTransferTime(List<SignRankVo> list) {
        List<SignRankVo> listt = new ArrayList<>();
        for (SignRankVo signRankVo : list) {
            signRankVo.setTime(abTimeTransferTime(signRankVo.getAbTime()));
            listt.add(signRankVo);
        }

        return listt;
    }

    private String abTimeTransferTime(Integer abTime){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(new Date(zero.getTime()+abTime));
    }

/*    private List<SignRankVo> idTransferName(List<SignRankVo> list) {
        List<SignRankVo> listt = new ArrayList<>();
        for (SignRankVo signRankVo : list) {
            signRankVo.setName(applyMapper.selectOne(new QueryWrapper<User>()
                    .eq("id", (signRankVo.getName()))
                    .select("name"))
                    .getName());
            listt.add(signRankVo);
        }
        return listt;

    }*/

    private List<SignRankVo> SetToList(Set<ZSetOperations.TypedTuple<String>> tuples) {
        List<SignRankVo> list = new ArrayList<>();
        for (ZSetOperations.TypedTuple<String> tuple : tuples) {
            list.add(new SignRankVo(tuple.getValue(), tuple.getScore().intValue()));
        }
        return list;
    }


    private String buildSignKey(String id, LocalDate date) {
        return String.format("u:sign:%s:%s", id, formatDate(date, "yyyyMM"));

    }

    private String formatDate(LocalDate date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(date);
    }



}
