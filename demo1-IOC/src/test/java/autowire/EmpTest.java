package autowire;

import beanlife.Person;
import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: wangzg
 * @date 2021/5/11 23:55
 * modified by: wangzg
 */
public class EmpTest extends TestCase {

    @Test
    public void testTest1() {
        //加载配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("autowire.xml");
        //获取配置创建的对象
        Emp emp = context.getBean("emp", Emp.class);
        emp.test();
    }
}