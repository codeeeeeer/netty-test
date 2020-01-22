package test.netty.bio;

import lombok.extern.slf4j.Slf4j;
import test.netty.constants.AddressConstants;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 〈〉
 *
 * @author liujie
 * @create 2020/1/22 9:28
 */
@Slf4j
public class Server {
    public void start() throws Exception{
        try (
                ServerSocket serverSocket = new ServerSocket(AddressConstants.PORT_TCP);
                Socket clientSocket = serverSocket.accept();
        ){
            try (InputStream inputStream = clientSocket.getInputStream()) {
                while (true){
                    byte[] buffer = new byte[1024];
                    int read = inputStream.read(buffer);
                    String contentStr = new String(buffer, 0, read);
                    log.info("receive message from {} : {}", clientSocket.getRemoteSocketAddress(), contentStr);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception{
        new Server().start();
    }
}
