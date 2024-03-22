package com.example.demo.image.dto;

import java.util.List;

import lombok.Data;

@Data
public class ImageResponse {
   List<ImageDto> images;
   Integer page;
   Integer pageSize;
   Integer count;
   Boolean isLast;
}
