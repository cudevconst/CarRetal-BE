package com.example.demo.repository;


import com.example.demo.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface NhanVienReporisoty extends JpaRepository<NhanVien, Long> {
    @Query(value = "select * from nhanvien k where k.id_user = ?1", nativeQuery = true)
    Optional<NhanVien> findByIdUser(Long idUser);
}
