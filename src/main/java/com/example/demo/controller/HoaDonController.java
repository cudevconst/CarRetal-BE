package com.example.demo.controller;

import com.example.demo.entity.HoaDon;
import com.example.demo.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/hoa-don")
public class HoaDonController {
    @Autowired
    private HoaDonService hoaDonService;

    @GetMapping("/{id}")
    private ResponseEntity<?> getHoaDonById(@PathVariable Long id){
        HoaDon hoaDon = hoaDonService.getHoaDonById(id);
        if(hoaDon != null){
            return ResponseEntity.ok(hoaDon);
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping()
    private ResponseEntity<?> getHoaDonKhachHangByTrangThai(@RequestParam(name = "khachhang") Long id, @RequestParam(name = "trangthai") String trangThai){
        List<HoaDon> list = hoaDonService.getHoaDonKhachHangTrangThai(id, trangThai);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/cap-nhat/{id}")
    private ResponseEntity<?> updateHoaDon(@PathVariable Long id){
        HoaDon hoaDon = hoaDonService.getHoaDonById(id);
        Map<String, Object> map = new HashMap<>();
        if(hoaDon != null){
            HoaDon hoaDon1 = hoaDonService.updateHoaDon(id);
            map.put("message", "Cập nhật thành công");
            map.put("hoadon", hoaDon1);
        }
        else{
            map.put("message", "Cập nhật thất bại");
        }
        return ResponseEntity.ok(map);
    }
}
