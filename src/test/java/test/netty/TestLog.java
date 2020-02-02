package test.netty;

import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import test.netty.utils.MessageUtil;

/**
 * 〈〉
 *
 * @author liujie
 * @create 2020/01/17 15:16
 */
@Slf4j
public class TestLog extends TestCase {

    public void testLog(){
        log.info("test message  {} - {}!", "hahahj", "124");
    }

    public void testMessageFormatter(){
        assertEquals("test message  hahahj - 124!", MessageUtil.format("test message  {} - {}!", "hahahj", "124"));
    }
}
