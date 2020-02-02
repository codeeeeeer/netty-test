package test.netty.netty.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import test.netty.annotation.RpcService;
import test.netty.rpc.helper.RpcEntity;
import test.netty.utils.CommonUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static test.netty.constants.CommonConstants.SUFFIX_CLASS_JAVA;
import static test.netty.constants.CommonConstants.URI_SPRATOR;

/**
 * 〈〉
 *
 * @author LewJay
 * @create 2020/1/23 12:58
 */
@ChannelHandler.Sharable
public class RpcDispatcherHandler extends ChannelInboundHandlerAdapter {
    private Map<String, RpcEntity> container = new ConcurrentHashMap<>();

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        String basePackage = "test.netty.service";
        String path = this.getClass().getResource("/").getPath();
        String searchPath = path + basePackage.replaceAll("\\.", File.separator);
        Collection<Class> allRpcServices = getAllRpcService(new File(searchPath), path);

        for (Class rpcService: allRpcServices) {
            RpcService rpcServiceAnnotation = (RpcService) rpcService.getAnnotation(RpcService.class);
            String baseRelativePath = rpcServiceAnnotation.relativePath();
            if (StringUtils.isBlank(baseRelativePath)){
                baseRelativePath = rpcService.getSimpleName().toLowerCase();
            }
            Object targetEntity = rpcService.newInstance();
            Collection<Method> allRpcMethods = getAllRpcMethods(rpcService);
            for (Method method: allRpcMethods) {
                RpcService methodAnnotation = method.getAnnotation(RpcService.class);
                String relativePath = methodAnnotation.relativePath();
                if (StringUtils.isBlank(relativePath)){
                    relativePath = method.getName();
                }
                container.put(baseRelativePath + URI_SPRATOR + relativePath, new RpcEntity(targetEntity, method));
            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String path = (String) msg;
        String[] paths = path.split("/");
        if (ArrayUtils.isEmpty(paths)){
            super.channelRead(ctx, msg);
        }
        if (StringUtils.isBlank(paths[0])){
            paths = ArrayUtils.remove(paths, 0);
        }
        RpcEntity rpcEntity = container.get(paths[0] + URI_SPRATOR + paths[1]);
        Object target = rpcEntity.getTarget();
        Method method = rpcEntity.getMethod();

        //传入参数
        Object result = method.invoke(target, paths[2]);
        //直接将参数写回去
        ctx.writeAndFlush(result);
    }

    private Collection<Class> getAllRpcService(File searchDir, String basePath) throws ClassNotFoundException {
        Collection<Class> result = new HashSet<>();
        File[] listFiles = searchDir.listFiles();
        for (File childFile: listFiles) {
            if (childFile.isDirectory()){
                CommonUtils.collectIfNotEmpty(result, getAllRpcService(childFile, basePath));
            }else if (childFile.getName().endsWith(SUFFIX_CLASS_JAVA)){
                String classPath = childFile.getPath().replace(
                        basePath.replace("/", "\\").replaceFirst("\\\\", ""), "")
                        .replace("\\", ".").replace(SUFFIX_CLASS_JAVA, "");
                Class<?> aClass = Class.forName(classPath);

                if (aClass.isAnnotationPresent(RpcService.class)){
                    result.add(aClass);
                }
            }
        }
        return result;
    }

    private Collection<Method> getAllRpcMethods(Class sourceClazz){
        Method[] clazzMethods = sourceClazz.getMethods();
        Set<Method> result = new HashSet<>();
        for (Method method: clazzMethods) {
            RpcService annotation = method.getAnnotation(RpcService.class);
            if (annotation != null){
                result.add(method);
            }
        }
        return result;
    }

}
