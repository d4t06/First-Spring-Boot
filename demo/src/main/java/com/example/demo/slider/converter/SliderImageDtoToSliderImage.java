package com.example.demo.slider.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.slider.dto.SliderImageDto;
import com.example.demo.slider.entity.SliderImage;


@Component
public class SliderImageDtoToSliderImage implements Converter<SliderImageDto, SliderImage> {

   @Override
   public SliderImage convert(SliderImageDto source) {

      SliderImage sliderImage = new SliderImage();

      sliderImage.setImage_id(source.image_id());
      sliderImage.setSlider_id(source.slider_id());

      return sliderImage;
   }

}
