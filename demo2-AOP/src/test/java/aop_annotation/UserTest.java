package aop_annotation;

import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: wangzg
 * @date 2021/5/12 23:22
 * modified by: wangzg
 */
public class UserTest extends TestCase {

    @Test
    public void testAopAnno(){
        ApplicationContext context = new ClassPathXmlApplicationContext("aop_annotation.xml");
        User user = context.getBean("user", User.class);
        user.test();
    }
}