
package com.example.demo.color.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.color.dto.ColorDto;
import com.example.demo.color.entity.Color;


@Component
public class ColorDtoToColor implements Converter<ColorDto,Color> {

    @Override
    public Color convert(ColorDto source) {
        Color color = new Color();

        color.setColor_name(source.color_name());
        color.setColor_name_ascii(source.color_name_ascii());
        color.setProductId(source.product_id());
        
        return color;
    }
    
}
