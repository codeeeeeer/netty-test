package test.netty;

import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

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

    public void testReplace(){
        String basePackage = "test.netty.service";
        String packagePath = basePackage.replace(".", File.separator);
        log.info(packagePath);
    }
}
