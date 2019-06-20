package com.lianpeng.learning.register;

import com.lianpeng.learning.protocol.RpcRequest;
import com.lianpeng.learning.service.CalculateServiceImpl;
import com.lianpeng.learning.service.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Configurable;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lianp
 * @date 2019/6/19 13:19
 * @since
 **/
@Configurable
public class RegisterHandler extends ChannelInboundHandlerAdapter {

    private static Map<String, Object> registeyMaps = new ConcurrentHashMap<String, Object>();

    static {
        registeyMaps.put( "com.lianpeng.learning.api.IHelloService", new HelloServiceImpl() );
        registeyMaps.put( "com.lianpeng.learning.api.ICalculateService", new CalculateServiceImpl() );
    }

    @Override
    public void channelRead( ChannelHandlerContext ctx, Object msg ) throws Exception {
        RpcRequest rpcRequest = (RpcRequest) msg;
        Object o = registeyMaps.get( rpcRequest.getClassName() );
        if ( o == null ) {
            new RuntimeException( "service not found " );
        }
        String methodName = rpcRequest.getMethodName();
        Object[] params = rpcRequest.getParams();
        Method method = o.getClass().getMethod( methodName, rpcRequest.getTypes() );
        Object result = method.invoke( o, params );
        ctx.write( result );
        ctx.flush();
        ctx.close();
    }

    @Override
    public void exceptionCaught( ChannelHandlerContext ctx, Throwable cause ) throws Exception {
        System.out.print( cause.getMessage() );
    }


}
