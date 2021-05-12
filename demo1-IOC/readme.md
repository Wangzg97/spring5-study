[TOC]

## IOC
## 一、入门案例
1、创建maven项目，pom文件导入spring相关依赖
```xml
<dependencies>
        <!-- https://mvnrepository.com/artifact/org.springframework/spring-beans -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-beans</artifactId>
            <version>5.3.6</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.springframework/spring-context -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.3.6</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.springframework/spring-core -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>5.3.6</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.springframework/spring-expression -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-expression</artifactId>
            <version>5.3.6</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/commons-logging/commons-logging -->
        <dependency>
            <groupId>commons-logging</groupId>
            <artifactId>commons-logging</artifactId>
            <version>1.2</version>
        </dependency>

        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.13.2</version>
            <scope>test</scope>
        </dependency>

    </dependencies>
```
2、创建实体类
```java
public class User {
    public void add() {
        System.out.println("ADD ..........");
    }
}
```
3、创建xml配置文件 bean1.xml
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- 配置User对象创建 -->
    <bean id="user" class="pojo.User"></bean>
</beans>
```
4、创建测试类
```java
public class UserTest extends TestCase {

    @Test
    public void testAdd() {
        //加载配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
        //获取配置创建的对象
        User user = context.getBean("user", User.class);
        user.add();
    }
}
```
5、运行测试类查看输出

## 二、IOC
### （一）原理
1. 什么是IOC
    - 由Spring管理对象创建和对象之间的调用过程
    - 目的是降低耦合度
    - 入门案例即为IOC的实现
2. 底层原理 
    - xml解析
    - 设计模式--工厂模式
    - 反射
3. 图解
- 在UserService中调用UserDao中的方法
- 原始方式：
![](https://i.loli.net/2021/05/10/3G5s2HklUyOqRMI.png)
- 工厂模式（降低耦合度）
![](https://i.loli.net/2021/05/10/2f7vNjDLF3TZ6aJ.png)
- IOC过程（进一步降低耦合度）
![](https://i.loli.net/2021/05/10/2SIiJmbek9Nuy5Z.png)


### （二）IOC接口（BeanFactory）
1. IOC思想基于IOC容器完成，IOC容器底层就是对象工厂
2. Spring提供IOC容器实现两种方式（两种接口）
   - BeanFactory：IOC容器基本实现，是Spring内部的使用接口，不提供开发人员使用。
   - ApplicationContent：BeanFactory的子接口，提供更多更强大的接口，一般由开发人员使用
   - 下面两句话的能达到相同的效果，对象创建的时机有区别
   ```java
        //ApplicationContext在加载配置文件时就会创建对象
        ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
        //BeanFactory加载配置文件时不会创建对象，只有在获取对象或使用对象时才会创建对象。
        //BeanFactory context = new ClassPathXmlApplicationContext("bean1.xml");
        User user = context.getBean("user", User.class);
        user.add();
   ```
3. ApplicationContext中的实现类
- ClassPathXmlApplicationContext：路径使用src下的内部路径
- FileSystemXmlApplicationContext：路径使用文件全路径

### （三）IOC操作-Bean管理
- Bean管理：spring创建对象，spring注入属性

#### 基于xml方式

##### 基于xml注入属性
1. 基于xml创建对象： 
   
   - 在spring配置文件中使用bean标签实现对象创建
   ```xml
      <!-- 配置User对象创建 -->
      <bean id="user" class="pojo.User"></bean>
   ```
   - bean标签下的属性：id：唯一标识；class：类全路径（包类路径）；name：同id，且name中可以使用特殊符号
   - **创建对象时，默认执行无参构造方法**

2. 基于xml注入属性
   - DI：依赖注入，就是注入属性
   - 注入属性的方式：
     - (1) 通过set方法注入，示例如下:
      创建类
      ```java
        public class Book {
            private String name;
            private String author;
         
            public void setName(String name) {
            this.name = name;
            }
         
            public void setAuthor(String author) {
            this.author = author;
            }
         }
      ```
     在spring配置文件中配置
      ```xml
          <!-- 使用set方法注入属性 -->
          <bean id="book" class="pojo.Book">
              <!-- 使用property完成属性注入 -->
              <property name="name" value="西游记"></property>
              <property name="author" value="罗贯中"></property>
          </bean>
      ```
     创建测试类
     ```java
        public class BookTest extends TestCase {
            @Test
            public void testTestInfo() {
               //加载配置文件
               ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
               //获取配置创建的对象
               Book book = context.getBean("book", Book.class);
               book.testInfo();
            }
         }
     ```
       
     - (2) 通过有参构造函数注入，示例如下：
     创建类
      ```java
         public class Order {
            private String name;
            private String address;
         
            public Order(String name, String address) {
               this.name = name;
               this.address = address;
            }
         }
     ```
     在spring配置文件中配置
      ```xml
          <!-- 使用有参构造注入属性 -->
          <bean id="order" class="pojo.Order">
              <!-- name为参数名；value为参数值；index为参数下表序号，从0开始 -->
              <constructor-arg name="name" value="一号订单"></constructor-arg>
              <constructor-arg name="address" value="某某街道20号"></constructor-arg>
          </bean>
      ```
     创建测试类
      ```java
      public class OrderTest extends TestCase {
      
          @Test
          public void testTestInfo() {
              //加载配置文件
              ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
              //获取配置创建的对象
              Order order = context.getBean("order", Order.class);
              order.testInfo();
          }
      }
      ```
     - (3) p名称空间注入，简化基于xml配置方式
   
##### 基于xml注入其他类型属性

###### 字面量，以类Book为例
   1) null值
   ```xml
      <bean id="book" class="pojo.Book">
        <property name="name" value="西游记"></property>
        <property name="author">
            <null></null>
        </property>
      </bean>
   ```
   2) 特殊符号，如给address赋值address="<<这是地址>>"，或者转义
   ```xml
      <bean id="book" class="pojo.Book">
        <property name="name" value="西游记"></property>
        <property name="author">
            <value>
                <![CDATA[<<这是地址>>]]>
            </value>
        </property>
      </bean>
   ```
   
###### 注入属性（外部bean）
   1) 创建service类和dao类
   ```java
      public class UserService {
      
          //创建UserDao类型属性，生成set方法
          UserDao userDao;
      
          public void setUserDao(UserDao userDao) {
              this.userDao = userDao;
          }
      
          public void add() {
              System.out.println("service add ......");
              //原始方法，创建UserDao对象然后调用方法
              //UserDao userDao = new UserDaoImpl();
              //userDao.update();
         }
      }
   ```
   ```java
      public interface UserDao {
          public void update();
      }
   ```
   ```java
      public class UserDaoImpl implements UserDao {
          @Override
          public void update() {
              System.out.println("dao update..........");
          }
      }
   ```
   2) spring配置文件 bean2.xml
   ```xml
      <?xml version="1.0" encoding="UTF-8" ?>
      <beans xmlns="http://www.springframework.org/schema/beans"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
      
          <bean id="userService" class="service.UserService">
              <property name="userDao" ref="userDao"></property>
          </bean>
          <bean id="userDao" class="dao.impl.UserDaoImpl"></bean>
      
      </beans>
   ```

###### 注入属性（内部bean和级联赋值）
   1) 一对多关系，部门和员工：一个部门多个员工，一个员工属于一个部门。下面是内部bean示例
      - 创建两个实体类Dept和Emp
      ```java
         public class Dept {
            private String dname;
         
            public void setDname(String dname) {
                this.dname = dname;
            }
         }
      ```
      ```java
         public class Emp {
            private String ename;
            private String gender;
            //员工属于某一个部门
            private Dept dept;
         
            public void setEname(String ename) {
                this.ename = ename;
            }
         
            public void setGender(String gender) {
                this.gender = gender;
            }
         
            public void setDept(Dept dept) {
                this.dept = dept;
            }
         }
      ```
      - 添加spring配置文件bean3.xml
      ```xml
         <?xml version="1.0" encoding="UTF-8" ?>
         <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
         
             <!-- 内部bean注入 -->
             <bean id="emp" class="bean.Emp">
                 <!-- 先设置普通属性 -->
                 <property name="ename" value="张三"></property>
                 <property name="gender" value="男"></property>
                 <!-- 设置对象类属性 -->
                 <property name="dept">
                     <bean id="dept" class="bean.Dept">
                         <property name="dname" value="一部"></property>
                     </bean>
                 </property>
             </bean>
         
         </beans>
      ```
   2) 注入属性-级联赋值
      - 第一种方式
      ```xml
         <?xml version="1.0" encoding="UTF-8" ?>
         <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
         
             <!-- 级联赋值 -->
             <!-- 通过外部bean实现级联赋值 -->
             <bean id="emp" class="bean.Emp">
                 <!-- 先设置普通属性 -->
                 <property name="ename" value="张三"></property>
                 <property name="gender" value="男"></property>
                 <!-- 设置对象类属性 -->
                 <property name="dept" ref="dept"></property>
             </bean>
             <bean id="dept" class="bean.Dept">
                 <property name="dname" value="一部"></property>
             </bean>
         
         </beans>
      ```
      - 第二种方式，要修改Emp.java，添加配置文件bean4.xml
      ```java
         public class Emp {
         private String ename;
         private String gender;
         //员工属于某一个部门
         private Dept dept;
      
         public Dept getDept() {
            return dept;
         }
      
         public void setEname(String ename) {
            this.ename = ename;
         }
      
         public void setGender(String gender) {
            this.gender = gender;
         }
      
         public void setDept(Dept dept) {
            this.dept = dept;
         }
         }
      ```
      ```xml
         <?xml version="1.0" encoding="UTF-8" ?>
         <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
         
             <!-- 级联赋值 -->
             <!-- 通过外部bean实现级联赋值 -->
             <bean id="emp" class="bean.Emp">
                 <!-- 先设置普通属性 -->
                 <property name="ename" value="张三"></property>
                 <property name="gender" value="男"></property>
                 <!-- 设置对象类属性 -->
                 <property name="dept" ref="dept"></property>
                 <property name="dept.dname" value="测试部门"></property>
             </bean>
             <bean id="dept" class="bean.Dept"></bean>
         
         </beans>
      ```
##### xml注入集合类型属性
1. 简单示例，创建实体类Student.java
```java
public class Student {
    private String[] courses;
    private List<String> list;
    private Map<String, String> maps;
    private Set<String> set;

