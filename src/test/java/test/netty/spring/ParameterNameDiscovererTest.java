package test.netty.spring;

import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import test.netty.helper.ParameterNameDiscovererTestHelper;

import java.lang.reflect.Method;

/**
 * 〈〉
 *
 * @author LewJay
 * @create 2020/2/3 17:09
 */
@Slf4j
public class ParameterNameDiscovererTest extends TestCase {
    public void testParameterNameDiscoverer(){
        Method[] methods = ParameterNameDiscovererTestHelper.class.getMethods();
        Method testMethod = null;
        for (Method method : methods) {
            if (method.getName().equals("test")){
                testMethod = method;
            }
        }
        LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(testMethod);
        log.info(ArrayUtils.toString(parameterNames));
    }
}
