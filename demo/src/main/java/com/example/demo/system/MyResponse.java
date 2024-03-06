package com.example.demo.system;

import lombok.Data;

@Data
public class MyResponse {
    private boolean flag;
    private String message;
    private Integer code;
    private Object data;

    public MyResponse(boolean flag, String message, Integer code) {
        this.flag = flag;
        this.message = message;
        this.code = code;
    }

    public MyResponse(boolean flag, String message, Integer code, Object data) {
        this.flag = flag;
        this.message = message;
        this.code = code;
        this.data = data;
    }

}
