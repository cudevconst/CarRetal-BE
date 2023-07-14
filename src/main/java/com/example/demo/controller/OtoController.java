package com.example.demo.controller;

import com.example.demo.repository.OtoRepository;
import com.example.demo.service.*;
import com.example.demo.entity.Oto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping(path = "/api/oto")
public class OtoController {

    @Autowired
    private OtoRepository otoRepository;
    @Autowired
    private OtoService otoService;

    @Autowired
    private FileUploadImpl fileUpload;

//    @Autowired
//    private S3Service s3Service;
    @Autowired
    private ChiTietOtoService chiTietOtoService;
    @GetMapping("")
    private List<Oto> getAllOto(@RequestParam String trangthai){
        return  otoService.getAllOtoActive(trangthai);
    }
    @GetMapping("/{id}")
    private Oto getOtoById(@PathVariable("id") Long id){
        return otoService.findById(id);
    }

    @GetMapping("/chi-tiet/image/{id}")
    private ResponseEntity<?> getChiTietOtoIdOto(@PathVariable("id") Long id){
        return ResponseEntity.ok(chiTietOtoService.findByIdOto(id));

    }

    @Transactional
    @PostMapping("/them-moi")
    ResponseEntity<?> themmoiOto(@RequestParam(name = "tenXe") String tenXe,
                                 @RequestParam(name = "choNgoi") Integer choNgoi,
                                 @RequestParam(name = "thuongHieu") String thuongHieu,
                                 @RequestParam(name = "namSanXuat") Integer namSanXuat,
                                 @RequestParam(name = "giaThue") Long giaThue,
                                 @RequestParam(name = "file") List<MultipartFile> files
    ) throws IOException {
        Map<String, Object> map = new HashMap<>();
        Oto oto = new Oto();
        oto.setTenXe(tenXe);
        oto.setChoNgoi(choNgoi);
        oto.setThuongHieu(thuongHieu);
        oto.setNamSanXuat(namSanXuat);
        oto.setGiaThue(giaThue);
        oto.setTrangThai("on");
        Oto oto1 = otoRepository.save(oto);
        Long idOto = oto1.getId();

        //AWS S3
//        int count = 0;
//        for(MultipartFile file : files){
//            String nameImage = imageFileName(file);
//            if(count == 0){
//                otoService.saveMainImageOto(nameImage, idOto);
//                count = 1;
//            }
//
//            chiTietOtoService.saveImageChiTietOto(nameImage, idOto);
//            s3Service.saveFile(file, nameImage);
//        }

        //Heroku
        int count = 0;
        for(MultipartFile file : files){
            String urlImage = fileUpload.uploadFile(file);
            if(count == 0){
                otoService.saveMainImageOto(urlImage, idOto);
                count = 1;
            }
            chiTietOtoService.saveImageChiTietOto(urlImage, idOto);


        }
        map.put("oto", oto1);
        map.put("message", "Thêm mới thành công");
        return ResponseEntity.ok(map);
    }
    @PostMapping("/them")
    private  ResponseEntity<?> createOto(@RequestBody Oto oto){
        try{

            Oto oto1 = otoRepository.save(oto);
            Map<String, Object> json = new HashMap<>();
            json.put("id", oto1.getId());
            json.put("path","/upload/" + oto1.getId());
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonRes = objectMapper.writeValueAsString(json);
            return ResponseEntity.ok(json);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/cap-nhat/{id}")
    private ResponseEntity<?> updateOto(@RequestBody Oto otoNew, @PathVariable Long id){
        Oto oto = otoService.updateOto(otoNew, id);
        if(oto != null){
            Map<String, Object> json = new HashMap<>();
            json.put("id", id);
            json.put("path","/upload/" + id);
            return ResponseEntity.ok(json);
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/xoa/{id}")
    private ResponseEntity<?> deleteOto(@PathVariable Long id){
        Optional<Oto> oto = otoRepository.findById(id);
        Map<String, Object> map = new HashMap<>();
        if(oto.isPresent()){
            otoRepository.delete(oto.get());

            map.put("message", "Xoá thành công");
        }
        else{
            map.put("message", "Xoá thành công");
        }
        return ResponseEntity.ok(map);
    }

    @GetMapping("/test")
    private ResponseEntity<?> predictOto(@RequestParam("thuongHieu") String thuongHieu,
                                         @RequestParam("choNgoi") Integer choNgoi,
                                         @RequestParam("namSanXuat") Integer namSX){

        return ResponseEntity.ok(otoService.predictGiaOto(thuongHieu, choNgoi, namSX));
    }
    private String imageFileName(MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        String newFileName = UUID.randomUUID().toString() + "." + fileExtension;
        return newFileName;
    }

}
