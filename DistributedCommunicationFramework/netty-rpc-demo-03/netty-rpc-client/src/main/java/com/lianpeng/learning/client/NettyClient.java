package com.lianpeng.learning.client;

import com.lianpeng.learning.protocol.RpcRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * @author lianp
 * @date 2019/6/19 15:00
 * @since
 **/
public class NettyClient {


    public static Object send( RpcRequest request ){

        EventLoopGroup clientGroup = new NioEventLoopGroup();
        final RpcProxyHandler handler = new RpcProxyHandler();
        try {
            Bootstrap boot = new Bootstrap();

            boot.group( clientGroup )
                    .channel( NioSocketChannel.class )
                    .handler( new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel( SocketChannel ch ) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast( new LengthFieldBasedFrameDecoder( Integer.MAX_VALUE, 0, 4, 0, 4 ) );
                            pipeline.addLast( new LengthFieldPrepender( 4 ) );
                            pipeline.addLast( "encoder", new ObjectEncoder() );
                            pipeline.addLast( "decoder", new ObjectDecoder( Integer.MAX_VALUE, ClassResolvers.cacheDisabled( null ) ) );
                            pipeline.addLast( handler );
                        }
                    } );
            ChannelFuture future = boot.connect( "127.0.0.1", 30088 ).sync();
            future.channel().writeAndFlush( request );
            future.channel().closeFuture().sync();
        } catch ( InterruptedException e ) {
            System.out.println( "连接失败" + e.getMessage() );
        } finally {
            clientGroup.shutdownGracefully();
        }
        return handler.getRespones();
    }
}
