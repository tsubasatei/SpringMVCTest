package com.xt.springmvc.handlers;

import com.xt.springmvc.dao.EmployeeDao;
import com.xt.springmvc.entities.Employee;
import com.xt.springmvc.entities.User;
import com.xt.springmvc.exception.UsernameNotMatchPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.*;

/**
 * @SessionAttributes : 除了可以通过属性名指定需要放到会话中的属性外（实际上使用的是 value 属性值），
 *      还可以通过模型属性的对象类型指定哪些模型属性需要放到会话中（实际上使用的是 types 属性值）
 *
 * 注意：该注解只能放在类的上面，而不能修饰方法
 */
@SessionAttributes(value = {"user"}, types = {String.class})
@Controller
@RequestMapping("/springMVC")
public class SpringMVCTest {

    private static final String SUCCESS = "success";
    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private ResourceBundleMessageSource messageSource;

    @GetMapping("/testSimpleMappingExceptionResolver")
    public String testSimpleMappingExceptionResolver(@RequestParam("i") int i) {
        String[] arrs = new String[10];
        System.out.println(arrs[i]);
        return SUCCESS;
    }

    /**
     * 若有 @ControllerAdvice 修饰的类，@ExceptionHandler(ArithmeticException.class) 中的异常类的类型可以匹配，返回 error 页面。
     * 若无，返回同标注在方法上
     * @return
     */
    @GetMapping("/testDefaultHandlerExceptionResolver")
    public String testDefaultHandlerExceptionResolver() {
        System.out.println("testDefaultHandlerExceptionResolver");
        return SUCCESS;
    }

    /**
     * @ResponseStatus 标注在方法上 返回 页面 HTTP Status 404 – Not Found
     * @param i
     * @return
     */
//    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "测试")
    @GetMapping("/testResponseStatusExceptionResolver")
    public String testResponseStatusExceptionResolver(@RequestParam("i") int i) {
        if (i == 13) {
            throw new UsernameNotMatchPasswordException();
        }
        System.out.println("testResponseStatusExceptionResolver");
        return SUCCESS;
    }

    /**
     * 1. @ExceptionHandler 方法的入参中可以加入 Exception 类型的参数, 该参数即对应发生的异常对象
     * 2. @ExceptionHandler 方法的入参中不能传入 Map. 若希望把异常信息传到页面上, 需要使用 ModelAndView 作为返回值
     * 3. @ExceptionHandler 方法标记的异常有优先级的问题.
     * 4. @ControllerAdvice: 如果在当前 Handler 中找不到 @ExceptionHandler 方法来处理当前方法出现的异常,
     * 则将去 @ControllerAdvice 标记的类中查找 @ExceptionHandler 标记的方法来处理异常.
     */
   /* @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleException2(Exception ex) {
        System.out.println("RuntimeException 出异常了：" + ex);
        ModelAndView mv = new ModelAndView("error");
        mv.addObject("exception", ex);
        return mv;
    }*/

   /* @ExceptionHandler(ArithmeticException.class)
    public ModelAndView handleException(Exception ex) {
        System.out.println("ArithmeticException 出异常了：" + ex);
        ModelAndView mv = new ModelAndView("error");
        mv.addObject("exception", ex);
        return mv;
    }*/

    @GetMapping("/testExceptionHandlerExceptionResolver")
    public String testExceptionHandlerExceptionResolver(@RequestParam int i) {
        System.out.println("result: " + 10/i);
        return SUCCESS;
    }

    @PostMapping("/testFileUpload")
    public String testFileUpload(@RequestParam("desc") String desc, @RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("desc: " + desc);
        System.out.println("fileName: " + file.getOriginalFilename());
        System.out.println("inputStream: " + file.getInputStream());

        return SUCCESS;
    }

    @GetMapping("/testI18n")
    public String testI18n(Locale locale) {
        String username = messageSource.getMessage("i18n.username", null, locale);
        System.out.println(username);
        return "i18n";
    }

    /**
     * 模拟文件下载
     * @param session
     * @return
     * @throws IOException
     */
    @GetMapping("/testResponseEntity")
    public ResponseEntity<byte[]> testResponseEntity(HttpSession session) throws IOException {
        byte[] body;
        ServletContext servletContext = session.getServletContext();
        // 文件的位置 为 webapp/WEB-INF下的文件
        InputStream in = servletContext.getResourceAsStream("WEB-INF/file/application-test.yml");
        body = new byte[in.available()];
        in.read(body);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment;filename=application-test.yml");
        HttpStatus status = HttpStatus.OK;

        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(body, headers, status);
        return responseEntity;
    }


