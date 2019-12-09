package com.eric.service.service;

import org.springframework.stereotype.Service;

import javax.jws.WebService;

/**
 * @Description HelloServiceImpl
 * 注意：targetNamespace的值时HelloService包的反向
 * @Date 2019/12/9 22:23
 * @Author LSM
 **/
@WebService(
        targetNamespace = "http://service.service.eric.com/",
        serviceName = "helloService",
        endpointInterface = "com.eric.service.service.HelloService"
)
@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String name) {
        System.out.println("欢迎你"+name);
        return "欢迎你"+name;
    }
}
