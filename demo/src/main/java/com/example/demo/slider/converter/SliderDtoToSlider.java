package com.example.demo.slider.converter;

import org.springframework.core.convert.converter.Converter;

import com.example.demo.slider.dto.SliderDto;
import com.example.demo.slider.entity.Slider;

public class SliderDtoToSlider implements Converter<SliderDto, Slider> {

   @Override
   public Slider convert(SliderDto source) {

      Slider slider = new Slider();

      return slider;
   }

}
