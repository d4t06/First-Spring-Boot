
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

        color.setColor(source.color());
        color.setColor_ascii(source.color_ascii());
        color.setProductAscii(source.product_ascii());
        
        return color;
    }
    
}
