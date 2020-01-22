package test.netty.utils;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.extern.slf4j.Slf4j;
import test.netty.constants.CommonConstants;

import java.util.Scanner;

/**
 * 〈〉
 *
 * @author liujie
 * @create 2020/01/17 15:58
 */
@Slf4j
public class NettyUtil {
    public static void shutdown(EventExecutorGroup group){
        try {
            group.shutdownGracefully().sync();
        } catch (InterruptedException e) {
            log.error("group shut down ！", e);
        }
    }

    public static void sendInputMsgToChannel(Channel channel){
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String input = scanner.next();
            if (CommonConstants.getExitStrs().contains(input)){
                return;
            }
            channel.writeAndFlush(Unpooled.copiedBuffer(input, CharsetUtil.UTF_8));
        }
    }
}
