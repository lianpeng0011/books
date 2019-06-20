package com.lianpeng.learning.rpc;


import com.lianpeng.learning.register.RegisterHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * @author lianp
 * @date 2019/6/19 11:45
 * @since
 **/
@Configurable
public class NettyService implements InitializingBean , DisposableBean {

    private int port = 30088;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workGroup;

    public NettyService( int port ) {
        this.port = port;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        start();
    }

    private void start() throws InterruptedException {
        bossGroup = new NioEventLoopGroup();
        workGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group( bossGroup, workGroup )
                .channel( NioServerSocketChannel.class )
                .childHandler( new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel( SocketChannel ch ) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        /**
                         * maxFrameLength 获取报文最大长度，如果大与次值。抛出TooLongFrameException
                         * lengthFieldOffset 长度字段偏移量：即对应的长度字段在整个消息数据中的位置
                         * lengthFieldLength 长度字段的长度。如 int 4 long 8
                         * LengthAdjustment 长度字段的补偿值
                         * initialBytesToStrip 从解码帧中去除的第一个字节数
                         */
                        pipeline.addLast( new LengthFieldBasedFrameDecoder( Integer.MAX_VALUE, 0, 4, 0, 4 ) );
                        pipeline.addLast( new LengthFieldPrepender( 4 ) );
                        pipeline.addLast( "encode",new ObjectEncoder() );
                        pipeline.addLast( "decode", new ObjectDecoder( Integer.MAX_VALUE, ClassResolvers.cacheDisabled( null ) ) );
                        pipeline.addLast( new RegisterHandler() );
                    }
                } )
                .option( ChannelOption.SO_BACKLOG, 128 )
                .childOption( ChannelOption.SO_KEEPALIVE, true );
        ChannelFuture future = b.bind(port).sync();
        System.out.println( "GP RPC Register start listen at " + port );
        future.channel().closeFuture().sync();
    }


    @Override
    public void destroy() throws Exception {
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
    }
}
