package aop_annotation.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author: wangzg
 * @date 2021/5/13 0:12
 * modified by: wangzg
 */
@Configuration
@ComponentScan(basePackages = {"aop_annotation"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ConfigAop {

}
