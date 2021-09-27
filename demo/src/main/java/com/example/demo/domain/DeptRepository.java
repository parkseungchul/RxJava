package com.example.demo.domain;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface DeptRepository extends ReactiveCrudRepository<Dept, Integer> {

}
