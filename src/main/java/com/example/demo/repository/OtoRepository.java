package com.example.demo.repository;


import com.example.demo.entity.Oto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OtoRepository extends JpaRepository<Oto, Long> {
    List<Oto> findByTrangThai(String trangthai);

    @Query(value = "select oto.id, oto.cho_ngoi, oto.gia_thue, oto.nam_sanxuat, oto.ten_xe, oto.thuong_hieu, oto.trang_thai, oto.main_image\n" +
            "from oto\n" +
            "inner join phieudatxe p on oto.id = p.id_oto\n" +
            "inner join hoadon h on p.id_hoadon = h.id\n" +
            "where h.id = ?1", nativeQuery = true)
    Oto findByHoaDon(Long id);

    @Query(value = "select count(*) from oto o where o.trang_thai like (?1)", nativeQuery = true)
    Integer countByTrangThai(String trangThai);
}
