package zhujie.config;

import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import zhujie.service.TicketService;

/**
 * @author: wangzg
 * @date 2021/5/12 1:23
 * modified by: wangzg
 */
public class SpringConfigTest extends TestCase {
    @Test
    public void testConfig(){
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        TicketService ticketService = context.getBean("ticketService", TicketService.class);
        ticketService.test();
    }
}