package com.lianpeng.learning.service;

import com.lianpeng.learning.api.ICalculateService;

/**
 * @author lianp
 * @date 2019/6/19 13:50
 * @since
 **/
public class CalculateServiceImpl implements ICalculateService {

    public int add( int a, int b ) {
        return a + b;
    }

    public int sub( int a, int b ) {
        return a - b;
    }

    public int mult( int a, int b ) {
        return a * b;
    }

    public int div( int a, int b ) {
        return a / b;
    }
}
