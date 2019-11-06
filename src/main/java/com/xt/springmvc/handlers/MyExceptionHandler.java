package com.xt.springmvc.handlers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author xt
 * @create 2019/11/7 1:44
 * @Desc
 */
@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(ArithmeticException.class)
    public ModelAndView handleException(Exception ex) {
        System.out.println("Exception 出异常了：" + ex);
        ModelAndView mv = new ModelAndView("error");
        mv.addObject("exception", ex);

        return mv;
    }
}
