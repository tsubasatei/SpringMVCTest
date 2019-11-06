package com.xt.springmvc.converter;


import com.xt.springmvc.entities.Department;
import com.xt.springmvc.entities.Employee;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * 自定义转换器
 */
@Component
public class EmployeeConverter implements Converter<String, Employee> {
    public Employee convert(String s) {
        if (s != null) {
            String[] strings = s.split("-");
            if (strings != null && strings.length == 4) {
                String lastName = strings[0];
                String email = strings[1];
                Integer gender = Integer.parseInt(strings[2]);
                Integer deptId = Integer.parseInt(strings[3]);
                Department department = new Department();
                department.setId(deptId);
                Employee employee = new Employee(null, lastName, email, gender, department);
                System.out.println(s + "--converter--" + employee);
                return employee;
            }
        }
        return null;
    }
}
