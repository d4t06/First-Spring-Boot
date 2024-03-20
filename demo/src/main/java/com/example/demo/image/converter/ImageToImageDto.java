package com.example.demo.image.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.example.demo.image.dto.ImageDto;
import com.example.demo.image.entity.Image;

@Component
public class ImageToImageDto implements Converter<Image, ImageDto> {

   @Override
   @Nullable
   public ImageDto convert(Image source) {
      ImageDto imageDto = new ImageDto(source.getId(), source.getImage_url(), source.getName(),
            source.getPublic_id(), source.getSize());
      return imageDto;
   }

}
