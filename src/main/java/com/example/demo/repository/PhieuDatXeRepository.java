package com.example.demo.repository;


import com.example.demo.entity.PhieuDatXe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PhieuDatXeRepository extends JpaRepository<PhieuDatXe, Long> {
    @Query(value = "select * from phieudatxe p where p.id_khachhang = ?1 order by p.ngay_dat desc", nativeQuery = true)
    List<PhieuDatXe> findByIdKhachhang(Long idKhachhang);

    @Query(value = "select oto.id, cho_ngoi, gia_thue, nam_sanxuat, ten_xe, thuong_hieu, oto.trang_thai, count(*) as soluong\n" +
            "from oto\n" +
            "inner join phieudatxe p on oto.id = p.id_oto\n" +
            "group by p.id_oto", nativeQuery = true)
    List<Map<String, Object>> countLanDatOTo();

    @Query(value = "select oto.id, cho_ngoi, gia_thue, nam_sanxuat, ten_xe, thuong_hieu, oto.trang_thai, p.ngay_bd, count(*) as soluong\n" +
            "from oto\n" +
            "right join phieudatxe p on oto.id = p.id_oto\n" +
            "group by oto.id, p.ngay_bd\n" +
            "having p.ngay_bd >= ?1 and p.ngay_bd <= ?2", nativeQuery = true)
    List<Map<String, Object>> countLanDatTheoThoiGian(String start, String end);
    @Query(value = "select id_oto from phieudatxe p where p.id = ?1", nativeQuery = true)
    Long findIdOtoById(Long id);
}
