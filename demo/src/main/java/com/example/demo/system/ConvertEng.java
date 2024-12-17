package com.example.demo.system;

import org.springframework.stereotype.Component;

@Component
public class ConvertEng {

   public String convert(String src) {
      return src.toLowerCase()
            .replaceAll("\\à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ắ|ằ|ẳ|ẵ|ặ", "a")
            .replaceAll("è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ", "e")
            .replaceAll("ì|í|ị|ỉ|ĩ", "i")
            .replaceAll("ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ", "o")
            .replaceAll("ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ", "u")
            .replaceAll("ỳ|ý|ỵ|ỷ|ỹ", "y")
            .replaceAll("đ", "d")
            .replaceAll("[\\W_]", "_");
   }

}
