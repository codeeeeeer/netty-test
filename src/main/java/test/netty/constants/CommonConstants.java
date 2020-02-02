package test.netty.constants;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

/**
 * 〈〉
 *
 * @author liujie
 * @create 2020/01/17 16:40
 */
public class CommonConstants {
    private final static Set<String> exitStrs = ImmutableSet.of("exit", "bye", "quit");
    public static Set<String> getExitStrs(){
        return exitStrs;
    }

    public final static String SUFFIX_CLASS_JAVA = ".class";
    public final static String URI_SPRATOR = ".class";


}
