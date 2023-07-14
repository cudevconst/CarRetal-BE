package com.example.demo.repository;


import com.example.demo.entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Long> {
    @Query(value = "select * from hoadon h where h.id_khachhang = ?1 and h.trang_thai like (?2)",nativeQuery = true)
    List<HoaDon> findByIdKhachhangAndTrangthai(Long idKhachhang, String trangThai);

    @Query(value = "select sum(so_tien)\n" +
            "from hoadon\n" +
            "where ngay_tt >= ?1 and ngay_tt <= ?2 and trang_thai like ('paid')", nativeQuery = true)
    Double sumDoanhThuTheoThoiGian(String start, String end);

    @Query(value = "select sum(so_tien)\n" +
            "from hoadon\n" +
            "where MONTH(ngay_tt) = MONTH(CURRENT_TIMESTAMP())\n" +
            "and YEAR(ngay_tt) = YEAR(CURRENT_TIMESTAMP)\n" +
            "and trang_thai = 'paid'", nativeQuery = true)
    Double sumDoanhThuThangTheoThoiGianThuc();

    @Query(value = "select a.thuong_hieu, a.doanh_thu from(\n" +
            "select thuong_hieu, h.trang_thai,  sum(so_tien) as doanh_thu from oto\n" +
            "left join phieudatxe p on oto.id = p.id_oto\n" +
            "left join hoadon h on h.id = p.id_hoadon\n" +
            "group by oto.thuong_hieu\n" +
            "having h.trang_thai like 'paid' or sum(so_tien) is null\n" +
            "order by sum(so_tien) desc\n" +
            ") as a", nativeQuery = true)
    List<Map<String, Object>> doanhThuTheoThuongHieu();

    @Query(value = "select a.month,a.year, a.sotien from(\n" +
            "               select month(ngay_tt) as month, year(ngay_tt) as year ,trang_thai,  sum(so_tien) as sotien from hoadon\n" +
            "               group by month(ngay_tt), year(ngay_tt)\n" +
            "               having trang_thai like ('paid')\n" +
            "               order by month(ngay_tt)\n" +
            ")\n" +
            "as a", nativeQuery = true)
    List<Map<String, Object>> doanhThuTheoTungThang();

}
