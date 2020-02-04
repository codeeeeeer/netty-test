package test.netty.rpc.message;

import java.lang.reflect.Method;

/**
 * 〈〉
 *
 * @author LewJay
 * @create 2020/2/3 18:41
 */
public abstract class AbstractMessageFormatter {
    private final String provider;

    public AbstractMessageFormatter(String provider) {
        this.provider = provider;
    }

    public String getProvider() {
        return provider;
    }

    public abstract String formatMessage(String resourceName, Object entity, Method method, Object[] args);
}
