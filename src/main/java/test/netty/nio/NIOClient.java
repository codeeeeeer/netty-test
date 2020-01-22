package test.netty.nio;

import lombok.extern.slf4j.Slf4j;
import test.netty.constants.AddressConstants;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * 〈〉
 *
 * @author liujie
 * @create 2020/1/22 10:18
 */
@Slf4j
public class NIOClient {

    public void start() throws Exception{
        try (SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(AddressConstants.ADDRESS_COMMON, AddressConstants.PORT_TCP));){
            socketChannel.configureBlocking(false);
            if (socketChannel.isConnected()){
                log.info("client connected !");
            }
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()){
                String next = scanner.next();
                byte[] inputBytes = next.getBytes();
                ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
                byteBuffer.put(inputBytes);
                byteBuffer.flip();
                socketChannel.write(byteBuffer);
            }
        }
    }

    public static void main(String[] args) throws Exception{
        new NIOClient().start();
    }
}
