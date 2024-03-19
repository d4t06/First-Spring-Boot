package com.example.demo.image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryService {

   Cloudinary cloudinary;

   Map params = ObjectUtils.asMap(
         "cloud_name", "dghu2ne82",
         "api_key", "945594823734944",
         "api_key", "945594823734944",
         "api_secret", "K0_8M1Q3k7Ai1FIPrrYbTgvf-9w");

   Map uploadParams = ObjectUtils.asMap(
      "folder", "spring-boot",
      "resource_type", "auto"
   );

   public CloudinaryService() {
      this.cloudinary = new Cloudinary(params);
   }

   public Map upload(MultipartFile multipartFile) throws IOException {
      File file = convert(multipartFile);
      return this.cloudinary.uploader().upload(file, uploadParams);

   }

   public void delete(String publicID) throws IOException {
      this.cloudinary.uploader().destroy(publicID, ObjectUtils.emptyMap());
   }

   private File convert(MultipartFile multipartFile) throws IOException {
      File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
      FileOutputStream fo = new FileOutputStream(file);
      fo.write(multipartFile.getBytes());
      fo.close();
      return file;
   }

}
