package beanlife;

import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pojo.Book;

/**
 * @author: wangzg
 * @date 2021/5/11 23:29
 * modified by: wangzg
 */
public class PersonTest extends TestCase {

    @Test
    public void test1(){
        //加载配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean8.xml");
        //获取配置创建的对象
        Person person = context.getBean("person", Person.class);
        System.out.println("第四步：获取bean实例使用");

        //手动销毁bean实例
        ((ClassPathXmlApplicationContext)context).close();
    }

}