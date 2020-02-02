package test.netty.service.impl;

import test.netty.annotation.RpcService;
import test.netty.service.IGreetService;
import test.netty.utils.MessageUtil;

/**
 * 〈〉
 *
 * @author LewJay
 * @create 2020/1/23 12:34
 */
@RpcService(relativePath = "englishGreet")
public class EnglishGreetServiceImpl implements IGreetService {
    @Override
    @RpcService(relativePath = "greet")
    public String greet(String name) {
        return MessageUtil.format("Hello ! Nice to meet you ! My name is {}", name);
    }
}