    public void setCourses(String[] courses) {
        this.courses = courses;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public void setMaps(Map<String, String> maps) {
        this.maps = maps;
    }

    public void setSet(Set<String> set) {
        this.set = set;
    }
}
```
添加配置文件bean5.xml
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="stu" class="collection.Student">
        <!-- 数组类型注入 -->
        <property name="courses">
            <!-- 也可使用list标签 -->
            <array>
                <value>数学</value>
                <value>语文</value>
            </array>
        </property>
        <!-- List类型注入 -->
        <property name="list">
            <list>
                <value>list1</value>
                <value>list2</value>
            </list>
        </property>
        <!-- Map类型注入 -->
        <property name="maps">
            <map>
                <entry key="key1" value="value1"></entry>
                <entry key="key2" value="value3"></entry>
            </map>
        </property>
        <!-- Set类型注入 -->
        <property name="set">
            <set>
                <value>set1</value>
                <value>set2</value>
            </set>
        </property>
    </bean>

</beans>
```
创建测试类
```java
public class StudentTest extends TestCase {

    @Test
    public void testTest1() {
        //加载配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean5.xml");
        //获取配置创建的对象
        Student stu = context.getBean("stu", Student.class);
        stu.test();
    }
}
```
2. 注意项
   1) 在集合中设置对象值
    - 修改Student.java
    ```java
        public class Student {
            private String[] courses;
            private List<String> list;
            private Map<String, String> maps;
            private Set<String> set;
        
            private List<Course> courseList;
        
            public void setCourseList(List<Course> courseList) {
                this.courseList = courseList;
            }
        
            public void setCourses(String[] courses) {
                this.courses = courses;
            }
        
            public void setList(List<String> list) {
                this.list = list;
            }
        
            public void setMaps(Map<String, String> maps) {
                this.maps = maps;
            }
        
            public void setSet(Set<String> set) {
                this.set = set;
            }
        
            public void test(){
                System.out.println(this.courses);
                System.out.println(this.list);
                System.out.println(this.maps);
                System.out.println(this.set);
            }
        }
    ```
   修改配置文件 bean5.xml
   ```xml
    <?xml version="1.0" encoding="UTF-8" ?>
    <beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    
        <bean id="stu" class="collection.Student">
            <!-- 数组类型注入 -->
            <property name="courses">
                <!-- 也可使用list标签 -->
                <array>
                    <value>数学</value>
                    <value>语文</value>
                </array>
            </property>
            <!-- List类型注入 -->
            <property name="list">
                <list>
                    <value>list1</value>
                    <value>list2</value>
                </list>
            </property>
            <!-- Map类型注入 -->
            <property name="maps">
                <map>
                    <entry key="key1" value="value1"></entry>
                    <entry key="key2" value="value3"></entry>
                </map>
            </property>
            <!-- Set类型注入 -->
            <property name="set">
                <set>
                    <value>set1</value>
                    <value>set2</value>
                </set>
            </property>
    
            <!-- List携带对象注入 -->
            <property name="courseList">
                <list>
                    <ref bean="c1"></ref>
                    <ref bean="c2"></ref>
                </list>
            </property>
        </bean>
        <!-- 创建多个Course对象 -->
        <bean id="c1" class="collection.Course">
            <property name="cname" value="c111"></property>
        </bean>
        <bean id="c2" class="collection.Course">
            <property name="cname" value="c222"></property>
        </bean>
    
    </beans>
   ```
   2) 把集合注入的部分提取出来作为公共值
        - 在spring配置文件中引入名称空间util
        - 使用util标签完成list注入
    ```xml
    <?xml version="1.0" encoding="UTF-8" ?>
    <beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:util="http://www.springframework.org/schema/util"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
    http://www.springframework.org/schema/util http://www.springframework.org/schema/beans/spring-util.xsd">
    
        <util:list id="bookList">
            <value>book1</value>
            <value>book2</value>
            <value>book3</value>
        </util:list>
    
        <bean id="book" class="collection.Student">
            <property name="list" ref="bookList"></property>
        </bean>
    
    </beans> 
   ```
