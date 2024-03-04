package com.example.demo.dto;

public class Student {
   private String name;
   private Integer age;
   private String id;


   public Student() {
     age = 0;  
     name = new String("Nguyen Van A");
     id = new String("B0000000");
   }

   @Override   
   public String toString() {
      return "Student{age= " + this.age + '}';
   }
}
