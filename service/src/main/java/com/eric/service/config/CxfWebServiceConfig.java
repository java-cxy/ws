package com.eric.service.config;

import com.eric.service.interceptor.AuthInterceptor;
import com.eric.service.service.HelloService;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * @Description CxfWebServiceConfig
 * @Date 2019/12/9 22:24
 * @Author LSM
 **/
@Configuration
public class CxfWebServiceConfig {

    @Autowired
    private Bus bus;
    @Autowired
    private HelloService helloService;

    @Bean("cxfServletRegistration")
    public ServletRegistrationBean dispatcherServlet() {
        //注册servlet 拦截/ws 开头的请求 不设置 默认为：/services/*
        return new ServletRegistrationBean(new CXFServlet(), "/ws/*");
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    /**
     * 发布endpoint
     * @return
     */
    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, helloService);
        //对用户名密码校验拦截
        endpoint.getInInterceptors().add(new AuthInterceptor());
        //发布地址
        endpoint.publish("/helloService");
        return endpoint;
    }
}
