package com.example.demo.vo;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Emp {

    @Id
    private Integer empno;
    private String ename;
    private String job;
    private Integer mgr;
    private String hiredate;
    private Integer sal;
    private Integer comm;
    private Integer deptno;
}
