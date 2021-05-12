package factorybean;

import collection.Course;
import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pojo.Book;

/**
 * @author: wangzg
 * @date 2021/5/11 23:01
 * modified by: wangzg
 */
public class MyBeanTest extends TestCase {
    /**
     * 测试普通bean
     */
    @Test
    public void testCommonBean(){
        //加载配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean7.xml");
        //获取配置创建的对象
        MyBean bean1 = context.getBean("myBean", MyBean.class);
        System.out.println(bean1);
    }

    /**
     * 测试工厂bean
     */
    @Test
    public void testFactoryBean(){
        //加载配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean7.xml");
        //获取配置创建的对象
        Course bean2 = context.getBean("myBean", Course.class);
        System.out.println(bean2);
    }

}