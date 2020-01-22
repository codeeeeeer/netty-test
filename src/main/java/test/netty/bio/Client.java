package test.netty.bio;

import test.netty.constants.AddressConstants;
import test.netty.utils.IOUtil;

import java.io.OutputStream;
import java.net.Socket;

/**
 * 〈〉
 *
 * @author liujie
 * @create 2020/1/22 9:35
 */
public class Client {

    public void start() throws Exception{
        try (Socket socket = new Socket(AddressConstants.ADDRESS_COMMON, AddressConstants.PORT_TCP)) {
            try (OutputStream socketOutputStream = socket.getOutputStream()){
                IOUtil.sendInputToOutputStream(socketOutputStream);
            }
        }
    }

    public static void main(String[] args) throws Exception{
        new Client().start();
    }
}