#### 基于注解方式
1. 针对bean管理提供的注解: 功能是一样的，都可以用来创建bean实例。
    1) @Component
    2) @Service
    3) @Controller
    4) @Repository
    
2. 示例
    1) 创建配置文件 bean12.xml
    ```xml
    <?xml version="1.0" encoding="UTF-8" ?>
    <beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
    http://www.springframework.org/schema/context http://www.springframework.org/schema/beans/spring-context.xsd">
    
        <!-- 开启组件扫描 -->
        <context:component-scan base-package="zhujie"></context:component-scan>
    
    </beans> 
   ```
   2) 创建类
    ```java
    //value默认值是类名首字母小写
    @Component(value = "ticketService")
        public class TicketService {
        public void test(){
            System.out.println("test service");
        }
    }
    ```
   3) 测试类
    ```java
    public class TicketServiceTest extends TestCase {
    
        @Test
        public void testTest1() {
            ApplicationContext context = new ClassPathXmlApplicationContext("bean12.xml");
            TicketService ticketService = context.getBean("ticketService", TicketService.class);
            ticketService.test();
        }
    }
    ```
    
3. 开启组件扫描的细节配置,自己配置扫描规则
    1) 设置扫描哪些规则
    ```xml
    <context:component-scan base-package="zhujie" use-default-filters="false">
        <!-- 设置扫描哪些规则 -->
        <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>
   ```
   2) 设置不扫描哪些规则
   ```xml
    <context:component-scan base-package="zhujie" use-default-filters="false">
        <!-- 设置不扫描哪些规则 -->
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>
   ```
   
