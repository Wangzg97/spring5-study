package pojo;

import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: wangzg
 * @date 2021/5/11 22:42
 * modified by: wangzg
 */
public class BookTest extends TestCase {

    @Test
    public void testTestInfo() {
        //加载配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
        //获取配置创建的对象
        Book book = context.getBean("book", Book.class);
        book.testInfo();
    }

    //测试单实例
    @Test
    public void testSingleton(){
        //加载配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
        //获取配置创建的对象
        Book book1 = context.getBean("book", Book.class);
        Book book2 = context.getBean("book", Book.class);
        System.out.println(book1);
        System.out.println(book2);
    }
}