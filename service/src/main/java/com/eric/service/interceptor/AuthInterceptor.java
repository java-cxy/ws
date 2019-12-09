package com.eric.service.interceptor;

import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.soap.SOAPException;
import java.util.List;

/**
 * @Description AuthInterceptor
 * @Date 2019/12/10 6:21
 * @Author LSM
 **/
public class AuthInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

    private static final String USERNAME="root";
    private static final String PASSWORD="admin";

    public AuthInterceptor() {
        //定义在哪个阶段进行拦截
        //PRE_PROTOCOL执行协议前，进行拦截
        super(Phase.PRE_PROTOCOL);
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        List<Header> headers = null;
        String username=null;
        String password=null;

        try {
            headers = message.getHeaders();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("headers size="+headers.size());

        if (headers == null || headers.size() == 0) {
            throw new Fault(new IllegalArgumentException("找不到Header，无法验证用户信息"));
        }

        //获取用户名,密码
        for (Header header : headers) {
            SoapHeader soapHeader = (SoapHeader) header;
            Element e = (Element) soapHeader.getObject();
            NodeList usernameNode = e.getElementsByTagName("username");
            NodeList pwdNode = e.getElementsByTagName("password");
            username=usernameNode.item(0).getTextContent();
            password=pwdNode.item(0).getTextContent();
            if( StringUtils.isEmpty(username)|| StringUtils.isEmpty(password)){
                throw new Fault(new IllegalArgumentException("用户信息为空"));
            }
        }

        System.out.println("username = "+username);
        System.out.println("password = "+password);

        //校验用户名密码
        if(!(username.equals(USERNAME) && password.equals(PASSWORD))){
            SOAPException soapExc = new SOAPException("认证失败");
            throw new Fault(soapExc);
        }
    }
}
