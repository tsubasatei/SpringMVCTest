package com.xt.springmvc.handlers;

import com.xt.springmvc.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorld {

    public HelloWorld() {
        System.out.println("HelloWorld Construct");
    }

    @Autowired
    private HelloService helloService;
    /**
     * 1. 使用 @RequestMapping 注解来映射请求的 URL
     * 2. 返回值会通过视图解析器解析为实际的物理视图，对于 InternalResourceViewResolver 视图解析器，会做如下的解析：
     *    通过 prefix + returnVal + suffix 这样的方式得到实际的物理视图，然后会做转发操作
     *        /WEB-INF/views/success.jsp
     * @return
     */
    @RequestMapping("helloWorld")
    public String hello() {
        System.out.println("hello world");
        System.out.println(helloService);
        return "success";
    }
}
