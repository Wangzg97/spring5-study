package zhujie.dao.impl;

import org.springframework.stereotype.Repository;
import zhujie.dao.TicketDao;

/**
 * @author: wangzg
 * @date 2021/5/12 1:08
 * modified by: wangzg
 */
@Repository(value = "ticketDaoImpl")
public class TicketDaoImpl implements TicketDao {
    @Override
    public void testDao() {
        System.out.println("test dao");
    }
}
