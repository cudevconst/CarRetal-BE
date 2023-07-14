package com.example.demo.repository;


import com.example.demo.entity.ChiTietOto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChiTietOtoRepository extends JpaRepository<ChiTietOto, Long> {
    @Query(value = "select * from chitietoto c where c.id_oto = ?1", nativeQuery = true)
    List<ChiTietOto> findByIdOto(Long idOto);
}

