package com.example.demo.image;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.image.converter.ImageToImageDto;
import com.example.demo.image.dto.ImageDto;
import com.example.demo.image.dto.ImageResponse;
import com.example.demo.image.entity.Image;
import com.example.demo.system.exception.ObjectNotFoundException;

@Service
public class ImageService {

   private final ImageRepository imageRepository;

   private final CloudinaryService cloudinaryService;

   private final ImageToImageDto imageToImageDto;

   public ImageService(
         ImageToImageDto imageToImageDto,
         CloudinaryService cloudinaryService,
         ImageRepository imageRepository) {
      this.imageRepository = imageRepository;
      this.cloudinaryService = cloudinaryService;
      this.imageToImageDto = imageToImageDto;
   }

   public ImageResponse findAll(int page) {
      Pageable pageable = PageRequest.of(page, 12);
      Page<Image> imagePage = this.imageRepository.findAll(pageable);

      List<Image> images = imagePage.getContent();

      List<ImageDto> imagesDto = new ArrayList<>();
      for (Image image : images) {
         ImageDto imageDto = this.imageToImageDto.convert(image);
         imagesDto.add(imageDto);
      }

      ImageResponse res = new ImageResponse();

      res.setImages(imagesDto);
      res.setPage(page);
      res.setPageSize(12);
      res.setCount((int) imagePage.getTotalElements());
      res.setIsLast(imagePage.isLast());

      return res;
   }

   public Image save(MultipartFile multipartFile) throws IOException {
      String imageDataUri = this.cloudinaryService.convertFileToDateURI(multipartFile);
      Map<?, ?> result = this.cloudinaryService.upload(imageDataUri);

      Image image = new Image();

      image.setName(multipartFile.getOriginalFilename());
      image.setImage_url((String) result.get("url"));
      image.setPublicID((String) result.get("public_id"));
      image.setSize(Math.round(multipartFile.getSize() / 1024));

      return this.imageRepository.save(image);
   }

   public void delete(String publicID) throws IOException {
      Image image = this.imageRepository.findByPublicID(publicID).get(0);

      if (image == null)
         throw new ObjectNotFoundException("image not found");

      this.cloudinaryService.delete(publicID);

      this.imageRepository.delete(image);
   }

}
