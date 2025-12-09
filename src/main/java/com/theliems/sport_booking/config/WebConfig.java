//package com.thliems.sport_booking.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//        registry.addInterceptor(new AuthInterceptor())
//                .addPathPatterns("/**")            // áp dụng tất cả
//                .excludePathPatterns(
//                        "/login", "/register",     // ngoại lệ
//                        "/css/**", "/js/**", "/images/**"
//                );
//    }
//}
