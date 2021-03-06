package xyz.zsq.netty.tcp.reconnect;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;

import java.util.concurrent.TimeUnit;

/**
 * Aubergine
 * zhaoshuangquan@allinmd.cn
 * 2017-06-16 15:58
 */
public class ConnectionListener implements ChannelFutureListener {

    private Client client;
    public ConnectionListener(Client client) {
        this.client = client;
    }
    @Override
    public void operationComplete(ChannelFuture channelFuture) throws Exception {
        if (!channelFuture.isSuccess()) {
            System.out.println("Reconnect");
            final EventLoop loop = channelFuture.channel().eventLoop();
            loop.schedule(new Runnable() {
                @Override
                public void run() {
                    client.createBootstrap(new Bootstrap(), loop);
                }
            }, 1L, TimeUnit.SECONDS);
        }
    }
}
