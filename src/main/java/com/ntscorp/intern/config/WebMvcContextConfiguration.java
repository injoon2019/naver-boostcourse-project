package com.ntscorp.intern.config;

import com.ntscorp.intern.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.ntscorp.intern"})
@ImportResource("classpath:/ApplicationContext.xml")
public class WebMvcContextConfiguration extends WebMvcConfigurerAdapter{
	private static final int CACHE_SECONDS = 86400;
	private static final int MAX_UPLOAD_BYTES = 10485760;
	private static final String RESOLVER_PREFIX = "/WEB-INF/views/";
	private static final String RESOLVER_SUFFIX = ".jsp";
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/assets/**")
			.addResourceLocations("classpath:/META-INF/resources/webjars/").setCachePeriod(CACHE_SECONDS);
        registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(CACHE_SECONDS);
        registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(CACHE_SECONDS);
        registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(CACHE_SECONDS);
	}
	
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    
    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("main");
        registry.addViewController("/detail").setViewName("detail");
        registry.addViewController("/booking").setViewName("reservation");
        registry.addViewController("/review").setViewName("review");
        registry.addViewController("/bookinglogin").setViewName("bookinglogin");
        registry.addViewController("/myreservation").setViewName("myreservation");
        registry.addViewController("/reviewwrite").setViewName("reviewwrite");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/myreservation");
    }
    
    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix(RESOLVER_PREFIX);
        resolver.setSuffix(RESOLVER_SUFFIX);
        return resolver;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(MAX_UPLOAD_BYTES);
        return multipartResolver;
    }
}
