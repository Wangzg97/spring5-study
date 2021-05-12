package aop_annotation;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author: wangzg
 * @date 2021/5/12 23:44
 * modified by: wangzg
 */
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
