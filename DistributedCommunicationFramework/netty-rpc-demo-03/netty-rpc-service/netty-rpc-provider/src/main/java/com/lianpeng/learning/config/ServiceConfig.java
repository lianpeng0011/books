package com.lianpeng.learning.config;

import com.lianpeng.learning.register.RegisterHandler;
import com.lianpeng.learning.rpc.NettyService;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author lianp
 * @date 2019/6/19 13:28
 * @since
 **/
@Configurable
@ComponentScan( "com.lianpeng.learning" )
public class ServiceConfig {


    @Bean( "nettyService" )
    public NettyService nettyService(){
        return new NettyService( 30088 );
    }

    @Bean( "registerHandler" )
    public RegisterHandler registerHandler(){
        return new RegisterHandler();
    }

}
