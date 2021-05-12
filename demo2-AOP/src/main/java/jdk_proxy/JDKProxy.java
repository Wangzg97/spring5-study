package jdk_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @author: wangzg
 * @date 2021/5/12 22:18
 * modified by: wangzg
 */
public class JDKProxy {
    public static void main(String[] args) {
        //创建接口实现类代理对象
        Class[] interfaces = {UserDao.class};
        //匿名内部类方试
//        Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), interfaces, new InvocationHandler() {
            //增强逻辑
//            @Override
//            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                return null;
//            }
//        })
        UserDaoImpl userDao = new UserDaoImpl();
        UserDao dao = (UserDao) Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), interfaces, new UserDaoProxy(userDao));
        int test = dao.test(1);
        System.out.println(test);
    }
}

//创建代理对象
class UserDaoProxy implements InvocationHandler {

    //需要创建代理对象的对象传递出来，此处为UserDaoImpl类对象
    //方式一
    private Object obj;
    public UserDaoProxy(Object o) {
        this.obj = o;
    }

    //增强逻辑
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //方法之前处理
        System.out.println("被增强的方法"+method.getName()+"执行之前,传递的参数是: "+ Arrays.toString(args));
        //执行被增强的方法
        Object ret = method.invoke(obj, args);
        //方法之后处理
        System.out.println("被增强的方法"+method.getName()+"执行之后,被代理的对象是: "+obj);

        return ret;
    }
}