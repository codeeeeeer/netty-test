package test.netty.utils;

import org.slf4j.helpers.MessageFormatter;

/**
 * 〈〉
 *
 * @author LewJay
 * @create 2020/1/23 12:36
 */
public class MessageUtil {
    public static String format(String pattern, Object... args){
        return MessageFormatter.arrayFormat(pattern, args).getMessage();
    }
}
