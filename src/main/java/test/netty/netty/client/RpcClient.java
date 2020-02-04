package test.netty.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import test.netty.constants.AddressConstants;
import test.netty.netty.client.handler.RpcClientHandler;
import test.netty.netty.common.handler.BaseStringPackageInitializer;
import test.netty.rpc.message.AbstractMessageFormatter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 〈〉
 *
 * @author liujie
 * @create 2020/01/17 16:29
 */
@Slf4j
public class RpcClient {
    private RpcClientHandler rpcClientHandler = null;
    private AbstractMessageFormatter messageFormatter = null;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public <T> T getBean(Class<T> clazz, final String resourceName){
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (rpcClientHandler == null){
                    initClient();
                }
                rpcClientHandler.setParam(messageFormatter.formatMessage(resourceName, proxy, method, args));
                return executorService.submit(rpcClientHandler).get();
            }
        });
    }

    public Object request(String param) throws ExecutionException, InterruptedException {
        if (rpcClientHandler == null){
            initClient();
        }

        rpcClientHandler.setParam(param);

        return executorService.submit(rpcClientHandler).get();
    }

    public void initClient(){
        rpcClientHandler = new RpcClientHandler();

        NioEventLoopGroup clientGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap()
                .group(clientGroup)
                .channel(NioSocketChannel.class)
                .handler(new BaseStringPackageInitializer(){
                    @Override
                    protected void addSpecialHandler(ChannelPipeline pipeline) {
                        pipeline.addLast("rpc client handler", rpcClientHandler);
                    }
                });
        try {
            ChannelFuture channelFuture = bootstrap
                    .connect(AddressConstants.ADDRESS_COMMON, AddressConstants.PORT_TCP)
                    .sync();
//            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("error ", e);
        }finally {
//            NettyUtil.shutdown(clientGroup);
        }
    }

    public void setMessageFormatter(AbstractMessageFormatter messageFormatter) {
        this.messageFormatter = messageFormatter;
    }
}
