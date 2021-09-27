package com.example.demo.domain;

import com.example.demo.vo.Emp;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Dept {

    @Id
    private Integer deptno;
    private String dname;
    private String loc;
    private List<Emp> empList;

}
