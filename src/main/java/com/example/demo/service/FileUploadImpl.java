package com.example.demo.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadImpl {
    String uploadFile(MultipartFile multipartFile) throws IOException;
}
