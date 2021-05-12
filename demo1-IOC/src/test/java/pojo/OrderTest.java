package pojo;

import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: wangzg
 * @date 2021/5/11 22:43
 * modified by: wangzg
 */
public class OrderTest extends TestCase {

    @Test
    public void testTestInfo() {
        //加载配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
        //获取配置创建的对象
        Order order = context.getBean("order", Order.class);
        order.testInfo();
    }
}