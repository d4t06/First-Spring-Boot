package com.example.demo.image;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.image.converter.ImageToImageDto;
import com.example.demo.image.dto.ImageDto;
import com.example.demo.image.entity.Image;
import com.example.demo.system.MyResponse;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/images")
public class ImageController {

   private final ImageService imageService;
   private final ImageToImageDto imageToImageDto;

   public ImageController(
         ImageToImageDto imageToImageDto,
         ImageService imageService) {
      this.imageService = imageService;
      this.imageToImageDto = imageToImageDto;
   }

   @GetMapping("")
   public MyResponse getAllImage() {
      List<Image> images = this.imageService.findAll();

      List<ImageDto> imagesDto = new ArrayList<>();

      for (Image image : images) {
         ImageDto imageDto = this.imageToImageDto.convert(image);
         imagesDto.add(imageDto);
      }

      return new MyResponse(true, "Get all images successful", 200, imagesDto);

   }

   @PostMapping("")
   @PreAuthorize("hasAuthority('ROLE_ADMIN')")
   public MyResponse save(@RequestParam MultipartFile multipartFile) throws IOException {
      BufferedImage bi = ImageIO.read(multipartFile.getInputStream());

      Image image = this.imageService.save(multipartFile);

      return new MyResponse(true, "upload image successful", HttpStatus.OK.value(), image);

   }

   @DeleteMapping("/{id}")
   @PreAuthorize("hasAuthority('ROLE_ADMIN')")
   public MyResponse delete(@PathVariable String id) throws IOException {
      this.imageService.delete(id);
      return new MyResponse(true, "Delete image successful", HttpStatus.OK.value());
   }

}
