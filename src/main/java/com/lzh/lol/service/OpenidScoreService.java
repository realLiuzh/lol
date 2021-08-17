package com.lzh.lol.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzh.lol.mapper.OpenidScoreMapper;
import com.lzh.lol.po.OpenidScore;
import org.springframework.stereotype.Service;

@Service
public class OpenidScoreService extends ServiceImpl<OpenidScoreMapper,OpenidScore> implements IService<OpenidScore> {
}
