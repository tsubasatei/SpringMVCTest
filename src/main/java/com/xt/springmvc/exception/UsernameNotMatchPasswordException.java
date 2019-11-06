package com.xt.springmvc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @ResponseStatus注解有两种用法，一种是加载自定义异常类上，一种是加在目标方法中
 *
 * 注解中有两个参数，value属性设置异常的状态码，reason 是异常的描述
 *
 * 标注在类上时:
 *  1. 若有 @ControllerAdvice 修饰的类，@ExceptionHandler(ArithmeticException.class) 中的异常类的类型可以匹配，返回 error 页面。
 *  2. 若无，返回同标注在方法上
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "用户名和密码不匹配")
public class UsernameNotMatchPasswordException extends RuntimeException{
}
