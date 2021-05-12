package pojo;

import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: wangzg
 * @date 2021/5/11 22:40
 * modified by: wangzg
 */
public class UserTest extends TestCase {

    @Test
    public void testAdd() {
        //加载配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
        //获取配置创建的对象
        User user = context.getBean("user", User.class);
        user.add();
    }
}