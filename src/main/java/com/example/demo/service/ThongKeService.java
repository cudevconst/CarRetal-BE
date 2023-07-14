package com.example.demo.service;

import com.example.demo.repository.HoaDonRepository;
import com.example.demo.repository.OtoRepository;
import com.example.demo.repository.PhieuDatXeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ThongKeService {

    @Autowired
    private OtoRepository otoRepository;
    @Autowired
    private PhieuDatXeRepository phieuDatXeRepository;

    @Autowired
    private HoaDonRepository hoaDonRepository;
    public Integer getCountByTrangThai(String trangThai){
        return otoRepository.countByTrangThai(trangThai);
    }
    public List<Map<String, Object>> countSoLanDatXe(){
        List<Map<String, Object>> list = phieuDatXeRepository.countLanDatOTo();


        return list;
    }
    public List<Map<String, Object>> countSoLanThoiGian(String start, String end){
        return phieuDatXeRepository.countLanDatTheoThoiGian(start, end);
    }

    public Double sumHoaDonTheoThoiGian(String start, String end){
        return hoaDonRepository.sumDoanhThuTheoThoiGian(start, end);
    }

    public Double sumHoaDonTheoThangThoiGianThuc(){
        return hoaDonRepository.sumDoanhThuThangTheoThoiGianThuc();
    }

    public List<Map<String, Object>> doanhThuTheoThuongHIeu(){
        return hoaDonRepository.doanhThuTheoThuongHieu();
    }

    public List<Map<String, Object>> doanhThuTheoTungThang(){
        return hoaDonRepository.doanhThuTheoTungThang();
    }
}
