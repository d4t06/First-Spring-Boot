package com.example.demo.color;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.color.converter.ColorDtoToColor;
import com.example.demo.color.converter.ColorToColorDto;
import com.example.demo.color.dto.ColorDto;
import com.example.demo.color.dto.ColorResponseDto;
import com.example.demo.color.entity.Color;
import com.example.demo.combine.CombineRepository;
import com.example.demo.combine.converter.CombineToCombineDto;
import com.example.demo.combine.dto.CombineDto;
import com.example.demo.combine.entity.Combine;
import com.example.demo.product_slider.ProductSliderRepository;
import com.example.demo.product_slider.entity.ProductSlider;
import com.example.demo.slider.entity.Slider;
import com.example.demo.slider.repository.SliderRepository;
import com.example.demo.storage.StorageRepository;
import com.example.demo.storage.entity.Storage;
import com.example.demo.system.MyResponse;
import com.example.demo.system.exception.ObjectNotFoundException;

@Service
public class ColorService {
    private final ColorToColorDto colorToColorDto;
    private final ColorDtoToColor colorDtoToColor;
    private final ColorRepository colorRepository;
    private final SliderRepository sliderRepository;
    private final ProductSliderRepository productSliderRepository;
    private final StorageRepository storageRepository;
    private final CombineRepository combineRepository;
    private final CombineToCombineDto combineToCombineDto;

    public ColorService(ColorToColorDto colorToColorDto,
            ColorDtoToColor colorDtoToColor,
            ProductSliderRepository productSliderRepository,
            SliderRepository sliderRepository,
            CombineRepository combineRepository,
            StorageRepository storageRepository,
            CombineToCombineDto combineToCombineDto,
            ColorRepository colorRepository) {
        this.colorDtoToColor = colorDtoToColor;
        this.colorToColorDto = colorToColorDto;
        this.colorRepository = colorRepository;
        this.productSliderRepository = productSliderRepository;
        this.sliderRepository = sliderRepository;
        this.storageRepository = storageRepository;
        this.combineRepository = combineRepository;
        this.combineToCombineDto = combineToCombineDto;

    }

    public MyResponse add(ColorDto colorDto) {
        Color color = this.colorDtoToColor.convert(colorDto);
        Color newColor = this.colorRepository.save(color);

        // create slider
        Slider slider = new Slider();
        slider.setName("slider for " + colorDto.color_ascii());
        Slider newSlider = this.sliderRepository.save(slider);

        // create product slider
        ProductSlider productSlider = new ProductSlider();
        productSlider.setColor_id(newColor.getId());
        productSlider.setSlider_id(newSlider.getId());
        ProductSlider newProductSlider = this.productSliderRepository.save(productSlider);

        // set product slider to new category
        newProductSlider.setSlider(newSlider);
        newColor.setProductSlider(newProductSlider);

        List<Storage> productStorages = this.storageRepository.findByProductAscii(colorDto.product_ascii());

        ColorDto newColorDto = this.colorToColorDto.convert(newColor);

        if (!productStorages.isEmpty()) {
            List<CombineDto> newCombinesDto = productStorages.stream().map(storage -> {
                Combine combine = new Combine();

                combine.setColor_id(newColor.getId());
                combine.setStorage_id(storage.getId());
                combine.setPrice(0);
                combine.setQuantity(0);
                combine.setProductAscii(colorDto.product_ascii());

                Combine newCombine = this.combineRepository.save(combine);
                return this.combineToCombineDto.convert(newCombine);
            }).toList();

            ColorResponseDto data = new ColorResponseDto(newColorDto, newCombinesDto);

            return new MyResponse(true, "add color successful", 200, data);

        }

        ColorResponseDto data = new ColorResponseDto(newColorDto, new ArrayList<CombineDto>());

        return new MyResponse(true, "add color successful", 200, data);

    }

    public void update(Long id, ColorDto colorDto) {
        this.colorRepository.findById(id).map(oldColor -> {
            oldColor.setColor(colorDto.color());
            oldColor.setColor_ascii(colorDto.color_ascii());

            Color newColor = this.colorRepository.save(oldColor);

            return newColor;
        }).orElseThrow(() -> new ObjectNotFoundException(""));
    }

    public void delete(Long id) {
        this.colorRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Brand not found"));

        this.colorRepository.deleteById(id);
    }
}
