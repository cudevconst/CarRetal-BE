package com.example.demo.dto;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhieuDatXeDTO {
    private Long id;
    private Date ngayDat;
    private Date ngayBD;
    private Date ngayKT;
    private String trangThai;
    private Long idHoaDon;
    private Long idKhachHang;
    private Long idOto;
}