4. 注解实现属性注入
    1) @Autowired: 根据属性类型自动装配
    2) @Qualifier: 根据属性名称注入。要和@Autowired一起使用
    3) @Resource: 可以根据属性类型自动装配，也可以根据属性名称注入
    4) @Value: 注入普通类型属性
    
5. 完全注解开发
    1) 创建配置类替代xml配置文件
    ```java
    @Configuration //作为配置类替代xml配置文件
    @ComponentScan(basePackages = {"zhujie"})
    public class SpringConfig {
    
    }
    ```
   2) 测试类
    ```java
    public class SpringConfigTest extends TestCase {
        @Test
        public void testConfig(){
            ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
            TicketService ticketService = context.getBean("ticketService", TicketService.class);
            ticketService.test();
        }
    }
    ```

#### FactoryBean（工厂bean）
1. Spring有两种bean，普通bean和工厂bean（FactoryBean）
2. 普通bean：
    - 在配置文件中定义的bean类型即为返回类型
    
3. 工厂bean
    - 在配置文件中定义的bean类型可以和返回类型不一样
    
    - 示例：
    1) 创建类实现接口FactoryBean，作为工厂bean,实现接口里的方法，在方法中定义返回的类型
    ```java
          public class MyBean implements FactoryBean<Course> {
        
              //定义返回bean
              @Override
              public Course getObject() throws Exception {
                  Course course = new Course();
                  course.setCname("course123");
                  return course;
              }
            
              @Override
              public Class<?> getObjectType() {
                  return null;
              }
            
              @Override
              public boolean isSingleton() {
                  return FactoryBean.super.isSingleton();
              }
          }

    ```
   添加配置文件bean7.xml
    ```xml
        <?xml version="1.0" encoding="UTF-8" ?>
        <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
        
            <!-- 普通bean -->
            <bean id="myBean" class="factorybean.MyBean"></bean>
        
        </beans>
   ```
   ```java
        public class MyBeanTest extends TestCase {
            /**
             * 测试普通bean
             */
            @Test
            public void testCommonBean(){
                //加载配置文件
                ApplicationContext context = new ClassPathXmlApplicationContext("bean7.xml");
                //获取配置创建的对象
                MyBean bean1 = context.getBean("myBean", MyBean.class);
                System.out.println(bean1);
            }
        
            /**
             * 测试工厂bean
             */
            @Test
            public void testFactoryBean(){
                //加载配置文件
                ApplicationContext context = new ClassPathXmlApplicationContext("bean7.xml");
                //获取配置创建的对象
                Course bean2 = context.getBean("myBean", Course.class);
                System.out.println(bean2);
            }
        
        }
    ```

