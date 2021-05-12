package collection;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: wangzg
 * @date 2021/5/11 0:41
 * modified by: wangzg
 */
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
        System.out.println(this.courseList);
    }
}
