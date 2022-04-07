package com.test.service.hosp.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author DearSil
 * @date 2022/4/6 14:46
 */
@Configuration
@MapperScan(basePackages = "com.test.service.hosp.mapper")
public class HospConfiguration {

    /**
     * 分页查询的初始化操作，如果没有这段代码，则分页查询不可用
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
