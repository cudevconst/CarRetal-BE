package com.example.demo.service;

import com.example.demo.dto.response.OtoResponse;
import com.example.demo.dto.response.PhieuDatXeResponse;
import com.example.demo.entity.HoaDon;
import com.example.demo.entity.Oto;
import com.example.demo.entity.PhieuDatXe;
import com.example.demo.repository.HoaDonRepository;
import com.example.demo.repository.OtoRepository;
import com.example.demo.repository.PhieuDatXeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class PhieuDatXeService {
    @Autowired
    private PhieuDatXeRepository phieuDatXeRepository;
    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private OtoRepository otoRepository;
    @Autowired
    private HoaDonService hoaDonService;
    @Autowired
    private OtoService otoService;
    public PhieuDatXe findPhieuDatXeByid(Long id){
        Optional<PhieuDatXe> phieuDatXe = phieuDatXeRepository.findById(id);
        if(phieuDatXe.isPresent()){
            return phieuDatXe.get();
        }
        else{
            return null;
        }
    }
    public PhieuDatXeResponse findById(Long id){
        PhieuDatXeResponse phieuDatXeResponse = new PhieuDatXeResponse();
        Optional<PhieuDatXe> phieuDatXe = phieuDatXeRepository.findById(id);
        if(phieuDatXe.isPresent()){
            phieuDatXeResponse.setPhieuDatXe(phieuDatXe.get());
            Oto oto = otoRepository.findById(phieuDatXeRepository.findIdOtoById(id)).get();
            OtoResponse otoResponse = new OtoResponse();
            otoResponse.setId(oto.getId());
            otoResponse.setTenXe(oto.getTenXe());
            otoResponse.setChoNgoi(oto.getChoNgoi());
            otoResponse.setGiaThue(oto.getGiaThue());
            otoResponse.setThuongHieu(oto.getThuongHieu());
            otoResponse.setTrangThai(oto.getTrangThai());
            otoResponse.setNamSanXuat(oto.getNamSanXuat());
            otoResponse.setMainImage(oto.getMainImage());
            phieuDatXeResponse.setOto(otoResponse);
            return phieuDatXeResponse;
        }
        else{
            return null;
        }
    }
    @Transactional
    public PhieuDatXe createPhieuDatXe(PhieuDatXe phieuDatXe){
        HoaDon hoaDon = hoaDonService.createHoaDon(phieuDatXe);
        phieuDatXe.setHoadon(hoaDon);
        phieuDatXe.setNgayDat(getDateNow());
        PhieuDatXe phieuDatXe1 = phieuDatXeRepository.save(phieuDatXe);
        otoService.updateTrangThai(phieuDatXe.getOto().getId(), "off");
        return phieuDatXe1;
    }

    @Transactional
    public void deleteById(Long id){
        PhieuDatXe phieuDatXe = phieuDatXeRepository.findById(id).get();
        hoaDonRepository.deleteById(phieuDatXe.getHoadon().getId());
        phieuDatXeRepository.deleteById(phieuDatXe.getId());
        return;
    }
    public List<PhieuDatXe> findPhieuDatXeByIdKhachHang(Long id){
        List<PhieuDatXe> list = phieuDatXeRepository.findByIdKhachhang(id);
        return list;
    }

    private Date getDateNow(){
        ZoneId zoneId = ZoneId.of("GMT+7");
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = zonedDateTime.format(formatter);

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate localDate = LocalDate.parse(formattedDate, formatter1);
        Date date = Date.valueOf(localDate);
        return date;
    }
}
