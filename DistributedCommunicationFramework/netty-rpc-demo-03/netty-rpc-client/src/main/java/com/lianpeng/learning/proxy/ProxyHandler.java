package com.lianpeng.learning.proxy;

import com.lianpeng.learning.client.NettyClient;
import com.lianpeng.learning.protocol.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author lianp
 * @date 2019/6/19 15:25
 * @since
 **/
public class ProxyHandler implements InvocationHandler {

    public Object createProxy( Class<?> clazz ) {

        Class<?>[] interfaces = clazz.isInterface() ? new Class[]{ clazz } : clazz.getInterfaces();

        return Proxy.newProxyInstance( clazz.getClassLoader(), interfaces, this );
    }


    @Override
    public Object invoke( Object proxy, Method method, Object[] args ) throws Throwable {
        Object result;
        try {
            RpcRequest request = new RpcRequest();
            request.setClassName( method.getDeclaringClass().getName() );
            request.setMethodName( method.getName() );
            request.setTypes( method.getParameterTypes() );
            request.setParams( args );

            result = NettyClient.send( request );
        } catch ( Exception e ) {
            System.out.println( e.getMessage() );
            throw e;
        }
        return result;
    }


}
