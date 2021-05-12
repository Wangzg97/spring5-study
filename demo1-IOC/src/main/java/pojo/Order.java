package pojo;

/**
 *  * 演示使用有参构造方法注入
 * @author: wangzg
 * @date 2021/5/10 23:03
 * modified by: wangzg
 */
public class Order {
    private String name;
    private String address;

    public Order(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public void testInfo() {
        System.out.println(this.name+"--"+this.address);
    }
}
