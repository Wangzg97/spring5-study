package beanlife;

/**
 * @author: wangzg
 * @date 2021/5/11 23:25
 * modified by: wangzg
 */
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
