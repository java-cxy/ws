package com.eric.client.client;

import com.eric.interceptor.LoginInterceptor;
import com.eric.service.service.HelloService;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

/**
 * @Description
 * @Date 2019/12/9 22:59
 * @Author LSM
 **/
public class ClientTest {

    public static void main(String[] args) {
        //staticFactory();
        dynamicProxy();
    }

    /**
     * 静态工厂方法
     */
    private static void staticFactory(){
        try {
            // 接口地址
            String address = "http://127.0.0.1:8088/ws/helloService?wsdl";
            // 代理工厂
            JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
            // 设置代理地址
            jaxWsProxyFactoryBean.setAddress(address);
            // 设置接口类型
            jaxWsProxyFactoryBean.setServiceClass(HelloService.class);
            // 创建一个代理接口实现
            HelloService us = (HelloService) jaxWsProxyFactoryBean.create();
            // 数据准备
            String userId = "zz";
            // 调用代理接口的方法调用并返回结果
            String result = us.hello(userId);
            System.out.println("返回结果:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 动态代理
     */
    private static void dynamicProxy(){
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://127.0.0.1:8088/ws/helloService?wsdl");
        // 需要密码的情况需要加上用户名和密码
        client.getOutInterceptors().add(new LoginInterceptor("root","admin"));
        Object[] objects = new Object[0];
        try {
            // invoke("方法名",参数1,参数2,参数3....);
            objects = client.invoke("hello", "maple");
            System.out.println("返回数据:" + objects[0]);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }
}
