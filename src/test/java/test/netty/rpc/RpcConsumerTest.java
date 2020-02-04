package test.netty.rpc;

import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import test.netty.netty.client.RpcClient;

/**
 * 〈〉
 *
 * @author LewJay
 * @create 2020/2/2 17:01
 */
@Slf4j
public class RpcConsumerTest extends TestCase {

    public void testConsumer() throws Exception{
        RpcClient rpcClient = new RpcClient();
        Object request = rpcClient.request("chineseGreet/greet/李宁");
        log.info(request.toString());
    }
}
