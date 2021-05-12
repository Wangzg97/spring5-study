package bean;

/**
 * @author: wangzg
 * @date 2021/5/10 23:56
 * modified by: wangzg
 */
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
