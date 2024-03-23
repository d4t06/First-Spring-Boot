package com.example.demo.slider.converter;

import org.springframework.core.convert.converter.Converter;

import com.example.demo.slider.dto.SliderImageDto;
import com.example.demo.slider.entity.SliderImage;

public class SliderImageToSliderImageDto implements Converter<SliderImage, SliderImageDto> {

   @Override
   public SliderImageDto convert(SliderImage source) {

      SliderImageDto sliderImageDto = new SliderImageDto(null, null, null);

      return sliderImageDto;
   }

}
