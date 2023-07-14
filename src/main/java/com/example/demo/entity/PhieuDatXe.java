package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "phieudatxe")
@Getter
@Setter
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PhieuDatXe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private Long id;

    private Date ngayDat;
    @Column(name = "ngay_bd")
    private Date ngayBD;
    @Column(name = "ngay_kt")
    private Date ngayKT;
    @Column(name = "trang_thai")
    private String trangThai;

    @JsonBackReference(value = "khachhang-phieudatxe")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_khachhang")
    private KhachHang khachhang;

//    @JsonBackReference(value = "nhanvien-phieudatxe")
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id_nhanvien")
//    private NhanVien nhanvien;

    @JsonBackReference(value = "oto-phieudatxe")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_oto")
    private Oto oto;

    @JsonBackReference(value = "hoadon-phieudatxe")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hoadon")
    private HoaDon hoadon;
}
