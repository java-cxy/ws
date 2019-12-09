package com.eric.service.service;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @Description HelloService
 * @Date 2019/12/9 22:57
 * @Author LSM
 **/
@WebService
public interface HelloService {

    /**
     * hello
     * @param name
     * @return
     */
    String hello(@WebParam(name="name") String name);

}
