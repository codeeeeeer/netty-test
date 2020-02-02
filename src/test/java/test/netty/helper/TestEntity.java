package test.netty.helper;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 〈〉
 *
 * @author LewJay
 * @create 2020/1/23 10:24
 */
@Data
@Accessors(chain = true)
@ToString
public class TestEntity {
    private Integer id;
    private String name;
    private BigDecimal money;
    private Date birth;
}