#### bean作用域
1. 在spring中可设置bean实例是单实例还是多实例对象
2. spring中bean默认是单实例
- 单实例测试
```java
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
```
终端输出为：
```shell
D:\Environment\Java\jdk1.8.0_181\bin\java.exe -Dvisualvm.id=268630827128999 -ea -Didea.test.cyclic.buffer.size=1048576 "-javaagent:D:\Program Files\IntelliJ IDEA 2020.3.1\lib\idea_rt.jar=11655:D:\Program Files\IntelliJ IDEA 2020.3.1\bin" -Dfile.encoding=UTF-8 -classpath "D:\Program Files\IntelliJ IDEA 2020.3.1\lib\idea_rt.jar;D:\Program Files\IntelliJ IDEA 2020.3.1\plugins\junit\lib\junit5-rt.jar;D:\Program Files\IntelliJ IDEA 2020.3.1\plugins\junit\lib\junit-rt.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\charsets.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\deploy.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\ext\access-bridge-64.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\ext\cldrdata.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\ext\dnsns.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\ext\jaccess.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\ext\jfxrt.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\ext\localedata.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\ext\nashorn.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\ext\sunec.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\ext\sunjce_provider.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\ext\sunmscapi.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\ext\sunpkcs11.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\ext\zipfs.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\javaws.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\jce.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\jfr.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\jfxswt.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\jsse.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\management-agent.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\plugin.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\resources.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\rt.jar;D:\Study\spring5-study\demo1-IOC\target\test-classes;D:\Study\spring5-study\demo1-IOC\target\classes;D:\Environment\apache-maven-3.6.1\repository\org\springframework\spring-core\5.3.6\spring-core-5.3.6.jar;D:\Environment\apache-maven-3.6.1\repository\org\springframework\spring-jcl\5.3.6\spring-jcl-5.3.6.jar;D:\Environment\apache-maven-3.6.1\repository\org\springframework\spring-context\5.3.6\spring-context-5.3.6.jar;D:\Environment\apache-maven-3.6.1\repository\org\springframework\spring-aop_annotation\5.3.6\spring-aop_annotation-5.3.6.jar;D:\Environment\apache-maven-3.6.1\repository\org\springframework\spring-beans\5.3.6\spring-beans-5.3.6.jar;D:\Environment\apache-maven-3.6.1\repository\org\springframework\spring-expression\5.3.6\spring-expression-5.3.6.jar;D:\Environment\apache-maven-3.6.1\repository\junit\junit\4.13.2\junit-4.13.2.jar;D:\Environment\apache-maven-3.6.1\repository\org\hamcrest\hamcrest-core\1.3\hamcrest-core-1.3.jar;D:\Environment\apache-maven-3.6.1\repository\commons-logging\commons-logging\1.2\commons-logging-1.2.jar" com.intellij.rt.junit.JUnitStarter -ideVersion5 -junit3 pojo.BookTest,testSingleton
pojo.Book@69b0fd6f
pojo.Book@69b0fd6f

Process finished with exit code 0
```
3. 设置为多实例
- spring配置文件中bean标签的scope属性值：
    1) singleton：单实例
       ```xml
        <bean id="book" class="pojo.Book" scope="singleton">
            <!-- 使用property完成属性注入 -->
            <property name="name" value="西游记"></property>
            <property name="author" value="罗贯中"></property>
        </bean>
       ```
    2) prototype：多实例
        ```xml
            <bean id="book" class="pojo.Book" scope="prototype">
                <!-- 使用property完成属性注入 -->
                <property name="name" value="西游记"></property>
                <property name="author" value="罗贯中"></property>
            </bean>
        ```
    3) request
    4) session
