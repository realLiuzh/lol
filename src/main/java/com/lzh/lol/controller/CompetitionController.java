package com.lzh.lol.controller;


import com.lzh.lol.base.ResultInfo;
import com.lzh.lol.service.CompetitionService;
import com.lzh.lol.vo.CompetitionListVO;
import com.lzh.lol.vo.CompetitionVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lzh
 * @since 2021-08-23
 */
@RestController
public class CompetitionController {
    
    @Resource
    private CompetitionService competitionService;

    /*** 
     * @description: 查询所有的赛事 
     * @param:  
     * @return: com.lzh.lol.base.ResultInfo<java.util.List<com.lzh.lol.vo.CompetitionVO>> 
     * @author 志昊的刘
     * @date: 2021/8/23 18:15
     */ 
    @GetMapping("/competition/list")
    public ResultInfo<List<CompetitionListVO>> competitionList(){
        List<CompetitionListVO> list = competitionService.selectAllCompetition();
        return new ResultInfo<>(list);
    }


    /***
     * @description: 查询某个赛事
     * @param: id
     * @return: com.lzh.lol.base.ResultInfo<com.lzh.lol.vo.CompetitionVO>
     * @author 志昊的刘
     * @date: 2021/8/23 18:28
     */
    @GetMapping("competition")
    public ResultInfo<CompetitionVO> competition(@Param("id") Integer id){
        CompetitionVO competitionVO = competitionService.selectOneCompetition(id);
        return new ResultInfo<>(competitionVO);
    }



}

