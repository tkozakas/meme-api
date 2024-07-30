package org.churk.memeapi.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<RequestFilter> loggingFilter() {
        FilterRegistrationBean<RequestFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new RequestFilter());
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }
}
