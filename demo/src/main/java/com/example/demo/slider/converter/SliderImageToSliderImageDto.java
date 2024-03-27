package com.example.demo.slider.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.image.converter.ImageToImageDto;
import com.example.demo.slider.dto.SliderImageDto;
import com.example.demo.slider.entity.SliderImage;

@Component
public class SliderImageToSliderImageDto implements Converter<SliderImage, SliderImageDto> {

   private final ImageToImageDto imageToImageDto;

   public SliderImageToSliderImageDto(
         ImageToImageDto imageToImageDto) {
      this.imageToImageDto = imageToImageDto;
   }

   @Override
   public SliderImageDto convert(SliderImage source) {

      SliderImageDto sliderImageDto = new SliderImageDto(source.getId(),
            source.getSlider_id(),
            source.getImage_id(),
            source.getLink_to(),
            source.getImage() != null
                  ? this.imageToImageDto.convert(source.getImage())
                  : null);

      return sliderImageDto;
   }

}
