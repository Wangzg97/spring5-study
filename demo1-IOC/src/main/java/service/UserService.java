package service;

import dao.UserDao;
import dao.impl.UserDaoImpl;

/**
 * @author: wangzg
 * @date 2021/5/10 23:41
 * modified by: wangzg
 */
public class UserService {

    //创建UserDao类型属性，生成set方法
    UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void add() {
        System.out.println("service add ......");
        //原始方法，创建UserDao对象然后调用方法
//        UserDao userDao = new UserDaoImpl();
//        userDao.update();
    }
}
