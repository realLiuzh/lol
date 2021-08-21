package com.lzh.lol.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzh.lol.mapper.ApplyMapper;
import com.lzh.lol.po.User;
import com.lzh.lol.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;


/**
 * @ClassName ApplyService
 * @Description TODO
 * @Author lzh
 * @Date 2021/7/27 15:47
 */
@Service
public class ApplyService {

    @Resource
    private ApplyMapper applyMapper;


    /**
     * @param user
     * @Description //TODO 报名参赛
     * @Date 2021/7/27 16:25
     * @Return void
     */
    public void apply(User user) {
        //1.检验参数
        checkApplyParams(user);
        //2.设置默认值
        setDefaultValue(user);
        //2.插入数据
        applyMapper.insert(user);
    }


    /**
     * @Description //TODO 是否已经报名
     * @Date 2021/8/1 15:59
     * @param openid
     * @Return boolean
     */
    public boolean isApply(String openid){
        return applyMapper.selectCount(new QueryWrapper<User>().eq("open_id", openid))!=0;
    }


    private void setDefaultValue(User user) {
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setIsValid(1);
    }

    /**
     * @param user
     * @Description //TODO 检验参数
     * @Date 2021/7/27 16:25
     * @Return void
     */
    private void checkApplyParams(User user) {
        AssertUtil.isTrue(StringUtils.isBlank(user.getId()) || !user.getId().matches("^\\d{10}$"), "学号输入有误!");
        //检查该学号是否注册过
        AssertUtil.isTrue(this.isApply(user.getOpenid()),"当前用户已报名");
        AssertUtil.isTrue(StringUtils.isBlank(user.getName())||!user.getName().matches("^[\\u2E80-\\u9FFF]+$"), "姓名输入有误!");
        //AssertUtil.isTrue(StringUtils.isBlank(user.getGender()),"性别不能为空");
//        AssertUtil.isTrue(StringUtils.isBlank(user.getPhone()) || !user.getPhone().matches("^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$"),
//                "手机号输入有误!");
    }
}
