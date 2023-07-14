package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OtoResponse {
    private Long id;
    private String tenXe;

    private Integer choNgoi;

    private String thuongHieu;

    private Integer namSanXuat;

    private Long giaThue;
    private String trangThai;

    private String mainImage;
}