4. singleton和prototype的区别
   - scope="singleton":加载spring配置文件*ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml")*时就会创建单实例对象
    
   - scope="prototype":在调用context.getBean()方法时创建对象
    
#### bean生命周期
1. 生命周期：从对象创建到销毁的过程
2. 具体过程
    1) 通过构造器创建bean实例（无参构造）
    2) 为bean的属性赋值和对其他bean的引用（调用set方法）
    3) 把bean实例传递给bean后置处理器的方法 postProcessBeforeInitialization
    4) 调用bean的初始化方法（需要进行配置）
    5) 把bean实例传递给bean后置处理器的方法 postProcessAfterInitialization
    6) 使用bean
    7) 当容器关闭时，调用bean的销毁方法（需要进行配置）
3. 演示
    1) 创建实体类Person.java
    ```java
    public class Person {
        private String pname;
    
        public Person() {
            System.out.println("第一步：无参构造创建bean实例");
        }
    
        public void setPname(String pname) {
            this.pname = pname;
            System.out.println("第二步：调用set方法设置属性值");
        }
    
        //创建执行的初始化方法
        public void init(){
            System.out.println("第三步：执行初始化方法");
        }
    
        //销毁对象的方法
        public void destroy(){
            System.out.println("第五步：执行销毁方法");
        }
    }
    ```
    2) 创建后置处理器实现类
    ```java
    public class BeanPostProcessor implements org.springframework.beans.factory.config.BeanPostProcessor {
        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            System.out.println("在bean的初始化方法执行前");
            return org.springframework.beans.factory.config.BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
        }
    
        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            System.out.println("在bean的初始化方法执行后");
            return org.springframework.beans.factory.config.BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
        }
    }
    ```
    2) 创建配置文件 bean8.xml
    ```xml
    <?xml version="1.0" encoding="UTF-8" ?>
    <beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    
        <!-- 普通bean -->
        <bean id="person" class="beanlife.Person" init-method="init" destroy-method="destroy">
            <property name="pname" value="张三"></property>
        </bean>
    
        <!-- 配置后置处理器 -->
        <bean id="postProcessor" class="beanlife.BeanPostProcessor"></bean>
    
    </beans>
    ```
    3) 创建测试方法
    ```java
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
    ```
   4) 查看控制台输出
    ```shell
    D:\Environment\Java\jdk1.8.0_181\bin\java.exe -Dvisualvm.id=270658998128500 -ea -Didea.test.cyclic.buffer.size=1048576 "-javaagent:D:\Program Files\IntelliJ IDEA 2020.3.1\lib\idea_rt.jar=12268:D:\Program Files\IntelliJ IDEA 2020.3.1\bin" -Dfile.encoding=UTF-8 -classpath "D:\Program Files\IntelliJ IDEA 2020.3.1\lib\idea_rt.jar;D:\Program Files\IntelliJ IDEA 2020.3.1\plugins\junit\lib\junit5-rt.jar;D:\Program Files\IntelliJ IDEA 2020.3.1\plugins\junit\lib\junit-rt.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\charsets.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\deploy.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\ext\access-bridge-64.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\ext\cldrdata.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\ext\dnsns.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\ext\jaccess.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\ext\jfxrt.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\ext\localedata.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\ext\nashorn.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\ext\sunec.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\ext\sunjce_provider.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\ext\sunmscapi.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\ext\sunpkcs11.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\ext\zipfs.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\javaws.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\jce.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\jfr.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\jfxswt.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\jsse.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\management-agent.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\plugin.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\resources.jar;D:\Environment\Java\jdk1.8.0_181\jre\lib\rt.jar;D:\Study\spring5-study\demo1-IOC\target\test-classes;D:\Study\spring5-study\demo1-IOC\target\classes;D:\Environment\apache-maven-3.6.1\repository\org\springframework\spring-core\5.3.6\spring-core-5.3.6.jar;D:\Environment\apache-maven-3.6.1\repository\org\springframework\spring-jcl\5.3.6\spring-jcl-5.3.6.jar;D:\Environment\apache-maven-3.6.1\repository\org\springframework\spring-context\5.3.6\spring-context-5.3.6.jar;D:\Environment\apache-maven-3.6.1\repository\org\springframework\spring-aop_annotation\5.3.6\spring-aop_annotation-5.3.6.jar;D:\Environment\apache-maven-3.6.1\repository\org\springframework\spring-beans\5.3.6\spring-beans-5.3.6.jar;D:\Environment\apache-maven-3.6.1\repository\org\springframework\spring-expression\5.3.6\spring-expression-5.3.6.jar;D:\Environment\apache-maven-3.6.1\repository\junit\junit\4.13.2\junit-4.13.2.jar;D:\Environment\apache-maven-3.6.1\repository\org\hamcrest\hamcrest-core\1.3\hamcrest-core-1.3.jar;D:\Environment\apache-maven-3.6.1\repository\commons-logging\commons-logging\1.2\commons-logging-1.2.jar" com.intellij.rt.junit.JUnitStarter -ideVersion5 -junit3 beanlife.PersonTest,test1
    第一步：无参构造创建bean实例
    第二步：调用set方法设置属性值
    在bean的初始化方法执行前
    第三步：执行初始化方法
    在bean的初始化方法执行后
    第四步：获取bean实例使用
    第五步：执行销毁方法
    
    Process finished with exit code 0
    ```
   
