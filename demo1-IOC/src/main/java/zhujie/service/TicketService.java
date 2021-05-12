package zhujie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import zhujie.dao.TicketDao;

import javax.annotation.Resource;

/**
 * @author: wangzg
 * @date 2021/5/12 0:52
 * modified by: wangzg
 */
//value默认值是类名首字母小写
@Service(value = "ticketService")
public class TicketService {

    @Value(value = "name")
    private String name;

    @Autowired
    @Qualifier(value = "ticketDaoImpl")
    private TicketDao ticketDao;

    //自动根据类型注入
//    @Resource
//    private TicketDao ticketDao;

    //自动根据名称注入
//    @Resource(name = "ticketDaoImpl")
//    private TicketDao ticketDao;

    public void test(){
        System.out.println("test service");
    }

    public void testAutowire(){
        ticketDao.testDao();
    }
}
