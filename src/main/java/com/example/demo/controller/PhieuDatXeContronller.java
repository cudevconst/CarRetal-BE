package com.example.demo.controller;

import com.example.demo.dto.response.PhieuDatXeResponse;
import com.example.demo.entity.PhieuDatXe;
import com.example.demo.service.HoaDonService;
import com.example.demo.service.PhieuDatXeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/phieu-dat-xe")
public class PhieuDatXeContronller {
    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private PhieuDatXeService phieuDatXeService;
    @GetMapping("/{id}")
    private ResponseEntity<?> getPhieuDatXeId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(phieuDatXeService.findById(id));
    }

    @PostMapping(path = "/them")
    private ResponseEntity<?> createPhieuDatXe(@RequestBody PhieuDatXe phieuDatXe){
        PhieuDatXe phieuDatXe1 = phieuDatXeService.createPhieuDatXe(phieuDatXe);
        return ResponseEntity.ok(phieuDatXe1);
    }

    @GetMapping("")
    private ResponseEntity<?> getPhieuDatXeByIdKhachhang(@RequestParam(name = "khachhang") Long id){
        return ResponseEntity.ok(phieuDatXeService.findPhieuDatXeByIdKhachHang(id));
    }
//    @PostMapping(path = "/cap-nhat/{id}")
//    private ResponseEntity<?> updatePhieuDatXe(@RequestBody PhieuDatXe phieuDatXe, @PathVariable Long id){
//        PhieuDatXe phieuDatXe1 =  phieuDatXeService.findPhieuDatXeByid(id);
//        if(phieuDatXe1 != null){
//            phieuDatXe1.setNgayBD(phieuDatXe.getNgayBD());
//            phieuDatXe1.setNgayKT(phieuDatXe.getNgayKT());
//            phieuDatXe1.setOto(phieuDatXe.getOto());
//
//        }
//        return ResponseEntity.ok(phieuDatXe1);
//    }
}
