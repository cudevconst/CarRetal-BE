package com.example.demo.controller;

import com.example.demo.repository.ChiTietOtoRepository;
import com.example.demo.service.ChiTietOtoService;
import com.example.demo.service.OtoService;
import com.example.demo.service.S3Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.net.HttpURLConnection.HTTP_OK;

@RestController
public class S3Controller {


    @Autowired
    private S3Service s3Service;;

    @Autowired
    private ChiTietOtoService chiTietOtoService;
    @Autowired
    private ChiTietOtoRepository chiTietOtoRepository;

    @Autowired
    private OtoService otoService;
    @PostMapping("upload/{id}")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, @PathVariable Long id){

        Map<String, Object> map = new HashMap<>();
//        String nameimageMain = imageFileName(mainImage);
//        otoService.saveMainImageOto(nameimageMain, id);
//        s3Service.saveFile(mainImage, nameimageMain);
//        int count = 0;
//        for(MultipartFile file : files){
            String nameImage = imageFileName(file);
//            if(count == 0){
//                otoService.saveMainImageOto(nameImage, id);
//                count = 1;
//            }

            chiTietOtoService.saveImageChiTietOto(nameImage, id);
            s3Service.saveFile(file, nameImage);
//        }
        map.put("message", "Thành công");
        return ResponseEntity.ok(map);
    }

    @GetMapping("download/{filename}")
    public ResponseEntity<byte[]> download(@PathVariable("filename") String filename){
        HttpHeaders headers=new HttpHeaders();
        headers.add("Content-type", MediaType.ALL_VALUE);
        headers.add("Content-Disposition", "attachment; filename="+filename);
        byte[] bytes = s3Service.downloadFile(filename);
        return  ResponseEntity.status(HTTP_OK).headers(headers).body(bytes);
    }


    @DeleteMapping("{filename}")
    public  String deleteFile(@PathVariable("filename") String filename){
        return s3Service.deleteFile(filename);
    }

    @GetMapping("list")
    public List<String> getAllFiles(){

        return s3Service.listAllFiles();

    }
    private String imageFileName(MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        String newFileName = UUID.randomUUID().toString() + "." + fileExtension;
        return newFileName;
    }
}