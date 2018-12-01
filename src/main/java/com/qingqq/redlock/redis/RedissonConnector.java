package com.qingqq.redlock.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 作者: 顷强强
 * 日期：2018/11/30.
 * 邮件：qiangqiang@leoao.com
 * TODO redis客户端连接器
 */
@Component
public class RedissonConnector {

    RedissonClient redisson;

    @PostConstruct
    public void init(){
        redisson = Redisson.create();
    }

    @Bean
    public RedissonClient getClient(){
        return redisson;
    }
}
