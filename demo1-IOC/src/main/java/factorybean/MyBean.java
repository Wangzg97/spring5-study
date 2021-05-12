package factorybean;

import collection.Course;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author: wangzg
 * @date 2021/5/11 23:00
 * modified by: wangzg
 */
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
