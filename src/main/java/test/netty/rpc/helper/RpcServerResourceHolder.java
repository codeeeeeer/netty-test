package test.netty.rpc.helper;

import org.apache.commons.lang3.StringUtils;
import test.netty.annotation.RpcService;
import test.netty.utils.CommonUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static test.netty.constants.CommonConstants.METHOD_SEPARATOR;
import static test.netty.constants.CommonConstants.SUFFIX_CLASS_JAVA;

/**
 * 〈〉
 *
 * @author LewJay
 * @create 2020/2/3 10:08
 */
public class RpcServerResourceHolder {
    private static final Map<String, RpcEntity> container = new ConcurrentHashMap<>();

    public static void initResource(String basePackage) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        String path = RpcServerResourceHolder.class.getResource("/").getPath();
        String packagePath = basePackage.replace(".", File.separator);
        String searchPath = path + packagePath;
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
                container.put(baseRelativePath + METHOD_SEPARATOR + relativePath, new RpcEntity(targetEntity, method));
            }
        }
    }


    private static Collection<Class> getAllRpcService(File searchDir, String basePath) throws ClassNotFoundException {
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

    private static Collection<Method> getAllRpcMethods(Class sourceClazz){
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

    public static Map<String, RpcEntity> getContainer() {
        return container;
    }
}
