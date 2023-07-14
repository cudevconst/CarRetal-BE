package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "nhanvien")
@Data
@Getter
@Setter
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class NhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private Long id;
    private String chucVu;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private User user;


//    @JsonManagedReference(value = "nhanvien-phieudatxe")
//    @OneToMany(mappedBy = "nhanvien", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<PhieuDatXe> phieuDatXes;
}
