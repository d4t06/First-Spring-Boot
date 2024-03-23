package com.example.demo.slider;

import org.springframework.web.bind.annotation.RestController;

@RestController("/sliders")
public class SliderController {

   private final SliderService sliderService;

   public SliderController(
         SliderService sliderService) {
      this.sliderService = sliderService;
   }



}
