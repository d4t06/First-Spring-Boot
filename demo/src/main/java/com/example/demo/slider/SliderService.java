package com.example.demo.slider;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.image.ImageRepository;
import com.example.demo.image.entity.Image;
import com.example.demo.slider.converter.SliderDtoToSlider;
import com.example.demo.slider.converter.SliderImageDtoToSliderImage;
import com.example.demo.slider.dto.SliderDto;
import com.example.demo.slider.dto.SliderImageDto;
import com.example.demo.slider.entity.Slider;
import com.example.demo.slider.entity.SliderImage;
import com.example.demo.slider.repository.SliderImageRepository;
import com.example.demo.slider.repository.SliderRepository;
import com.example.demo.system.exception.ObjectNotFoundException;

@Service
public class SliderService {

   private final SliderRepository sliderRepository;

   private final SliderImageRepository sliderImageRepository;

   private final ImageRepository imageRepository;

   private final SliderDtoToSlider sliderDtoToSlider;

   private final SliderImageDtoToSliderImage sliderImageDtoToSliderImage;

   public SliderService(
         SliderRepository sliderRepository,
         SliderImageRepository sliderImageRepository,
         SliderDtoToSlider sliderDtoToSlider,
         SliderImageDtoToSliderImage sliderImageDtoToSliderImage,
         ImageRepository imageRepository

   ) {
      this.sliderImageRepository = sliderImageRepository;
      this.sliderRepository = sliderRepository;
      this.sliderDtoToSlider = sliderDtoToSlider;
      this.sliderImageDtoToSliderImage = sliderImageDtoToSliderImage;
      this.imageRepository = imageRepository;
   }

   public Slider create(SliderDto sliderDto) {
      Slider slider = this.sliderDtoToSlider.convert(sliderDto);
      return this.sliderRepository.save(slider);
   }

   public SliderImage createSliderImage(SliderImageDto sliderImageDto) {
      SliderImage sliderImage = this.sliderImageDtoToSliderImage.convert(sliderImageDto);
      return this.sliderImageRepository.save(sliderImage);

   }

   public List<SliderImage> createSliderImages(List<SliderImageDto> sliderImagesDto) {
      List<SliderImage> sliderImages = sliderImagesDto.stream().map(dto -> {
         Image image = this.imageRepository.findById(dto.image_id()).orElseThrow(() -> new ObjectNotFoundException(""));

         SliderImage sliderImage = this.createSliderImage(dto);
         sliderImage.setImage(image);
         return sliderImage;
      }).toList();
      return sliderImages;
   }

   public void deleteSliderImage(Long id) {
      this.sliderImageRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("slider image not found"));

      this.sliderImageRepository.deleteById(id);
   }

   // public void deleteSliderImages(List<Long> ids) {
   // for (Long id : ids) {
   // this.deleteSliderImage(id);
   // }
   // }
}
