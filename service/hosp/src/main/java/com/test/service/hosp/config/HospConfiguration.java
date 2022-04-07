package com.test.service.hosp.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author DearSil
 * @date 2022/4/6 14:46
 */
@Configuration
@MapperScan(basePackages = "com.test.service.hosp.mapper")
public class HospConfiguration {
}
