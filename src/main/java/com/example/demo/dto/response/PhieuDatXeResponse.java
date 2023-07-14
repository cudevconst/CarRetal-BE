package com.example.demo.dto.response;

import com.example.demo.entity.Oto;
import com.example.demo.entity.PhieuDatXe;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhieuDatXeResponse {
    private PhieuDatXe phieuDatXe;
    private OtoResponse oto;
}
