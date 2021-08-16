package com.lzh.lol.controller;

import com.lzh.lol.base.ResultInfo;
import com.lzh.lol.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @ClassName PthotoController
 * @Description TODO
 * @Author lzh
 * @Date 2021/8/2 17:17
 */
@RestController
public class PhotoController {
    Logger logger = LoggerFactory.getLogger(PhotoController.class);

    @Resource
    private FileService fileService;

    @GetMapping("/file")
    public void file(String name, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info("开始传输图片:{}",name);
        fileService.getFile(name,response);
    }

    @GetMapping("/random/file")
    public ResultInfo<Map> randomFile(){
        HashMap<String, Object> map = new HashMap<>();
        Random random = new Random();
        int i = random.nextInt(20)+1000;
        map.put("photo","http://www.csomlh.com:9099/"+i+".png");
        return new ResultInfo<>(map);
    }
}