    // 不配置 CommonsMultipartResolver 时，可用
    @ResponseBody
    @PostMapping("/testHttpMessageConverter")
    public String testHttpMessageConverter(@RequestBody String body) {
        System.out.println(body);
        return "Hello " + new Date();
    }

    @ResponseBody
    @PostMapping("/testJson")
    public Collection<Employee> testJson() {
        Collection<Employee> employees = employeeDao.getAll();
        return employees;
    }

    @PostMapping("/testConvert")
    public String testConvert(@RequestParam("employee")Employee employee) {
        System.out.println("员工信息：" + employee);
        employeeDao.save(employee);
        return "redirect:/emp/emps";
    }

    @GetMapping("/testRedirect")
    public String testRedirect() {
        System.out.println("testRedirect");
        return "redirect:/index.jsp";
    }

    @GetMapping("/testHelloView")
    public String testHelloView() {
        System.out.println("testHelloView");
        return "helloView";
    }

    @GetMapping("/testViewAndViewResolver")
    public String testViewAndViewResolver() {
        System.out.println("testViewAndViewResolver");
        return SUCCESS;
    }

    /**
     * 1. 有 @ModelAttribute 标记的方法，会在每个目标方法执行之前被 SpringMVC 调用
     * 2. @ModelAttribute 注解也可以来修饰目标方法 POJO 类型的入参，其 value 属性值有如下作用：
     *  1). SpringMVC 会使用 value 属性值在 implicitModel 中查找对应的对象，若存在则会直接传入到目标方法的入参中
     *  2). SpringMVC 会以 这个 value 为 key，POJO 类型的对象为 value，存入到 request 中
     * @param id
     * @param map
     */
    @ModelAttribute
    public void getUser(@RequestParam(value="id", required = false) Integer id,
                        Map<String, Object> map) {
        System.out.println("ModelAttribute method");
        if (id != null) {
            // 模拟从数据库中获取对象
            User user = new User(1, "Tom", "123456", "tom@163.com", 30);
            System.out.println("从数据库中获取一个对象：" + user);
            map.put("user", user);
        }
    }

    /**
     * 运行流程：
     * 1. 执行 @ModelAttribute 注解修饰的方法：从数据库中取出对象，把对象放入到 Map 中，键为 user
     * 2. SpringMVC 从 Map 中取出 user 对象，并把表单的请求参数赋给该 user 对象的对应属性
     * 3. SpringMVC 把上述对象传入目标方法的参数
     *
     * 注意：在 @ModelAttribute 修饰的方法中，放入到 Map 时的键需要和目标方法入参类型的第一个字母小写的字符串一致
     *
     * SpringMVC 确定目标方法 POJO 类型入参的过程
     * 1. 确定一个 key
     *  1). 若目标方法的 POJO 类型的参数没有使用 @ModelAttribute 作为修饰，则 key 为 POJO 类名第一个字母的小写
     *  2). 若使用了 @ModelAttribute 来修饰，则 key 为 @ModelAttribute 注解的 value 属性值
     *
     * 2. 在 implicitModel 中查找 key 对应的对象，若存在，则作为入参传入
     *  1). 若在 @ModelAttribute 标记的方法中在 Map 中保存过，且 key 和 1确定的 key 一致，则会获取到
     *
     * 3. 若 implicitModel 中不存在 key 对应的对象，则检查当前的 Handler 是否使用了 @SessionAttributes 注解修饰，
     *  若使用了该注解，且 @SessionAttributes 注解的 value 属性值中包含了 key，则会从 HttpSession 中来
     *  获取 key 所对应的 value 值，若存在则直接传入到目标方法的入参中。若不存在则将抛出异常
     * 4. 若 Handler 没有标识 @SessionAttributes 注解或 @SessionAttributes 注解的 value 值中不包含 key，
     *   则会通过反射来创建 POJO 类型的参数，传入为目标方法的参数
     * 5. SpringMVC 会把 key 和 POJO 类型的对象保存到 implicitModel 中，进而会保存到 request 中
     *
     * 源代码分析的流程：
     * 1. 调用 @ModelAttribute 注解修饰的方法，实际上把 @ModelAttribute 方法中的 Map 中的数据放在了 implicitModel 中。
     * 2. 解析请求处理器的目标参数，实际上该目标参数来自于 WebDataBinder 对象的 target 属性
     *      1). 创建 WebDataBinder 对象：
     *      ①. 确定 objectName 属性：
     *      若传入的 attrName 属性值为 "", 则 objectName 为类名第一个字母小写
     *      注意：attrName，若目标方法的 POJO 属性使用了 @ModelAttribute 来修饰，
     *           则 attrName 值即为 @ModelAttribute 的 value 属性值
     *      ②. 确定 target 属性：
     *          > 在 implicitModel 中查找 attrName 对应的属性值。若存在，ok
     *          > 若不存在：则验证当前 Handler 是否使用了 @SessionAttributes 进行修饰，若使用了，
     *                      则尝试从 Session 中获取 attrName 所对应的值。
     *                      若 Session 中没有对应的属性值，则抛出异常？？？(没有抛出异常！！！)
     *          > 若 Handler 没有使用 @SessionAttributes 进行修饰，或 @SessionAttributes 中没有使用
     *              value 值指定的 key 和 attrName 相匹配，则通过反射创建 POJO 对象
     *
     *      2). SpringMVC 把表单的请求参数赋值给了 WebDataBinder 的 target 对应的属性
     *      3). SpringMVC 会把 WebDataBinder 的 attrName 和 target 给到 implicitModel，进而传到域对象中
     *      4). 把 WebDataBinder 的 target 作为参数传递给目标方法的入参
     * @param user
     * @return
     */
    @PostMapping("/testModelAttribute")
    public String testModelAttribute(User user) {
        System.out.println("修改：" + user);
        return SUCCESS;
    }

