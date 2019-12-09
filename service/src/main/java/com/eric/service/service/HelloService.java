package com.eric.service.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @Description HelloService
 * @Date 2019/12/9 22:23
 * @Author LSM
 **/
@WebService
public interface HelloService {

    /**
     * Hello
     * @param name
     * @return
     */
    @WebMethod
    String hello(@WebParam(name = "name")String name);

}
