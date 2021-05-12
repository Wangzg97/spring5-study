package zhujie.service;

import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: wangzg
 * @date 2021/5/12 0:54
 * modified by: wangzg
 */
public class TicketServiceTest extends TestCase {

    @Test
    public void testTest1() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean12.xml");
        TicketService ticketService = context.getBean("ticketService", TicketService.class);
        ticketService.test();
    }

    @Test
    public void testTestAutowire() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean12.xml");
        TicketService ticketService = context.getBean("ticketService", TicketService.class);
        ticketService.testAutowire();
    }
}