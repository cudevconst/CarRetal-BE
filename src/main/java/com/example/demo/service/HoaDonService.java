package com.example.demo.service;

import com.example.demo.entity.HoaDon;
import com.example.demo.entity.Oto;
import com.example.demo.entity.PhieuDatXe;
import com.example.demo.repository.HoaDonRepository;
import com.example.demo.repository.OtoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class HoaDonService {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private OtoRepository otoRepository;
    @Transactional
    public HoaDon createHoaDon(PhieuDatXe phieuDatXe){
        HoaDon hoaDon = new HoaDon();
        hoaDon.setKhachhang(phieuDatXe.getKhachhang());
        hoaDon.setNgayCoc(phieuDatXe.getNgayDat());
        hoaDon.setTienCoc(otoRepository.findById(phieuDatXe.getOto().getId()).get().getGiaThue()*0.3);
        hoaDon.setSoTien(otoRepository.findById(phieuDatXe.getOto().getId()).get().getGiaThue()*1.0);
        hoaDon.setTrangThai("deposited");
        HoaDon hoaDon1 = hoaDonRepository.save(hoaDon);
        return hoaDon1;
    }
    public HoaDon getHoaDonById(Long id){
        Optional<HoaDon> optionalHoaDon = hoaDonRepository.findById(id);
        if(optionalHoaDon.isPresent()){
            return optionalHoaDon.get();
        }
        return null;
    }
    public List<HoaDon> getHoaDonKhachHangTrangThai(Long idKhachHang, String trangThai){
        List<HoaDon> list = hoaDonRepository.findByIdKhachhangAndTrangthai(idKhachHang, trangThai);
        return list;
    }
    @Transactional
    public HoaDon updateHoaDon(Long id){
        Optional<HoaDon> hoaDonOptional = hoaDonRepository.findById(id);
        if(hoaDonOptional.isPresent()){
            HoaDon hoaDon = hoaDonOptional.get();
            hoaDon.setTrangThai("paid");
            hoaDon.setNgayThanhToan(getDateNow());
            Oto oto = otoRepository.findByHoaDon(id);
            oto.setTrangThai("on");
            otoRepository.save(oto);
            hoaDonRepository.save(hoaDon);

            return hoaDon;
        }
        return null;
    }
    @Transactional

    public void deleteById(Long id){
        hoaDonRepository.deleteById(id);
        return;
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
