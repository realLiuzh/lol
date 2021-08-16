package com.lzh.lol.controller;

import com.alibaba.fastjson.JSON;
import com.lzh.lol.base.ResultInfo;
import com.lzh.lol.po.Team;
import com.lzh.lol.service.TeamService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TeamController
 * @Description TODO
 * @Author lzh
 * @Date 2021/8/2 17:55
 */
@RestController
public class TeamController {

    @Resource
    private TeamService teamService;

    /**
     * @param map
     * @Description //TODO 创建战队
     * @Date 2021/8/2 21:21
     * @Return com.lzh.lol.base.ResultInfo<java.lang.String>
     */
    @PostMapping("/team/build")
    public ResultInfo<String> buildTeam(@RequestBody Map<String, Object> map) {
        Team team = JSON.parseObject(JSON.toJSONString(map.get("team")), Team.class);
        List<String> list = team.getMemberId();
        list.removeIf(o -> StringUtils.isBlank((String) o));
        team.setMemberId(list);
        String openid = (String) map.get("openid");
        if (team.getId() == null) {
            teamService.buildTeam(team, openid);
        }else {
            //更新战队成员
            teamService.updateTeam(team,openid);
        }

        return new ResultInfo<>("战队创建成功!");
    }

    /**
     * @param openid
     * @Description //TODO 根据openid来查询战队信息
     * @Date 2021/8/2 17:56
     * @Return com.lzh.lol.base.ResultInfo
     */
    @GetMapping("/team/info")
    public ResultInfo<Team> getTeamInfo(@RequestParam("openid") String openid) {
        Team teamInfo = teamService.getTeamInfo(openid);
        if(teamInfo==null) return new ResultInfo<Team>(400,"用户未参加战队!",null);
        List<String> list = teamInfo.getMemberId();
        while (list.size()<=6){
            list.add("");
        }
        teamInfo.setMemberId(list);
        return new ResultInfo<>(teamInfo);
    }

    /**
     * @param openid
     * @Description //TODO 退出战队
     * @Date 2021/8/3 13:27
     * @Return com.lzh.lol.base.ResultInfo<java.lang.String>
     */
    @GetMapping("/team/exit")
    public ResultInfo<String> teamExit(@RequestParam("openid") String openid) {
        teamService.teamExit(openid);
        return new ResultInfo<>();
    }


}
