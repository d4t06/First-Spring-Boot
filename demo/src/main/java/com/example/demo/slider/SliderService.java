package com.example.demo.slider;

import org.springframework.stereotype.Service;

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

   private final SliderDtoToSlider sliderDtoToSlider;

   private final SliderImageDtoToSliderImage sliderImageDtoToSliderImage;

   public SliderService(
         SliderRepository sliderRepository,
         SliderImageRepository sliderImageRepository,
         SliderDtoToSlider sliderDtoToSlider,
         SliderImageDtoToSliderImage sliderImageDtoToSliderImage

   ) {
      this.sliderImageRepository = sliderImageRepository;
      this.sliderRepository = sliderRepository;
      this.sliderDtoToSlider = sliderDtoToSlider;
      this.sliderImageDtoToSliderImage = sliderImageDtoToSliderImage;
   }

   public Slider create(SliderDto sliderDto) {
      Slider slider = this.sliderDtoToSlider.convert(sliderDto);
      return this.sliderRepository.save(slider);
   }

   public SliderImage createSliderImage(SliderImageDto sliderImageDto) {
      SliderImage sliderImage = this.sliderImageDtoToSliderImage.convert(sliderImageDto);
      return this.sliderImageRepository.save(sliderImage);

   }

   public void deleteSliderImage(long id) {
      this.sliderImageRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("slider image not found"));

      this.sliderImageRepository.deleteById(id);
   }
}
