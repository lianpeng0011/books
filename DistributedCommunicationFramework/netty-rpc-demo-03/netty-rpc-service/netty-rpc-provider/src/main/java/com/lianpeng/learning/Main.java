package com.lianpeng.learning;

import com.lianpeng.learning.config.ServiceConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author lianp
 * @date 2019/6/19 14:06
 * @since
 **/
public class Main {


    public static void main( String[] args ) {
        ApplicationContext context = new AnnotationConfigApplicationContext( ServiceConfig.class );
        ( (AnnotationConfigApplicationContext) context ).refresh();
    }
}
