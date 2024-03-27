package com.example.demo.slider.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.slider.dto.SliderDto;
import com.example.demo.slider.dto.SliderImageDto;
import com.example.demo.slider.entity.Slider;
import com.example.demo.slider.entity.SliderImage;

@Component
public class SliderToSliderDto implements Converter<Slider, SliderDto> {

   private final SliderImageToSliderImageDto sliderImageToSliderImageDto;

   public SliderToSliderDto(SliderImageToSliderImageDto sliderImageToSliderImageDto) {
      this.sliderImageToSliderImageDto = sliderImageToSliderImageDto;
   }

   List<SliderImageDto> getImagesDto(List<SliderImage> sliderImages) {
      ArrayList<SliderImageDto> sliderImagesDto = new ArrayList<>();
      for (SliderImage sliderImage : sliderImages) {
         SliderImageDto sliderImageDto = this.sliderImageToSliderImageDto.convert(sliderImage);
         sliderImagesDto.add(sliderImageDto);
      }

      return sliderImagesDto;
   }

   @Override
   public SliderDto convert(Slider source) {
      SliderDto sliderDto = new SliderDto(
            source.getId(),
            source.getName(),
            source.getSliderImages().isEmpty()
                  ? new ArrayList<>()
                  : this.getImagesDto(source.getSliderImages()));

      return sliderDto;
   }

}
