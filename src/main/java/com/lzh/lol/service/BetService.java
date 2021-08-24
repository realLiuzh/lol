package com.lzh.lol.service;

import com.lzh.lol.dto.MyBetDTO;
import com.lzh.lol.po.Bet;
import com.lzh.lol.mapper.BetMapper;
import com.lzh.lol.utils.AssertUtil;
import com.lzh.lol.vo.BetVO;
import com.lzh.lol.vo.MyBetVO;
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


    @Resource
    private BetMapper betMapper;

    @Resource
    private CreditsService creditsService;

    @Resource
    private CompetitionService competitionService;

    //用户下注
    @Transactional(propagation = Propagation.REQUIRED)
    public void buildUserBet(BetVO betVO) {
        //校验参数
        AssertUtil.isTrue(betVO.getBetScore() < MIN_SCORE || betVO.getBetScore() > MAX_SCORE, "下注分数不合法");
        AssertUtil.isTrue(betVO.getBetScore() > creditsService.getCredit(betVO.getOpenid()), "您的积分不足");
        Bet bet = new Bet(null, betVO.getOpenid(), betVO.getCompetitionId(), betVO.getBetTeamId(), betVO.getBetScore(), new Date());

        //扣除积分
        betMapper.insert(bet);
/*
        Integer i=null;
        i.toString();*/
        creditsService.addScore(betVO.getOpenid(), (-1) * (betVO.getBetScore()));

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


}
