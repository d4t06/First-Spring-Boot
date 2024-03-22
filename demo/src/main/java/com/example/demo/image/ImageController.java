package com.example.demo.image;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.image.dto.ImageResponse;
import com.example.demo.image.entity.Image;
import com.example.demo.system.MyResponse;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/images")
public class ImageController {

   private final ImageService imageService;

   public ImageController(
         ImageService imageService) {
      this.imageService = imageService;
   }

   @GetMapping("")
   public MyResponse getAllImage(
         @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
      ImageResponse imageResponse = this.imageService.findAll(page);

      return new MyResponse(true, "Get all images successful", 200, imageResponse);

   }

   @PostMapping("")
   @PreAuthorize("hasAuthority('ROLE_ADMIN')")
   public MyResponse save(@RequestPart("image") MultipartFile multipartFile) throws IOException {

      Image image = this.imageService.save(multipartFile);

      return new MyResponse(true, "upload image successful", HttpStatus.OK.value(), image);

   }

   @DeleteMapping("")
   @PreAuthorize("hasAuthority('ROLE_ADMIN')")
   public MyResponse delete(@RequestParam String publicID) throws IOException {

      this.imageService.delete(publicID);
      return new MyResponse(true, "Delete image successful", HttpStatus.OK.value());
   }

}
