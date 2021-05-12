package aop_annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author: wangzg
 * @date 2021/5/12 23:11
 * modified by: wangzg
 */
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

    //环绕通知
    @Around(value = "execution(* aop_annotation.User.test(..))")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("环绕前......");
        //被增强的方法
        proceedingJoinPoint.proceed();
        System.out.println("环绕后......");
    }
}
