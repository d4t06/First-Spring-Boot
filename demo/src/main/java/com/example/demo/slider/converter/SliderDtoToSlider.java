package com.example.demo.slider.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.slider.dto.SliderDto;
import com.example.demo.slider.entity.Slider;


@Component
public class SliderDtoToSlider implements Converter<SliderDto, Slider> {

   @Override
   public Slider convert(SliderDto source) {

      Slider slider = new Slider();

      slider.setName(source.name());

      return slider;
   }

}
