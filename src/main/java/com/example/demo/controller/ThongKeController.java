package com.example.demo.controller;


import com.example.demo.service.ThongKeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/thong-ke")
public class ThongKeController {
    @Autowired
    private ThongKeService thongKeService;
    @GetMapping("/oto")
    private ResponseEntity<?> countOtoByTrangThai(@RequestParam(name = "trangthai") String trangThai){
        int soLuong = thongKeService.getCountByTrangThai(trangThai);
        Map<String, Object> map = new HashMap<>();
        map.put("soluong", soLuong);
        return ResponseEntity.ok(map);
    }
    @GetMapping("/oto/lan-dat")
    private ResponseEntity<?> countLanDatOto(@RequestParam(name = "start") String start, @RequestParam(name = "end") String end){
        List<Map<String, Object>> list = new ArrayList<>();
        if(start.isEmpty() && end.isEmpty()){
            list =  thongKeService.countSoLanDatXe();
        }
        else{
            list = thongKeService.countSoLanThoiGian(start, end);
        }

        return ResponseEntity.ok(list);
    }

    @GetMapping("/hoa-don/chi-tiet")
    private ResponseEntity<?> sumDoanhThuTheoThoiGian(@RequestParam(name = "start") String start, @RequestParam(name = "end") String end){
        Map<String, Object> map = new HashMap<>();
        map.put("doanhthu", thongKeService.sumHoaDonTheoThoiGian(start, end));
        return ResponseEntity.ok(map);
    }

    @GetMapping("/dashboard/hoa-don")
    private ResponseEntity<?> sumDoanhThuTheoThang(){
        Map<String, Object> map = new HashMap<>();
        map.put("doanhthu", thongKeService.sumHoaDonTheoThangThoiGianThuc());
        return ResponseEntity.ok(map);
    }

    @GetMapping("/doanh-thu/thuong-hieu")
    private ResponseEntity<?> doanhThuTheoThuongHieu(){
        return ResponseEntity.ok(thongKeService.doanhThuTheoThuongHIeu());
    }

    @GetMapping("/doanh-thu/thang")
    private ResponseEntity<?> doanhThuTheoTungThang(){
        return ResponseEntity.ok(thongKeService.doanhThuTheoTungThang());
    }

}
