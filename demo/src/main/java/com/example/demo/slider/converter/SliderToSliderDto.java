package com.example.demo.slider.converter;

import org.springframework.core.convert.converter.Converter;

import com.example.demo.slider.dto.SliderDto;
import com.example.demo.slider.entity.Slider;

public class SliderToSliderDto implements Converter<Slider, SliderDto> {

   @Override
   public SliderDto convert(Slider source) {
      SliderDto sliderDto = new SliderDto(null, null);

      return sliderDto;
   }

}
