package com.lzh.lol;

import com.lzh.lol.service.BetService;
import com.lzh.lol.vo.MyBetVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author 志昊的刘
 * @version 1.0
 * @description: TODO
 * @date 2021/8/24 16:10
 */
@SpringBootTest
public class BetServiceTest {

    @Autowired
    private BetService betService;

    @Test
    public void test1(){
        List<MyBetVO> list = betService.myBat("oxaes5CMTEX6jH-BznONsjx2mwIA");
        list.forEach(System.out::println);



    }

}
