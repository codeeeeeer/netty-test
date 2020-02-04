package test.netty.helper;

import lombok.extern.slf4j.Slf4j;
import test.netty.utils.MessageUtil;

import java.math.BigDecimal;

/**
 * 〈〉
 *
 * @author LewJay
 * @create 2020/2/3 17:10
 */
@Slf4j
public class ParameterNameDiscovererTestHelper {
    public void test(String param0, Integer param1, BigDecimal param2){
        log.info(MessageUtil.format("{}, {}, {}", param0, param1, param2));
    }
}