#### xml自动装配
1. 自动装配：根据指定装配规则（属性名称或属性类型）自动将匹配的属性值注入
2. 演示自动装配过程
    1) 根据属性名称自动装配
        - 创建实体类
        ```java
        public class Dept {
            @Override
            public String toString() {
                return "Dept{}";
            }
        }
        ```
       ```java
        public class Emp {
            private Dept dept;
        
            public void setDept(Dept dept) {
                this.dept = dept;
            }
        
            @Override
            public String toString() {
                return "Emp{" +
                        "dept=" + dept +
                        '}';
            }
        
            public void test(){
                System.out.println(dept);
            }
        }
        ```
    - 创建配置文件 autowire.xml
        ```xml
        <?xml version="1.0" encoding="UTF-8" ?>
        <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
        
            <!-- 手动装配 -->
        <!--    <bean id="emp" class="autowire.Emp">-->
        <!--        <property name="dept" ref="dept"></property>-->
        <!--    </bean>-->
        <!--    <bean id="dept" class="autowire.Dept"></bean>-->
        
            <!-- 自动装配autowire
                byName:根据属性名称注入，即bean的id值应于属性名称一致
                byType:根据属性类型注入
                -->
            <bean id="emp" class="autowire.Emp" autowire="byName"></bean>
            <bean id="dept" class="autowire.Dept"></bean>
        
        </beans>
        ```
    - 测试类
        ```java
        public class EmpTest extends TestCase {
        
            @Test
            public void testTest1() {
                //加载配置文件
                ApplicationContext context = new ClassPathXmlApplicationContext("autowire.xml");
                //获取配置创建的对象
                Emp emp = context.getBean("emp", Emp.class);
                emp.test();
            }
        }
        ```
   
    2) 根据属性类型注入 autowire="byType"

