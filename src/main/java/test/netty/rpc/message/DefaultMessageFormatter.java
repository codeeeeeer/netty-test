package test.netty.rpc.message;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.util.Assert;
import test.netty.constants.CommonConstants;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈〉
 *
 * @author LewJay
 * @create 2020/2/3 18:43
 */
public class DefaultMessageFormatter extends AbstractMessageFormatter {
    public DefaultMessageFormatter(String provider) {
        super(provider);
    }

    @Override
    public String formatMessage(String resourceName, Object entity, Method method, Object[] args) {
        StringBuilder message = new StringBuilder();
        message.append(getProvider())
                .append(resourceName)
                .append(CommonConstants.METHOD_SEPARATOR)
                .append(method.getName());
        if (ArrayUtils.isNotEmpty(args)){
            LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
            String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
            Assert.isTrue(parameterNames.length == args.length, "ARGS EXCEPTION");
            if (ArrayUtils.isNotEmpty(parameterNames)){
                Map<String, Object> paramMap = new HashMap<>(parameterNames.length);
                for (int i = 0; i < parameterNames.length; i++) {
                    paramMap.put(parameterNames[i], args[i]);
                }
                message.append(CommonConstants.URI_SEPARATOR).append(JSON.toJSONString(paramMap));
            }
        }
        return message.toString();
    }
}
