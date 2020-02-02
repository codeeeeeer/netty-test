package test.netty.service.impl;

import test.netty.annotation.RpcService;
import test.netty.service.IGreetService;
import test.netty.utils.MessageUtil;

/**
 * 〈〉
 *
 * @author LewJay
 * @create 2020/1/23 12:35
 */
@RpcService(relativePath = "chineseGreet")
public class ChineseGreetServiceImpl implements IGreetService {
    @Override
    @RpcService
    public String greet(String name) {
        return MessageUtil.format("你好啊！很高兴见到你。我叫 {} !", name);
    }
}
