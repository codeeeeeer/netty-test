package test.netty;

import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;

/**
 * 〈〉
 *
 * @author LewJay
 * @create 2020/1/29 10:34
 */
@Slf4j
public class StringTest extends TestCase {

    public void testSplit(){
        String[] strings = "ha/".split("/");
        log.info(strings.length + "");
        log.info(strings[0]);
//        log.info(strings[1]);
    }
}
