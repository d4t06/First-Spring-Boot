package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.dto.Student;


@Service
public class StudentService {
   public String findAll() {
      return new String("This route find all student");
      // return List.of(new Student());
   }
}
