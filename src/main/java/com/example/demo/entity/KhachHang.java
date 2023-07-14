package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "khachhang")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KhachHang {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private Long id;
    private String cccd;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    @JsonManagedReference(value = "khachhang-hoadon")
    @OneToMany(mappedBy = "khachhang", cascade = CascadeType.ALL)
    private List<HoaDon> hoaDons;
    @JsonManagedReference(value = "khachhang-phieudatxe")
    @OneToMany(mappedBy = "khachhang", cascade = CascadeType.ALL)
    private List<PhieuDatXe> phieuDatXes;
}


