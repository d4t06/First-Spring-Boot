package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/items")
public class ItemController {

   @GetMapping("")
   public String getAll() {

      return new String("This route get all items");
   }
}
