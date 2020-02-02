package test.netty.rpc.helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;

/**
 * 〈〉
 *
 * @author LewJay
 * @create 2020/2/2 10:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RpcEntity {
    private Object target;
    private Method method;
}
