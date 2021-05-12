package zhujie.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author: wangzg
 * @date 2021/5/12 1:21
 * modified by: wangzg
 */
@Configuration //作为配置类替代xml配置文件
@ComponentScan(basePackages = {"zhujie"})
public class SpringConfig {

}
