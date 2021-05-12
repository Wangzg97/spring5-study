package pojo;

/**
 * 演示使用set方法注入
 * @author: wangzg
 * @date 2021/5/10 22:54
 * modified by: wangzg
 */
public class Book {
    private String name;
    private String author;

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void testInfo() {
        System.out.println(this.name+"---"+this.author);
    }
}
