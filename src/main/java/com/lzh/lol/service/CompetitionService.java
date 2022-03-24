package com.lzh.lol.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzh.lol.mapper.CompetitionMapper;
import com.lzh.lol.mapper.TeamMapper;
import com.lzh.lol.po.Competition;
import com.lzh.lol.po.Team;
import com.lzh.lol.utils.AssertUtil;
import com.lzh.lol.vo.CompetitionListVO;
import com.lzh.lol.vo.CompetitionVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lzh
 * @since 2021-08-23
 */
@Service
public class CompetitionService  {

    @Resource
    private CompetitionMapper competitionMapper;

    @Resource
    private TeamMapper teamMapper;


    //查询所有的赛事
    public List<CompetitionListVO> selectAllCompetition() {
        QueryWrapper<Competition> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "team_id_1", "team_id_2", "create_date")
                .ge("create_date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
                .orderByAsc("create_date");

        //return buildCompetitionVO(competitionMapper.selectList(queryWrapper));
        List<Competition> list = competitionMapper.selectList(queryWrapper);
        return list.stream().map(this::buildCompetitionListVO).collect(Collectors.toList());
    }

    //查询某个赛事
    public CompetitionVO selectOneCompetition(Integer id) {
        Competition competition = competitionMapper.selectById(id);

        return buildCompetitionVO(competition);
    }

    //将Competition转换成CompetitionVO
    private CompetitionVO buildCompetitionVO(Competition competition) {
        return new CompetitionVO(competition.getId(),
                competition.getTeamId1(),
                selectTeamNameById(competition.getTeamId1()),
                competition.getTeamId2(),
                selectTeamNameById(competition.getTeamId2()),
                competition.getCreateDate());
    }


    //将Competition转换成CompetitionListVO
    private CompetitionListVO buildCompetitionListVO(Competition competition) {

        return new CompetitionListVO(competition.getId(), selectTeamNameById(competition.getTeamId1()), selectTeamNameById(competition.getTeamId2()), competition.getCreateDate());

    }

    //通过team_id查询team_name
    public String selectTeamNameById(Integer id) {
        QueryWrapper<Team> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("team_name")
                .eq("id", id);

        List<Object> list = teamMapper.selectObjs(queryWrapper);
        AssertUtil.isTrue(list.isEmpty(), "该战队不存在,系统错误");

        return (String) list.get(0);

    }


}
