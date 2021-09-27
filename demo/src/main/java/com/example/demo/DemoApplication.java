package com.example.demo;

import com.example.demo.domain.Dept;

import com.example.demo.vo.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class DemoApplication implements ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    MongoOperations mongoOperations;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
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

        for(Dept dept: deptList){
            mongoOperations.save(dept);
        }

    }
}
