package com.example.demo.slider;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.slider.converter.SliderImageToSliderImageDto;
import com.example.demo.slider.converter.SliderToSliderDto;
import com.example.demo.slider.dto.SliderDto;
import com.example.demo.slider.dto.SliderImageDto;
import com.example.demo.slider.entity.Slider;
import com.example.demo.slider.entity.SliderImage;
import com.example.demo.system.MyResponse;

@RestController()
@RequestMapping("/sliders")
public class SliderController {

   private final SliderService sliderService;

   private final SliderToSliderDto sliderToSliderDto;

   private final SliderImageToSliderImageDto sliderImageToSliderImageDto;

   public SliderController(
         SliderImageToSliderImageDto sliderImageToSliderImageDto,
         SliderToSliderDto sliderToSliderDto,
         SliderService sliderService) {
      this.sliderService = sliderService;
      this.sliderToSliderDto = sliderToSliderDto;
      this.sliderImageToSliderImageDto = sliderImageToSliderImageDto;
   }

   @PostMapping("")
   public MyResponse createSlider(
         @RequestBody SliderDto sliderDto) {
      Slider slider = this.sliderService.create(sliderDto);
      SliderDto newSliderDto = this.sliderToSliderDto.convert(slider);

      return new MyResponse(true, "Add slider successful", 200, newSliderDto);
   }

   @PostMapping("/images")
   public MyResponse createSliderImages(
         @RequestBody List<SliderImageDto> sliderImagesDto) {
      List<SliderImage> sliderImages = this.sliderService.createSliderImages(sliderImagesDto);

      List<SliderImageDto> newSliderImages = sliderImages.stream()
            .map(sliderImage -> this.sliderImageToSliderImageDto.convert(sliderImage)).toList();

      return new MyResponse(true, "Add slider image successful", 200, newSliderImages);
   }

   @PutMapping("/images/{id}")
   public MyResponse updateSliderImage(
         @RequestBody SliderImageDto sliderImagesDto,
         @PathVariable Long id) {

      sliderService.updateSliderImage(sliderImagesDto, id);

      return new MyResponse(true, "Add slider image successful", 200);
   }

   @DeleteMapping("/images/{id}")
   public MyResponse deleteSliderImage(
         @PathVariable Long id) {
      this.sliderService.deleteSliderImage(id);

      return new MyResponse(false, "Delete slider image successful", 200);
   }

}
