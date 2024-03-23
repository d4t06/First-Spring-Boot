package com.example.demo.slider.converter;

import org.springframework.core.convert.converter.Converter;

import com.example.demo.slider.dto.SliderImageDto;
import com.example.demo.slider.entity.SliderImage;

public class SliderImageDtoToSliderImage implements Converter<SliderImageDto, SliderImage> {

   @Override
   public SliderImage convert(SliderImageDto source) {

      SliderImage sliderImage = new SliderImage();

      return sliderImage;
   }

}
