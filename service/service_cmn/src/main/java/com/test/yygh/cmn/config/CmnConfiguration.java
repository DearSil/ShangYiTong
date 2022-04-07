package com.test.yygh.cmn.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author DearSil
 * @date 2022/4/7 21:30
 */
@Configuration
@MapperScan("com.test.yygh.cmn.mapper")
@EnableSwagger2
//需要把整个项目的依赖包初始化建造对象
@ComponentScan("com.test")
public class CmnConfiguration {

    //配置分页
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }

}
