package com.lzh.lol.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzh.lol.dto.MyBetDTO;
import com.lzh.lol.mapper.CompetitionMapper;
import com.lzh.lol.mapper.TeamMapper;
import com.lzh.lol.po.Bet;
import com.lzh.lol.mapper.BetMapper;
import com.lzh.lol.po.Competition;
import com.lzh.lol.po.Team;
import com.lzh.lol.utils.AssertUtil;
import com.lzh.lol.utils.GetIDByUtil;
import com.lzh.lol.vo.BetVO;
import com.lzh.lol.vo.MyBetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lzh
 * @since 2021-08-23
 */
@Service
public class BetService {

    private static final Integer MIN_SCORE = 0;
    private static final Integer MAX_SCORE = 1000;

    public static final Integer BET_SUCCESS_RATE = 2;
    public static final Integer BET_FAIL = 0;

    @Value("${bet.profit}")
    public double profit;


    @Resource
    private BetMapper betMapper;

    @Resource
    private TeamService teamService;

    @Resource
    private TeamMapper teamMapper;

    @Resource
    private CompetitionMapper competitionMapper;

    @Resource
    private CreditsService creditsService;

    @Resource
    private CompetitionService competitionService;

    @Autowired
    private GetIDByUtil getIDByUtil;



    //用户下注
    @Transactional(propagation = Propagation.REQUIRED)
    public void buildUserBet(BetVO betVO) {
        //校验参数
        AssertUtil.isTrue(betVO.getBetScore() < MIN_SCORE || betVO.getBetScore() > MAX_SCORE, "下注分数不合法");
        AssertUtil.isTrue(betVO.getBetScore() > creditsService.getCredit(betVO.getOpenid()), "您的积分不足");
        AssertUtil.isTrue(betVO.getBetTeamId() == null, "请选择下注队伍");
        //判断下注人员是否是队员
        AssertUtil.isTrue(checkIsMember(betVO.getOpenid(), betVO.getCompetitionId()), "不符合下注条件!");
        Bet bet = new Bet(null, betVO.getOpenid(), betVO.getCompetitionId(), betVO.getBetTeamId(), betVO.getBetScore(), new Date());


        betMapper.insert(bet);

        //扣除积分
        creditsService.addScore(betVO.getOpenid(), (-1) * (betVO.getBetScore()));

    }

    private boolean checkIsMember(String openid, Integer competitionId) {
        String id = getIDByUtil.getIDByOpenId(openid);
        Competition competition = competitionMapper.selectById(competitionId);
        List<String> list1 = teamService.getMemberId(competition.getTeamId1());
        if (list1.contains(id)) return true;
        List<String> list2 = teamService.getMemberId(competition.getTeamId2());
        if (list2.contains(id)) return true;

        QueryWrapper<Team> wrapper = new QueryWrapper<>();
        wrapper.eq("id", competition.getTeamId1())
                .or()
                .eq("id", competition.getTeamId2())
                .select("captain_id");
        List<String> list = (List<String>) (List) teamMapper.selectObjs(wrapper);

        return list.contains(id);
    }


    public List<MyBetVO> myBat(String openid) {
        List<MyBetDTO> myBetDTO = betMapper.myBet(openid);

        if (myBetDTO.isEmpty()) return new ArrayList<>();

        return buildMyBetVOBYMyBetDTO(myBetDTO);
    }

    private List<MyBetVO> buildMyBetVOBYMyBetDTO(List<MyBetDTO> myBetDTO) {

        return myBetDTO.stream().map(o -> new MyBetVO(
                competitionService.selectTeamNameById(o.getTeamId1()),
                competitionService.selectTeamNameById(o.getTeamId2()),
                o.getWinId() == null ? null : competitionService.selectTeamNameById(o.getWinId()),
                competitionService.selectTeamNameById(o.getBetTeamId()),
                o.getBetScore(),
                o.getCreateDate(),
                o.getWinId() == null ? null : o.getWinId().compareTo(o.getBetTeamId()) == 0 ? o.getBetScore() * BET_SUCCESS_RATE : BET_FAIL
        )).collect(Collectors.toList());

    }


    public void influenceOfBetting(Integer competitionId, Integer winId) {
        AssertUtil.isTrue(competitionId == null || winId == null, "参数有误!");
        QueryWrapper<Bet> wrapper = new QueryWrapper<>();
        wrapper.select("openid","bet_score")
                .eq("competition_id", competitionId)
                .eq("bet_team_id", winId);
        List<Bet> betList = betMapper.selectList(wrapper);
        List<Bet> list = betList.stream().peek(bet -> bet.setBetScore(bet.getBetScore() * profit)).collect(Collectors.toList());
        addBetScore(list);
    }

    private void addBetScore(List<Bet> list) {
        for (Bet bet : list) {
            creditsService.addScore(bet.getOpenid(), bet.getBetScore());
        }
    }


}
