package com.lianpeng.learning;

import com.lianpeng.learning.api.IHelloService;
import com.lianpeng.learning.proxy.ProxyHandler;

/**
 * @author lianp
 * @date 2019/6/19 15:00
 * @since
 **/
public class Main {


    public static void main( String[] args ) {
        IHelloService helloService = (IHelloService) new ProxyHandler().createProxy( IHelloService.class );
        System.out.print( helloService.sayHello( "lianpeng" ) );
    }
}
