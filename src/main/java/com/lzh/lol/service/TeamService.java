package com.lzh.lol.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lzh.lol.mapper.ApplyMapper;
import com.lzh.lol.mapper.TeamMapper;
import com.lzh.lol.po.Competition;
import com.lzh.lol.po.Team;
import com.lzh.lol.po.User;
import com.lzh.lol.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName TeamService
 * @Description TODO
 * @Author lzh
 * @Date 2021/8/2 18:06
 */
@Service
public class TeamService {

    @Resource
    private TeamMapper teamMapper;

    @Resource
    private ApplyMapper applyMapper;

    @Resource
    private ApplyService applyService;

    /**
     * @param team
     * @Description //TODO 创建战队
     * @Date 2021/8/2 18:07
     * @Return void
     */
    public void buildTeam(Team team, String openid) {
        //1.逻辑校验
        logicCheck(team, openid);

        List<String> list = team.getMemberId();
        list.add(team.getCaptainId());
        //3.检查队友中是否有已经参加战队
        AssertUtil.isTrue(!checkIsFreedom(list), "战队成员中有非自由人!");

        team.setCreateTime(new Date());
        team.setIsValid(1);
        teamMapper.insert(team);

        //4.为每个队员设置战队
        setTeamForMember(team.getId(), list);
    }

    private void setTeamForMember(int teamId, List<String> memberList) {
        for (String item : memberList) {
            applyMapper.updateById(new User(item, teamId));
        }

    }

    private boolean checkIsFreedom(List<String> memberId) {

        for (String id : memberId) {
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("id", id);
            User user = applyMapper.selectOne(wrapper);
            if (user.getTeam() != null) {
                return false;
            }
        }
        return true;
    }


    private boolean checkIsFreedom(List<String> memberId, Integer teamId) {

        for (String id : memberId) {
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("id", id);
            User user = applyMapper.selectOne(wrapper);
            if (user.getTeam() != null && !user.getTeam().equals(teamId)) {
                return false;
            }
        }
        return true;
    }


    private boolean checkIsValid(List<String> memberId) {
        for (String id : memberId) {
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("id", id);
            if (applyMapper.selectCount(wrapper) == 0) {
                return false;
            }
        }
        return true;
    }

    private boolean checkCaptainUnify(String captainId, String openid) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("open_id", openid)
                .select("id");
        User user = applyMapper.selectOne(wrapper);
        AssertUtil.isTrue(user == null, "队长尚未报名比赛!");

