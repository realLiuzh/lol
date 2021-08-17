package com.lzh.lol.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzh.lol.mapper.OpenidDateMapper;
import com.lzh.lol.po.OpenidDate;
import org.springframework.stereotype.Service;

@Service
public class OpenidDateService extends ServiceImpl<OpenidDateMapper,OpenidDate> implements IService<OpenidDate> {
}