#### 外部属性文件
1. 直接配置属性（以数据库连接池为例）
创建配置文件 bean10.xml
   ```xml
    <?xml version="1.0" encoding="UTF-8" ?>
    <beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    
        <!-- 直接配置连接池 -->
        <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
            <property name="driverClassName" value="com.mysql.jdbc.Driver"></property>
            <property name="url" value="jdbc:mysql://localhost:3306/test"></property>
            <property name="username" value="root"></property>
            <property name="password" value="123456"></property>
        </bean>
    
    </beans>
   ```
    
2. 引入外部属性文件配置数据库连接池
    1) 创建外部属性文件 jdbc.properties
    ```properties
    prop.driverclass=com.mysql.jdbc.Driver
    prop.url=jdbc:mysql://localhost:3306/test
    prop.username=root
    prop.password=123456
    ```
    2) 创建spring配置文件 bean11.xml，并引入外部属性文件
    ```xml
    <?xml version="1.0" encoding="UTF-8" ?>
    <beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
    http://www.springframework.org/schema/context http://www.springframework.org/schema/beans/spring-context.xsd">
    
        <context:property-placeholder location="classpath:jdbc.properties"/>
        <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
            <property name="driverClassName" value="${prop.driverclass}"></property>
            <property name="url" value="${prop.url}"></property>
            <property name="username" value="${prop.username}"></property>
            <property name="password" value="${prop.password}"></property>
        </bean>
    
    </beans>
    ```

