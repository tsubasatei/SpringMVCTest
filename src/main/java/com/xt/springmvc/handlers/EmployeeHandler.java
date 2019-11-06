package com.xt.springmvc.handlers;

import com.xt.springmvc.dao.DepartmentDao;
import com.xt.springmvc.dao.EmployeeDao;
import com.xt.springmvc.entities.Department;
import com.xt.springmvc.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;

/**
 * 员工管理
 */
@Controller
@RequestMapping("/emp")
public class EmployeeHandler {
    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    DepartmentDao departmentDao;

    @ModelAttribute
    public void getEmployee(@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {
        if (id != null) {
            map.put("employee", employeeDao.get(id));
        }

    }

    @GetMapping("/emps")
    public String list(Map<String, Object> map) {
        Collection<Employee> employees = employeeDao.getAll();
        map.put("emps", employees);
        return "emp/list";
    }

    @GetMapping("/emp")
    public String toAddPage(Map<String, Object> map) {
        Collection<Department> departments = departmentDao.getDepartments();
        map.put("depts", departments);
        // 和 add.jsp 页面 modelAttribute 属性的值一致
        map.put("employee", new Employee());
        return "emp/add";
    }

    /**
     * 1. 在表单/命令对象类的属性中标注校验注解，
     * 2. 在处理方法对应的入参前添加 @Valid，
     * 3. Spring MVC 就会实施校验并将校验结果保存在被校验入参对象之后的 BindingResult 或 Errors 入参中
     *
     * BindingResult 或 Errors 类型
     * 需校验的 Bean 对象和其绑定结果对象或错误对象时成对出现的，它们之间不允许声明其他的入参
     *
     * @param employee
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/emp")
    public String add(@Valid Employee employee, BindingResult bindingResult,
                      Map<String, Object> map) {
        System.out.println("保存的员工信息：" + employee);
        if (bindingResult.getErrorCount() > 0) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                System.out.println(error.getField() + " : " + error.getDefaultMessage());
            }
            //若验证出错, 则转向定制的页面
            map.put("depts", departmentDao.getDepartments());
            return "emp/add";
        }
        employeeDao.save(employee);
        return "redirect:/emp/emps";
    }

    @DeleteMapping("/emp/{id}")
    public String delete(@PathVariable Integer id) {
        employeeDao.delete(id);
        return "redirect:/emp/emps";
    }

    @GetMapping("/emp/{id}")
    public String toEditPage(@PathVariable Integer id, Map<String, Object> map) {
        Employee employee = employeeDao.get(id);
        Collection<Department> departments = departmentDao.getDepartments();
        map.put("employee", employee);
        map.put("depts", departments);

        return "emp/add";
    }

    @PutMapping("/emp")
    public String update(Employee employee) {
        System.out.println("修改的员工信息：" + employee);
        employeeDao.save(employee);
        return "redirect:/emp/emps";
    }

    /**
     * 由 @InitBinder 标识的方法• ，可以对 WebDataBinder 对象进行初始化。
     * WebDataBinder 是 DataBinder 的子类，用于完成由表单字段到 JavaBean 属性的绑定
     * @InitBinder 方法不能有返回值，它必须声明为void• 。
     * @InitBinder 方法的参数通常是是 WebDataBinder •
     *
     * 不自动绑定对象中的 lastName 属性，另行处理
     * @param binder
     */
    /*
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("lastName");
    }
    */
}
