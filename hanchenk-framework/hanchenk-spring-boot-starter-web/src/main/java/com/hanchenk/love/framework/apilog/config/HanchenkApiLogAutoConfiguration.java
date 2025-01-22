package com.hanchenk.love.framework.apilog.config;

import com.hanchenk.love.framework.apilog.core.filter.ApiAccessLogFilter;
import com.hanchenk.love.framework.apilog.core.interceptor.ApiAccessLogInterceptor;
import com.hanchenk.love.framework.common.enums.WebFilterOrderEnum;
import com.hanchenk.love.framework.web.config.WebProperties;
import com.hanchenk.love.framework.web.config.HanchenkWebAutoConfiguration;
import com.hanchenk.love.module.infra.api.logger.ApiAccessLogApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@AutoConfiguration(after = HanchenkWebAutoConfiguration.class)
public class HanchenkApiLogAutoConfiguration implements WebMvcConfigurer {

    /**
     * 创建 ApiAccessLogFilter Bean，记录 API 请求日志
     */
    @Bean
    @ConditionalOnProperty(prefix = "hanchenk.access-log", value = "enable", matchIfMissing = true) // 允许使用 hanchenk.access-log.enable=false 禁用访问日志
    public FilterRegistrationBean<ApiAccessLogFilter> apiAccessLogFilter(WebProperties webProperties,
                                                                         @Value("${spring.application.name}") String applicationName,
                                                                         ApiAccessLogApi apiAccessLogApi) {
        ApiAccessLogFilter filter = new ApiAccessLogFilter(webProperties, applicationName, apiAccessLogApi);
        return createFilterBean(filter, WebFilterOrderEnum.API_ACCESS_LOG_FILTER);
    }

    private static <T extends Filter> FilterRegistrationBean<T> createFilterBean(T filter, Integer order) {
        FilterRegistrationBean<T> bean = new FilterRegistrationBean<>(filter);
        bean.setOrder(order);
        return bean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ApiAccessLogInterceptor());
    }

}
