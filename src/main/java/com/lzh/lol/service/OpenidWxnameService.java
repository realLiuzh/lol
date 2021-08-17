package com.lzh.lol.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzh.lol.mapper.OpenidWxnameMapper;
import com.lzh.lol.po.OpenidWxname;
import org.springframework.stereotype.Service;

@Service
public class OpenidWxnameService extends ServiceImpl<OpenidWxnameMapper,OpenidWxname> implements IService<OpenidWxname> {
}
