package com.lianpeng.learning.service;

import com.lianpeng.learning.api.IHelloService;

/**
 * @author lianp
 * @date 2019/6/19 13:49
 * @since
 **/
public class HelloServiceImpl implements IHelloService {

    @Override
    public String sayHello( String name ) {
        return "Hello" + name;
    }
}
