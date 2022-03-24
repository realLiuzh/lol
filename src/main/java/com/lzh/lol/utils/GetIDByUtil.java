package com.lzh.lol.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzh.lol.mapper.ApplyMapper;
import com.lzh.lol.po.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName GetIDByUtil
 * @Description TODO
 * @Author lzh
 * @Date 2021/8/2 17:41
 */
@Component
public class GetIDByUtil {

    @Resource
    private ApplyMapper applyMapper;

    public String getIDByOpenId(String openid) {
        AssertUtil.isTrue(StringUtils.isBlank(openid), "openid不能为空!");
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("id")
                .eq("open_id", openid);
        User user = applyMapper.selectOne(wrapper);
        return user.getId();
    }

}
