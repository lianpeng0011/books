package com.lianpeng.learning.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author lianp
 * @date 2019/6/20 13:40
 * @since
 **/
public class RpcProxyHandler extends ChannelInboundHandlerAdapter {

    private Object respones;

    public Object getRespones() {
        return respones;
    }

    @Override
    public void channelRead( ChannelHandlerContext ctx, Object msg ) throws Exception {
        respones = msg;
    }

    @Override
    public void exceptionCaught( ChannelHandlerContext ctx, Throwable cause ) throws Exception {
        System.out.println("client exception is general");
    }
}
