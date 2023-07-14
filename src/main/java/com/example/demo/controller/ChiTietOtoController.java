package com.example.demo.controller;

import com.example.demo.entity.ChiTietOto;
import com.example.demo.repository.ChiTietOtoRepository;
import com.example.demo.service.ChiTietOtoService;
import com.example.demo.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping(path = "/api/chi-tiet")
public class ChiTietOtoController {
    @Autowired
    private ChiTietOtoService chiTietOtoService;

    @Autowired
    private S3Service s3Service;
    @Autowired
    private ChiTietOtoRepository chiTietOtoRepository;

    @GetMapping("/{id}")
    private List<ChiTietOto> getChiTietOto(@PathVariable Long id){
        List<ChiTietOto> list = chiTietOtoRepository.findByIdOto(id);
        return list;

    }
    @PostMapping("/cap-nhat/{id}")
    public ResponseEntity<?> update(@RequestParam("file") MultipartFile file, @PathVariable Long id){
        Map<String, Object> map = new HashMap<>();
        try{
            String imageName = imageFileName(file);
            ChiTietOto chiTietOto = chiTietOtoRepository.findById(id).get();
            s3Service.deleteFile(chiTietOto.getUrlImage());
            System.out.println(chiTietOto.getUrlImage());
            chiTietOtoService.upDateImageChiTietOto(imageName, id);
            s3Service.saveFile(file, imageName);
            map.put("message", "Cập nhật ảnh thành công");

        }
        catch (Exception e){
            e.printStackTrace();
            map.put("message", "Cập nhật ảnh thất bại");
        }
        return ResponseEntity.ok(map);
    }
    private String imageFileName(MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        String newFileName = UUID.randomUUID().toString() + "." + fileExtension;
        return newFileName;
    }
}
