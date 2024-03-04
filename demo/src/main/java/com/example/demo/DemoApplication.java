package com.example.demo;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.dto.Student;

import org.springframework.web.bind.annotation.GetMapping;


@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("/")
	public List<Student> getMethodName() {
		Student st1 = new Student();

		System.out.println(st1);

		return List.of( new Student(),  new Student());
	}

	@GetMapping("/test")
	public String TestRoute() {
		return new String("this is a test route");
	}
	
}
