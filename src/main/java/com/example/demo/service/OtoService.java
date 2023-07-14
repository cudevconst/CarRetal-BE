package com.example.demo.service;


import com.example.demo.entity.ChiTietOto;
import com.example.demo.repository.OtoRepository;
import com.example.demo.entity.Oto;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.QRDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OtoService {
    @Autowired
    private OtoRepository otoRepository;

    public List<Oto> getAllOtoActive(String trangthai){
        return otoRepository.findByTrangThai(trangthai);
    }
    public Oto findById(Long id){
        Optional<Oto> optionalOto = otoRepository.findById(id);
        if(optionalOto.isPresent()){
            return optionalOto.get();
        }
        return null;
    }
    @Transactional
    public Oto saveMainImageOto(String image, Long idOto){
        Optional<Oto> optionalOto = otoRepository.findById(idOto);
        if(optionalOto.isPresent()){
            Oto oto = optionalOto.get();
            oto.setMainImage(image);
            return otoRepository.save(oto);
        }
        return null;
    }
    @Transactional
    public Oto updateOto(Oto otonew, Long id){
        Optional<Oto> otoOptional = otoRepository.findById(id);
        if(otoOptional.isPresent()){
            Oto oto = otoOptional.get();
            oto.setTenXe(otonew.getTenXe());
            oto.setChoNgoi(otonew.getChoNgoi());
            oto.setThuongHieu(otonew.getThuongHieu());
            oto.setNamSanXuat(otonew.getNamSanXuat());
            oto.setGiaThue(otonew.getGiaThue());
            otoRepository.save(oto);
            return otonew;
        }
        return null;
    }
    @Transactional
    public Oto updateTrangThai(Long id, String trangThai){
        Optional<Oto> otoOptional = otoRepository.findById(id);
        if(otoOptional.isPresent()){
            Oto oto = otoOptional.get();
            oto.setTrangThai(trangThai);
            otoRepository.save(oto);
            return oto;
        }
        return null;
    }

    public double predictGiaOto(String thuongHieu, Integer choNgoi, Integer namSanXuat){

        List<Oto> list = otoRepository.findAll();


        double[][] features = new double[list.size()][4];
        double[] prices = new double[list.size()];

        for(int i = 0; i < list.size(); i++){
            Oto oto = list.get(i);
            features[i][0] = Double.valueOf(converttoInt(oto.getThuongHieu()));
            features[i][1] = Double.valueOf(oto.getChoNgoi());
            features[i][2] = Double.valueOf(oto.getNamSanXuat());
            prices[i] = Double.valueOf(oto.getGiaThue());
        }
        RealMatrix XMatrix = MatrixUtils.createRealMatrix(features);

        // Tạo một vector RealVector từ vector kết quả Y
        RealVector YVector = MatrixUtils.createRealVector(prices);

        // Tính toán hệ số của mô hình hồi quy tuyến tính
        RealVector coefficients = new QRDecomposition(XMatrix).getSolver().solve(YVector);

        // Tính toán dự đoán giá xe dựa trên các yếu tố đầu vào và hệ số của mô hình
        double predictedPrice = coefficients.getEntry(0) * converttoInt(thuongHieu) +
                coefficients.getEntry(1) * choNgoi +
                coefficients.getEntry(2) * namSanXuat;

        return predictedPrice;



    }

    public Integer converttoInt(String thuongHieu){
        int intValue = 0;
        for (char c : thuongHieu.toCharArray()) {
            intValue += (int) c;
        }
        return intValue;
    }


}
