package com.example.demo;

import com.example.demo.domain.Dept;
import com.example.demo.domain.DeptRepository;
import com.example.demo.vo.Emp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@DataMongoTest
public class TestDeptRepository {

/**

 new Dept(10, "ACCOUNTING", "NEW YORK");
 new Dept(20, "RESEARCH",   "DALLAS");
 new Dept(30, "SALES",      "CHICAGO");
 new Dept(40, "OPERATIONS", "BOSTON");
 
    INSERT INTO EMP VALUES (7839,"KING","PRESIDENT",null,"81-11-17",5000,null,10);
    INSERT INTO EMP VALUES (7934,"MILLER","CLERK",7782,"82-01-11",1300,null,10);
    INSERT INTO EMP VALUES (7782,"CLARK","MANAGER",7839,"81-05-09",2450,null,10);


    INSERT INTO EMP VALUES (7566,"JONES","MANAGER",7839,"81-04-01",2975,null,20);
    INSERT INTO EMP VALUES (7902,"FORD","ANALYST",7566,"81-12-11",3000,null,20);
    INSERT INTO EMP VALUES (7369,"SMITH","CLERK",7902,"80-12-09",800,null,20);
    INSERT INTO EMP VALUES (7788,"SCOTT","ANALYST",7566,"82-12-22",3000,null,20);
    INSERT INTO EMP VALUES (7876,"ADAMS","CLERK",7788,"83-01-15",1100,null,20);

    INSERT INTO EMP VALUES (7654,"MARTIN","SALESMAN",7698,"81-09-10",1250,1400,30);
    INSERT INTO EMP VALUES (7499,"ALLEN","SALESMAN",7698,"81-02-11",1600,300,30);
    INSERT INTO EMP VALUES (7844,"TURNER","SALESMAN",7698,"81-08-21",1500,0,30);
    INSERT INTO EMP VALUES (7900,"JAMES","CLERK",7698,"81-12-11",950,null,30);
    INSERT INTO EMP VALUES (7698,"BLAKE","MANAGER",7839,"81-05-01",2850,null,30);
    INSERT INTO EMP VALUES (7521,"WARD","SALESMAN",7698,"81-02-23",1250,500,30);
**/
    @Autowired
    DeptRepository deptRepository;

    @BeforeEach
    public void dataSet(){
        List<Dept> deptList = new ArrayList<>();
        Dept dept1 = new Dept(10, "ACCOUNTING", "NEW YORK",
        Arrays.asList(
            new Emp(7839,"KING","PRESIDENT",null,"81-11-17",5000,null, 10),
            new Emp(7934,"MILLER","CLERK",7782,"82-01-11",1300,null,10),
            new Emp(7782,"CLARK","MANAGER",7839,"81-05-09",2450,null,10)
        ));


        Dept dept2 = new Dept(20, "RESEARCH",   "DALLAS",
        Arrays.asList(
            new Emp(7566,"JONES","MANAGER",7839,"81-04-01",2975,null,20),
            new Emp(7902,"FORD","ANALYST",7566,"81-12-11",3000,null,20),
            new Emp(7369,"SMITH","CLERK",7902,"80-12-09",800,null,20),
            new Emp(7788,"SCOTT","ANALYST",7566,"82-12-22",3000,null,20),
            new Emp(7876,"ADAMS","CLERK",7788,"83-01-15",1100,null,20)
        ));


        Dept dept3 = new Dept(30, "SALES", "CHICAGO",
        Arrays.asList(
                new Emp(7654,"MARTIN","SALESMAN",7698,"81-09-10",1250,1400,30),
                new Emp(7499,"ALLEN","SALESMAN",7698,"81-02-11",1600,300,30),
                new Emp(7844,"TURNER","SALESMAN",7698,"81-08-21",1500,0,30),
                new Emp(7900,"JAMES","CLERK",7698,"81-12-11",950,null,30),
                new Emp(7698,"BLAKE","MANAGER",7839,"81-05-01",2850,null,30),
                new Emp(7521,"WARD","SALESMAN",7698,"81-02-23",1250,500,30)
        ));
        Dept dept4 =  new Dept(40, "OPERATIONS", "BOSTON", null);

        deptList.add(dept1);
        deptList.add(dept2);
        deptList.add(dept3);
        deptList.add(dept4);
        StepVerifier.create(
                deptRepository.saveAll(deptList)
        ).expectNextCount(deptList.size()).verifyComplete();











    }

    @Test
    public void findAll(){
        StepVerifier.create(
                deptRepository.findAll().flatMap(dept1 -> {
                    return Flux.just(dept1);
                })
        ).expectNextMatches(dept1 -> {
            System.out.println(dept1.getDeptno());
            System.out.println(dept1.getDname());
            System.out.println(dept1.getLoc());
            System.out.println(dept1.getEmpList());
            return true;
        }).expectNextMatches(dept1 -> {
            System.out.println(dept1.getDeptno());
            System.out.println(dept1.getDname());
            System.out.println(dept1.getLoc());
            System.out.println(dept1.getEmpList());
            return true;
        }).expectNextMatches(dept1 -> {
            System.out.println(dept1.getDeptno());
            System.out.println(dept1.getDname());
            System.out.println(dept1.getLoc());
            System.out.println(dept1.getEmpList());
            return true;
        }).expectNextMatches(dept1 -> {
            System.out.println(dept1.getDeptno());
            System.out.println(dept1.getDname());
            System.out.println(dept1.getLoc());
            System.out.println(dept1.getEmpList());
            return true;
        }).verifyComplete();
    }

    @Test
    public void findAll2(){
        StepVerifier.create(
                deptRepository.findAll()
        ).expectNextMatches(dept1 -> {
            System.out.println(dept1.getDeptno());
            System.out.println(dept1.getDname());
            System.out.println(dept1.getLoc());
            System.out.println(dept1.getEmpList());
            return true;
        }).expectNextCount(3L).verifyComplete();
    }


    @Test
    public void findId(){
        StepVerifier.create(
                deptRepository.findById(10)
        ).expectNextMatches(dept1 -> {
            System.out.println(dept1.getDeptno());
            System.out.println(dept1.getDname());
            System.out.println(dept1.getLoc());
            System.out.println(dept1.getEmpList());
            return true;
        }).verifyComplete();
    }



    @Test
    public void search(){

        deptRepository.findAll().flatMap(dept -> {
           if(dept.getEmpList() != null){
               dept.setEmpList(dept.getEmpList().stream().filter(emp -> emp.getSal() > 2500).collect(Collectors.toList()));
               return Flux.just(dept);
           }else{
               return Flux.empty();
           }
        }).subscribe(dept ->{
            System.out.println("====================>"+dept.getEmpList());
        });

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
