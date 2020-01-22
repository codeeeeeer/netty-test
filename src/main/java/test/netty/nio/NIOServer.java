package test.netty.nio;

import lombok.extern.slf4j.Slf4j;
import test.netty.constants.AddressConstants;
import test.netty.utils.CommonUtils;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * 〈〉
 *
 * @author liujie
 * @create 2020/1/22 10:18
 */
@Slf4j
public class NIOServer {

    public void start() throws Exception{
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
             Selector selector = Selector.open();){
            serverSocketChannel.bind(new InetSocketAddress(AddressConstants.PORT_TCP));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            log.info("server ready at {}", serverSocketChannel.getLocalAddress());
            while (true){
                if (selector.select() <= 0){
                    log.info("did not select any this time");
                    CommonUtils.sleep(1, TimeUnit.SECONDS);
                    continue;
                }
                Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
                while (selectionKeyIterator.hasNext()){
                    SelectionKey selectionKey = selectionKeyIterator.next();

                    if (selectionKey.isAcceptable()){
                        ServerSocketChannel serverSocketChannel1 = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel socketChannel = serverSocketChannel1.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        log.info("accept client channel {}", socketChannel.getRemoteAddress());
                    }else if (selectionKey.isReadable()){
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
                        socketChannel.read(byteBuffer);
                        byteBuffer.flip();
                        byte[] bytes = new byte[2048];
                        int remaining = byteBuffer.remaining();
                        byteBuffer.get(bytes, 0, remaining);
                        String content = new String(bytes, 0, remaining);
                        log.info("receive message from {} : {}", socketChannel.getRemoteAddress(), content);
                    }

                    selectionKeyIterator.remove();
                }
            }
        }
    }

    public static void main(String[] args) throws Throwable{
        new NIOServer().start();
    }
}
