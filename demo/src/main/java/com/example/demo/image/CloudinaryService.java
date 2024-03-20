package com.example.demo.image;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import io.github.cdimascio.dotenv.Dotenv;

@Service
public class CloudinaryService {

   Cloudinary cloudinary;

   Dotenv dotenv = Dotenv.configure().filename(".env.local").load();

   Map<?, ?> params = ObjectUtils.asMap(
         "cloud_name", dotenv.get("CLOUD_NAME"),
         "api_key", dotenv.get("CLOUD_API_KEY"),
         "api_secret", dotenv.get("CLOUD_API_SECRET"));

   Map<?, ?> uploadParams = ObjectUtils.asMap(
         "folder", "spring-boot",
         "resource_type", "auto");

   public CloudinaryService() {
      this.cloudinary = new Cloudinary(params);
   }

   public Map<?, ?> upload(String dataUri) throws IOException {
      return this.cloudinary.uploader().upload(dataUri, uploadParams);

   }

   public void delete(String publicID) throws IOException {
      this.cloudinary.uploader().destroy(publicID, ObjectUtils.emptyMap());
   }

   public String convertFileToDateURI(MultipartFile file) throws IOException {
      StringBuilder sb = new StringBuilder();
      sb.append("data:image/png;base64,");
      sb.append(new String(Base64.getEncoder().encode(file.getBytes())));
      return sb.toString();
   }
}
