package com.xt.springmvc.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Date;

@Data
@NoArgsConstructor
public class Employee {

	private Integer id;

    /**
     * @NotEmpty 用在 集合类上面
     *
     * @NotBlank 用在 String上面
     *
     * @NotNull 用在 基本类型上
     */
	@NotBlank
    private String lastName;

	@Email
    private String email;

    //1 male, 0 female
    private Integer gender;

    private Department department;

    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;

    @NumberFormat(pattern = "#,###,###.##")
    private Float salary;

    public Employee(Integer id, @NotBlank String lastName, @Email String email, Integer gender, Department department) {
        this.id = id;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.department = department;
    }
}
