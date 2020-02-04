package test.netty.netty.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import test.netty.constants.CommonConstants;
import test.netty.rpc.helper.RpcEntity;
import test.netty.rpc.helper.RpcServerResourceHolder;
import test.netty.rpc.message.AbstractMesssageAnalysisier;

import java.lang.reflect.Method;

/**
 * 〈〉
 *
 * @author LewJay
 * @create 2020/1/23 12:58
 */
@ChannelHandler.Sharable
public class RpcDispatcherHandler extends ChannelInboundHandlerAdapter {
    private AbstractMesssageAnalysisier messsageAnalysisier;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String path = (String) msg;
        if (StringUtils.isBlank(path) || ! path.startsWith(CommonConstants.PROVIDER_NAME)){
            super.channelRead(ctx, msg);
        }
        String[] paths = path.split("/");
        if (ArrayUtils.isEmpty(paths)){
            super.channelRead(ctx, msg);
        }
        int firstIndexOfSeparator = path.indexOf(CommonConstants.URI_SEPARATOR);
        String paramStr = path.substring(firstIndexOfSeparator);
        String requestService = path.substring(CommonConstants.PROVIDER_NAME.length(), firstIndexOfSeparator - 1);
        RpcEntity rpcEntity = RpcServerResourceHolder.getContainer().get(requestService);
        Object target = rpcEntity.getTarget();
        Method method = rpcEntity.getMethod();
        //传入参数
        Object result = method.invoke(target, messsageAnalysisier.analysis(target.getClass(), method, paramStr));
        //直接将参数写回去
        ctx.writeAndFlush(result);
    }
}
