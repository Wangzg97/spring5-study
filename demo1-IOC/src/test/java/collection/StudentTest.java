package collection;

import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: wangzg
 * @date 2021/5/11 22:49
 * modified by: wangzg
 */
public class StudentTest extends TestCase {

    @Test
    public void testTest1() {
        //加载配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean5.xml");
        //获取配置创建的对象
        Student stu = context.getBean("stu", Student.class);
        stu.test();
    }
}