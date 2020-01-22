package test.netty.utils;

import test.netty.constants.CommonConstants;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

/**
 * 〈〉
 *
 * @author liujie
 * @create 2020/1/22 9:39
 */
public class IOUtil {
    public static void sendInputToOutputStream(OutputStream target) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String input = scanner.next();
            if (CommonConstants.getExitStrs().contains(input)){
                return;
            }
            target.write(input.getBytes());
        }
    }
}
