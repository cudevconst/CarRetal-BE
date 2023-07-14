package com.example.demo.controller;

import com.example.demo.entity.KhachHang;
import com.example.demo.repository.KhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/khach-hang")
public class KhachHangController {
    @Autowired
    private KhachHangRepository khachHangRepository;
    @GetMapping("/{id}")
    private KhachHang getKhachhang(@PathVariable Long id){
        Optional<KhachHang> khachHang = khachHangRepository.findById(id);
        if(khachHang.isPresent()){
            return khachHang.get();
        }
        return null;
    }
}
