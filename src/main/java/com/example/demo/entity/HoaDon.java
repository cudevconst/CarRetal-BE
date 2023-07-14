package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "hoadon")
@Getter
@Setter
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private Long id;

    private Date ngayCoc;
    @Column(name = "ngay_tt")
    private Date ngayThanhToan;
    private String trangThai;
    private Double tienCoc;
    private Double soTien;

    @JsonBackReference(value = "khachhang-hoadon")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_khachhang")
    private KhachHang khachhang;

    @JsonManagedReference(value = "hoadon-phieudatxe")
    @OneToMany(mappedBy = "hoadon", cascade = CascadeType.ALL)
    private List<PhieuDatXe> phieuDatXes;
}
