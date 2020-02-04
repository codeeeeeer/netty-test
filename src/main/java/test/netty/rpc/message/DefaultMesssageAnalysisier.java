package test.netty.rpc.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import java.lang.reflect.Method;

/**
 * 〈〉
 *
 * @author LewJay
 * @create 2020/2/3 19:52
 */
public class DefaultMesssageAnalysisier extends AbstractMesssageAnalysisier{
    @Override
    public Object[] analysis(Class<?> clazz, Method method, String argsMessage) {
        if (StringUtils.isBlank(argsMessage)){
            return new Object[0];
        }
        LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        Object[] result = new Object[parameterNames.length];
        JSONObject jsonObject = JSON.parseObject(argsMessage);
        for (int i = 0; i < parameterNames.length; i++) {
            result[i] = jsonObject.get(parameterNames[i]);
        }
        return result;
    }
}