        return captainId.equals(user.getId());
    }

    private boolean checkTeamValid(String teamName) {
        QueryWrapper<Team> teamQueryWrapper = new QueryWrapper<>();
        teamQueryWrapper.eq("team_name", teamName)
                .eq("is_valid", 1);
        return teamMapper.selectCount(teamQueryWrapper) == 0;
    }

    private boolean checkTeamValid(String teamName, Integer id) {
        QueryWrapper<Team> teamQueryWrapper = new QueryWrapper<>();
        teamQueryWrapper.eq("team_name", teamName)
                .eq("is_valid", 1)
                .ne("id", id);
        return teamMapper.selectCount(teamQueryWrapper) == 0;

    }


    /**
     * @param openid
     * @Description //TODO 查看战队信息
     * @Date 2021/8/2 21:04
     * @Return com.lzh.lol.po.Team
     */
    public Team getTeamInfo(String openid) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("open_id", openid)
                .select("id", "team");
        User user = applyMapper.selectOne(wrapper);
        AssertUtil.isTrue(user == null, "该用户尚未报名!");
        Integer teamId = user.getTeam();
        //AssertUtil.isTrue(teamId == null, "该用户尚未参加战队!");
        if(teamId==null) return null;
        QueryWrapper<Team> teamQueryWrapper = new QueryWrapper<>();
        teamQueryWrapper.eq("id", teamId)
                .eq("is_valid", 1)
                .select("id", "team_name", "captain_id");
        Team team = teamMapper.selectOne(teamQueryWrapper);
        AssertUtil.isTrue(team == null, "该战队并不存在!");
        team.setMemberId(getMemberId(teamId));
        return team;
    }

    /**
     * @param teamId
     * @Description //TODO 获取某个战队下的队员
     * @Date 2021/8/2 21:11
     * @Return java.util.List<java.lang.String>
     */
    public List<String> getMemberId(Integer teamId) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("team", teamId)
                .select("id");
        //amazing
        List<String> list = (List<String>) (List) applyMapper.selectObjs(wrapper);

        QueryWrapper<Team> wrapper1=new QueryWrapper<>();
        wrapper1.eq("id",teamId)
                .select("captain_id");
        List<String> list1 = (List<String>)(List) teamMapper.selectObjs(wrapper1);
        list.removeAll(list1);
        return list;
    }

    /**
     * @param openid
     * @Description //TODO 退出战队
     * @Date 2021/8/2 21:24
     * @Return void
     */
    public void teamExit(String openid) {
        //先判断这个人是否报名
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("open_id", openid);
        User user = applyMapper.selectOne(wrapper);
        AssertUtil.isTrue(user == null, "该用户尚未报名!");
        AssertUtil.isTrue(user.getTeam() == null, "该用户尚未加入战队!");

        //判断是否是队长
        QueryWrapper<Team> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("captain_id", user.getId())
                .eq("is_valid", 1)
                .select("id");
        //Integer integer = teamMapper.selectCount(wrapper1);
        Team team = teamMapper.selectOne(wrapper1);
        if (team != null) {
            //是队长
            List<String> list = getMemberId(user.getTeam());
            for (String item : list) {
                memberExitTeam(item);
            }
            //删除这个队伍
            teamMapper.deleteById(team.getId());
        }
        user.setTeam(null);
        applyMapper.updateById(user);

    }

    private void memberExitTeam(String item) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", item);
        applyMapper.update(new User(), wrapper);
    }


    /**
     * @param team
     * @param openid
     * @Description //TODO 更新战队
     * @Date 2021/8/3 13:38
     * @Return void
     */
    public void updateTeam(Team team, String openid) {

        //1.逻辑校验
        logicCheck(team, openid);

        List<String> list = team.getMemberId();
        list.add(team.getCaptainId());
        AssertUtil.isTrue(!checkIsFreedom(list, team.getId()), "战队成员中有非自由人!");

        //2.更新数据
        teamMapper.updateById(team);


        //2.05删除每个队员的战队
        setNullForMember(team.getId());
        //2.1为每个队员设置战队
        setTeamForMember(team.getId(),list);
    }

    private void setNullForMember(Integer id) {
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("team",id);
        applyMapper.update(new User(),wrapper);
    }

    private void logicCheck(Team team, String openid) {
        //-1.查看队长是否报名了比赛
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("open_id", openid);
        AssertUtil.isTrue(applyMapper.selectCount(wrapper) == 0, "您尚未报名比赛!");
        //0.基本参数校验
        AssertUtil.isTrue(team == null, "战队信息为空!");
        AssertUtil.isTrue(StringUtils.isBlank(openid), "openid为空!");

        //0.25 战队名字不能为空
        AssertUtil.isTrue(StringUtils.isBlank(team.getTeamName()), "战队名字不能为空!");
        //0.5 战队名字是否重复
        if (team.getId() == null) {
            AssertUtil.isTrue(!checkTeamValid(team.getTeamName()), "战队名字已被占用!");
        } else {
            AssertUtil.isTrue(!checkTeamValid(team.getTeamName(), team.getId()), "战队名字已被占用!");
        }

        //1.查看队长是否和当前用户统一
        AssertUtil.isTrue(!checkCaptainUnify(team.getCaptainId(), openid), "您没有权限,队长必须为本人!");

        //2.检查队友中是否有未注册的成员
        AssertUtil.isTrue(!checkIsValid(team.getMemberId()), "战队成员尚未全部报名!");

    }


/*    public void selectTeamIdMapName(){
        QueryWrapper<Team> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","team_name");
        List<Map<String, Object>> list = teamMapper.selectMaps(queryWrapper);
        Map<String, Object> stringObjectMap = list.get(1);
        Set<Map.Entry<String, Object>> set = stringObjectMap.entrySet();
        set.forEach(o-> System.out.println(o.getKey()+"  "+o.getValue()));
*//*        maps.forEach(stringObjectMap -> {
            Set<Map.Entry<String, Object>> set = stringObjectMap.entrySet();
            set.forEach(o-> System.out.println(o.getKey()+"   "+o.getValue()));
        });*//*
    }*/


}
