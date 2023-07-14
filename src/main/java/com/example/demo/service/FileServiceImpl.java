package com.example.demo.service;


import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileServiceImpl {


    String saveFile(MultipartFile file, String nameImage);

    byte[] downloadFile(String filename);


    String deleteFile(String filename);


    List<String> listAllFiles();
}