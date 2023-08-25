package app.vcampus.client.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.json.JsonObjectDecoder;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Callable;

public class NettyClient implements Callable<NettyHandler> {
    private final String host;
    private final int port;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private static final EventLoopGroup workerGroup = new NioEventLoopGroup();

    @Override
    public NettyHandler call() {
        try {
            NettyHandler handler = new NettyHandler();

            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(@NotNull SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new JsonObjectDecoder()).addLast(handler);
                }
            });

            // Start the client.
            ChannelFuture f = b.connect(host, port).syncUninterruptibly(); // (5)

            return handler;
        } finally {
//            workerGroup.shutdownGracefully();
        }
    }
}
