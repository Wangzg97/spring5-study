# AOP

## 概念
1. 概念：面向切面编程，利用AOP可以对业务逻辑的各个部分进行隔离，降低各部分之间的耦合度，提高程序的可重用性。图示如下
    ![](https://i.loli.net/2021/05/12/oDOWdxu2ym7NMEC.png)
      
## 原理
AOP底层使用动态代理
1. 有两种情况动态代理
    1) 有接口情况，使用JDK动态代理。**通过创建接口实现类代理对象，增强类的方法。**
    ![](https://i.loli.net/2021/05/12/57DMLFOCEelch86.png)
        1) 使用JDK动态代理，使用Proxy类（java.long.reflect.Proxy）中的方法（newProxyInstance）创建代理对象
           ![](https://i.loli.net/2021/05/12/Ona85KfRkb9j7cd.png)
    2) 无接口情况，使用CGLIB动态代理。**创建子类的代理对象，增强类的方法。**
    ![](https://i.loli.net/2021/05/12/QSjXyRW6p9wH5DB.png)

2. JDK动态代理实现示例
    1) 创建接口
    ```java
    public interface UserDao {
        int test(int x);
    
        String test2(String v);
    }
    ```
    2) 创建接口实现类
    ```java
    public class UserDaoImpl implements UserDao{
        @Override
        public int test(int x) {
            return x;
        }
    
        @Override
        public String test2(String v) {
            return v;
        }
    }
    ```
   3) 使用Proxy创建接口代理对象
    ```java
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
    ```
## 相关术语
1. 连接点：可以被增强的方法
2. 切入点：实际被真正增强的方法
3. 通知（增强）：
   1) 前置通知
   2) 后置通知 
   3) 环绕通知 
   4) 异常通知 
   5) 最终通知
4. 切面：是一个动作，把通知应用到切入点的过程

## AOP操作实现
1. Spring框架一般基于Aspectj实现AOP操作
    - Aspectj不是Spring的组成部分，是一种单独的框架，一般把Aspectj与Spring框架一起使用，进行AOP操作
2. 基于Aspectj实现AOP的方法
   1) 基于xml配置文件
   2) 基于注解方式
    
3. 切入点表达式
   1) 作用：明确对哪一个类里的哪一个方法进行增强
   2) 语法：execution([权限修饰符][返回类型][类全路径]([参数列表]))  。示例如下
      - 对com.example.dao.UserDao类里面的test()方法进行增强: execution(* com.example.dao.UserDao.test(..))
      - 对com.example.dao.UserDao类里面的所有方法进行增强: execution(* com.example.dao.UserDao.*(..))
      - 对com.example.dao包里所有类里面的所有方法进行增强: execution(* com.example.dao.*.*(..))
   
### 注解方式实现AOP
1. 创建类User.java
```java
public class User {
    public void test(){
        System.out.println("test");
    }
}
```
2. 创建增强类（编写增强逻辑）UserProxy.java
```java
public class UserProxy {
    
    //作为前置通知
    public void before(){
        System.out.println("before......");
    }
}
```
3. 进行通知的配置
   1) 在spring配置文件中开启注解扫描 aop-annotation.xml
   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <beans xmlns="http://www.springframework.org/schema/beans"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xmlns:context="http://www.springframework.org/schema/context"
   xmlns:aop="http://www.springframework.org/schema/aop"
   xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
   http://www.springframework.org/schema/context http://www.springframework.org/schema/beans/spring-context.xsd
   http://www.springframework.org/schema/aop http://www.springframework.org/schema/beans/spring-aop.xsd">
   
       <!-- 开启组件扫描 -->
       <context:component-scan base-package="aop_annotation"></context:component-scan>
   
   </beans>
   ```
   2) 使用注解创建User和UserProxy对象：在User.java和UserProxy.java添加@Component注解
   3) 在增强类上添加注解@Aspect：在UserProxy.java添加@Aspect注解
   4) 在spring配置文件中开启生成代理对象，修改aop-annotation.xml
   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <beans xmlns="http://www.springframework.org/schema/beans"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xmlns:context="http://www.springframework.org/schema/context"
   xmlns:aop="http://www.springframework.org/schema/aop"
   xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
   http://www.springframework.org/schema/context http://www.springframework.org/schema/beans/spring-context.xsd
   http://www.springframework.org/schema/aop http://www.springframework.org/schema/beans/spring-aop.xsd">
   
       <!-- 开启组件扫描 -->
       <context:component-scan base-package="aop_annotation"></context:component-scan>
   
       <!-- 开启生成代理对象 -->
       <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
   
   </beans>
   ```
   5) 配置不同类型的通知(@Before,@After,@AfterReturnning,@AfterThrowing,@Arund)：在增强类的作为通知单方法上添加通知类型注解，使用切入点表达式配置。即修改UserProxy.java
   ```java
   @Component
   @Aspect
   public class UserProxy {
   
       //作为前置通知
       @Before(value = "execution(* aop_annotation.User.test(..))")
       public void before(){
           System.out.println("before......");
       }
   }
   ```
   6) 测试
   ```java
   @Test
   public void testAopAnno(){
      ApplicationContext context = new ClassPathXmlApplicationContext("aop-annotation.xml");
      User user = context.getBean("user", User.class);
      user.test();
   }
   ```
   7) 相同切入点抽取
      - 例如UserProxy类中的方法的切入点一样，可通过注解@Pointcut抽取出来
   ```java
   @Component
   @Aspect
   public class UserProxy {
   
       //相同切入点抽取
       @Pointcut(value = "execution(* aop_annotation.User.test(..))")
       public void pointdemo(){
   
       }
   
       //作为前置通知
       @Before(value = "execution(* aop_annotation.User.test(..))")
      //    @Before(value = "pointdemo()")//使用公共切入点
         public void before(){
         System.out.println("before......");
      }
   }
   ```
   8) 多个增强类可以设置优先级，通过@Order(数字类型质),数字值越小优先级越高
   ```java
   @Component
   @Aspect
   @Order(1)
   public class PersonProxy {
      //作为前置通知
      @Before(value = "execution(* aop_annotation.User.test(..))")
      public void before(){
      System.out.println("PersonProxy before......");
   }
   }
   ```
   
### 配置文件实现AOP
1. 创建类和增强类
   ```java
   public class Book {
       public void test(){
           System.out.println("test.....");
       }
   }
   ```
   ```java
   public class BookProxy {
       public void before(){
           System.out.println("before.....");
       }
   }
   ```
2. 配置文件 aop-xml.xml
   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:context="http://www.springframework.org/schema/context"
          xmlns:aop="http://www.springframework.org/schema/aop"
          xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                               http://www.springframework.org/schema/context http://www.springframework.org/schema/beans/spring-context.xsd
                               http://www.springframework.org/schema/aop http://www.springframework.org/schema/beans/spring-aop.xsd">
   
       <bean id="book" class="aop_xml.Book"></bean>
       <bean id="bookProxy" class="aop_xml.BookProxy"></bean>
   
       <aop:config>
           <!-- 配置切入点 -->
           <aop:pointcut id="p" expression="execution(* aop_xml.Book.test(..))"/>
           <!-- 切面 -->
           <aop:aspect ref="bookProxy">
               <!-- 增强作用在具体的方法上 -->
               <aop:before method="before" pointcut-ref="p"></aop:before>
           </aop:aspect>
       </aop:config>
   
   </beans>
   ```
   