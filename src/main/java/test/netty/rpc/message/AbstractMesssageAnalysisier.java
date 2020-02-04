package test.netty.rpc.message;

import java.lang.reflect.Method;

/**
 * 〈〉
 *
 * @author LewJay
 * @create 2020/2/3 19:52
 */
public abstract class AbstractMesssageAnalysisier {
    public abstract Object[] analysis(Class<?> clazz, Method method, String argsMessage);
}
