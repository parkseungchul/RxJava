package com.example.demo.controller;


import com.example.demo.domain.DeptRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
public class DeptController {

    @Autowired
    DeptRepository deptRepository;

    @GetMapping
    public Mono<Rendering> index(){
        log.debug("index");
        return Mono.just(Rendering.view("index.html")
                .modelAttribute("depts",  this.deptRepository.findAll())
                .build());
    }

}