    @GetMapping("/testSessionAttributes")
    public String testSessionAttributes(Map<String, Object> map) {
        User user = new User("Jerry", "qwerty", "jerry@163.com", 20);
        map.put("user", user);
        map.put("it", "SpringMVC");
        return SUCCESS;
    }

    /**
     * 目标方法可以添加 Map 类型(实际上也可以是 Model 类型或 ModelMap 类型)的参数
     * @param map
     * @return
     */
    @GetMapping("/testMap")
    public String testMap(Map<String, Object> map) {
        map.put("names", Arrays.asList("Tom", "Jerry", "Mike"));
        return SUCCESS;
    }
    /**
     * 目标方法的返回值可以是 ModelAndView 类型
     * 其中可以包含视图和模型信息
     * SpringMVC 会把 ModelAndView 的 model 中数据放入到 request 域对象中
     *
     * @return
     */
    @GetMapping("/testModelAndView")
    public ModelAndView testModelAndView() {
        ModelAndView modelAndView = new ModelAndView(SUCCESS);
        // 添加模型数据到 ModelANdView
        modelAndView.addObject("time", new Date());
        return modelAndView;
    }

    /**
     * 可以使用 Servlet 原生的 API 作为目标方法的参数，具体支持以下类型
     * HttpServletRequest
     * HttpServletResponse
     * HttpSession
     * java.security.Principal
     * locale InputStream
     * Reader
     * Writer
     *
     * @param request
     * @param response
     * @param out
     * @return
     * @throws IOException
     */
    @GetMapping("/testServletAPI")
    public void testServletAPI(HttpServletRequest request, HttpServletResponse response, Writer out) throws IOException {
        System.out.println("testServletAPI : request = " + request + ", response = " + response);
        out.write("hello Servlet API");
    }

    /**
     * SpringMVC 会按 请求参数名 和 POJO 属性名 进行自动匹配, 自动为该对象填充属性值。
     * 支持级联属性: 如 dept.deptId、 dept.address.tel
     *
     * @param user
     * @return
     */
    @PostMapping("/testPOJO")
    public String testPOJO(User user) {
        System.out.println("testPOJO : " + user);
        return SUCCESS;
    }

    @GetMapping("/testCookieValue")
    public String testCookieValue(@CookieValue("JSESSIONID") String sessionId) {
        System.out.println("testCookieValue : sessionId = " + sessionId);
        return SUCCESS;
    }

    /**
     * 了解：
     * @RequestHeader ：映射请求头信息
     * 用法同 @RequestParam
     *
     * @param al
     * @return
     */
    @GetMapping("/testRequestHeader")
    public String testRequestHeader(@RequestHeader("Accept-Language") String al) {
        System.out.println("testRequestHeader: " + al);
        return SUCCESS;
    }

