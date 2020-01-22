package test.netty.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 〈〉
 *
 * @author liujie
 * @create 2020/01/17 15:30
 */
@Slf4j
public class CommonUtils {

    public static void close(AutoCloseable closeable){
        try {
            closeable.close();
        } catch (Exception e) {
            log.error("caught exception when closing !", e);
        }
    }

    public static void sleep(long time, TimeUnit timeUnit){
        try {
            Thread.sleep(timeUnit.toMillis(time));
        } catch (InterruptedException e) {
            log.error("error when sleeping! {}", e);
        }
    }
}
