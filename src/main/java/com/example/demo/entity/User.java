package com.example.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "`user`")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private Long id;

    private String username;
    private String password;
    private String hoTen;
    private Date ngaySinh;
    private String diaChi;
    @Column(name = "so_dien_thoai")
    private String sdt;
    private String email;
    private String role;


    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private NhanVien nhanvien;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private KhachHang khachhang;
}
