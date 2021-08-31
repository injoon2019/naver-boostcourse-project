package com.ntscorp.intern.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = { "com.ntscorp.intern"})
@Import({ DBConfig.class })
public class ApplicationConfig {

}
