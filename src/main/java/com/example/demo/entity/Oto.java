package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "oto")
@Data
@Getter
@Setter
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Oto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private Long id;
    @Column(name = "ten_xe")
    private String tenXe;
    @Column(name = "cho_ngoi")
    private Integer choNgoi;
    @Column(name = "thuong_hieu")
    private String thuongHieu;
    @Column(name = "nam_sanxuat")
    private Integer namSanXuat;
    @Column(name = "gia_thue")
    private Long giaThue;
    @Column(name = "trang_thai")
    private String trangThai;

    private String mainImage;



    @JsonManagedReference(value = "oto-chitietoto")
    @OneToMany(mappedBy = "oto", cascade = CascadeType.ALL)
    private List<ChiTietOto> chiTietOtos;

    @JsonManagedReference(value = "oto-phieudatxe")
    @OneToMany(mappedBy = "oto", cascade = CascadeType.ALL)
    private List<PhieuDatXe> phieuDatXes;
}
