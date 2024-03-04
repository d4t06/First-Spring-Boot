package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.StudentService;

@RestController()
@RequestMapping(path = "api/students")
public class StudentController {

   private final StudentService studentService;

   public StudentController(StudentService studentService) {
      this.studentService =  studentService;
   }

   @GetMapping()
   public String getAll() {
      return this.studentService.findAll();
      // return new String("This route find all students");
   }
}