    /**
     * @RequestParam ： 来映射请求参数
     *  value 值 ：请求参数的参数名
     *  required ：该参数是否必填。默认为 true
     *  defaultValue ： 请求参数的默认值
     *
     * @param username
     * @param age
     * @return
     */
    @GetMapping("/testRequestParam")
    public String testRequestParam(@RequestParam("username") String username,
                                   @RequestParam(value = "age", required = false, defaultValue = "0") int age) {
        System.out.println("testRequestParam : username = " + username + ", age = " + age);
        return SUCCESS;
    }

    /**
     * 1. Rest 风格的 URL
     * 以 CRUD 为例：
     * 新增：/order    POST
     * 修改：/order/1  PUT        update?id=1
     * 获取：/order/1  GET        get?id=1
     * 删除：/order/1  DELETE     delete?id=1
     *
     * 2. 如何发送 PUT 和 DELETE 请求呢？
     *  1). 需要配置 HiddenHttpMethodFilter
     *  2). 需要发送 POST 请求
     *  3). 需要在发送 POST 请求时携带一个 name="_method" 的隐藏域，值为 DELETE 或 PUT
     *
     * 3. 在 SpringMVC 的目标方法中如何得到 id 呢？
     *      使用 @PathVariable 注解
     *
     * 4. 解决 tomcat7 以上版本 JSPs only permit GET, POST or HEAD. Jasper also permits OPTIONS
     *      在 跳转到的目标页面 添加：<%@ page isErrorPage="true" %>
     *
     * @param id
     * @return
     */
    @DeleteMapping("testRestDelete/{id}")
    public String testRestDelete(@PathVariable("id") Integer id) {
        System.out.println("test Rest Delete : " + id);
        return SUCCESS;
    }

    @PutMapping("testRestPut/{id}")
    public String testRestPut(@PathVariable("id") Integer id) {
        System.out.println("test Rest Put : " + id);
        return SUCCESS;
    }

    @PostMapping("testRestPost")
    public String testRestPost() {
        System.out.println("test Rest Post");
        return SUCCESS;
    }

    @GetMapping("testRestGet/{id}")
    public String testRestGet(@PathVariable("id") Integer id) {
        System.out.println("test Rest Get : " + id);
        return SUCCESS;
    }

    /**
     * @PathVariable 可以来映射 URL 中的占位符到目标方法的参数中
     *
     * @param id
     * @return
     */
    @RequestMapping("testPathVariable/{id}")
    public String testPathVariable(@PathVariable("id") Integer id) {
        System.out.println("testPathVariable, id : " + id);
        return SUCCESS;
    }
    /**
     * 了解：@RequestMapping 支持 Ant 分割资源地址映射
     *  ？ ：匹配文件名中的一个字符
     *  *  ：匹配文件名中的任意字符
     *  ** ：匹配多层路径
      * @return
     */
    @RequestMapping("/testAnt/*/abc")
    public String testAnt() {
        System.out.println("testAnt");
        return SUCCESS;
    }

    /**
     * 了解：@RequestMapping 可以使用 params 和 headers 来更加精确的映射请求。
     * params 和 headers 支持简单的表达式
     * @return
     */
    @RequestMapping(value = "/testParamsAndHeaders", params = {"username", "age!=10"}, headers = { "Accept-Language=zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7" })
    public String testParamsAndHeaders() {
        System.out.println("testParamsAndHeaders");
        return SUCCESS;
    }

    /**
     * 常用：@RequestMapping 使用 method 属性来指定请求方式
     * @return
     */
    @RequestMapping(value = "/testMethod", method = RequestMethod.POST)
    public String testMethod() {
        System.out.println("testMethod");
        return SUCCESS;
    }

    /**
     * 1. @RequestMapping 除了修饰方法，还可以来修饰类
     * 2.
     * 1). 类定义处：提供初步的请求映射信息，相对于 WEB 应用的根目录
     * 2). 方法处：提供进一步的细分映射信息
     *            相对于类定义处的 URL。若类定义处未标注 @RequestMapping，则方法标记的 URL 相对于 WEB 应用的根目录
     * @return
     */
    @RequestMapping("/testRequestMapping")
    public String testRequestMapping() {
        System.out.println("testRequestMapping");
        return SUCCESS;
    }
}

