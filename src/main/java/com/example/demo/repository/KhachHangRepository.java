package com.example.demo.repository;


import com.example.demo.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Long> {
    @Query(value = "select * from khachhang k where k.id_user = ?1", nativeQuery = true)
    Optional<KhachHang> findByIdUser(Long idUser);
}

