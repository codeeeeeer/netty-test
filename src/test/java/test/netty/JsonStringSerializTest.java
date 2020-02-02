package test.netty;

import com.alibaba.fastjson.JSON;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import test.netty.helper.TestEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 〈〉
 *
 * @author LewJay
 * @create 2020/1/23 10:03
 */
@Slf4j
public class JsonStringSerializTest extends TestCase {

    public void testObjectToString(){
        TestEntity testEntity = new TestEntity()
                .setId(1000)
                .setBirth(new Date(1579749428073L))
                .setName("Johnney")
                .setMoney(new BigDecimal("10000.99"));
        String jsonString = JSON.toJSONString(testEntity);
        String expectedJsonStr = "{\"birth\":1579749428073,\"id\":1000,\"money\":10000.99,\"name\":\"Johnney\"}";
        assertEquals(expectedJsonStr, jsonString);

        TestEntity testEntity1 = JSON.parseObject(jsonString, TestEntity.class);
        assertEquals(testEntity1.toString(), "TestEntity(id=1000, name=Johnney, money=10000.99, birth=Thu Jan 23 11:17:08 CST 2020)");

    }
}
