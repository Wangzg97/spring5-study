package autowire;

/**
 * @author: wangzg
 * @date 2021/5/11 23:53
 * modified by: wangzg
 */
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
