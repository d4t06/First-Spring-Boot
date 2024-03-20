package com.example.demo.image;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.image.entity.Image;

@Service
public class ImageService {

   private final ImageRepository imageRepository;

   private final CloudinaryService cloudinaryService;

   public ImageService(
         CloudinaryService cloudinaryService,
         ImageRepository imageRepository) {
      this.imageRepository = imageRepository;
      this.cloudinaryService = cloudinaryService;
   }

   public List<Image> findAll() {

      return this.imageRepository.findAll();
   }

   public Image save(MultipartFile multipartFile) throws IOException {
      String imageDataUri = this.cloudinaryService.convertFileToDateURI(multipartFile);
      Map<?, ?> result = this.cloudinaryService.upload(imageDataUri);

      Image image = new Image();

      image.setName(multipartFile.getOriginalFilename());
      image.setImage_url((String) result.get("url"));
      image.setPublic_id((String) result.get("public_id"));
      image.setSize(Math.round(multipartFile.getSize() / 1024));

      return this.imageRepository.save(image);
   }

   public void delete(String publicID) throws IOException {
      this.cloudinaryService.delete(publicID);
   }

}
