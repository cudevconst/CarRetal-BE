package com.example.demo.service;

import com.example.demo.entity.ChiTietOto;
import com.example.demo.entity.Oto;
import com.example.demo.repository.ChiTietOtoRepository;
import com.example.demo.repository.OtoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChiTietOtoService {
    @Autowired
    private ChiTietOtoRepository chiTietOtoRepository;
    @Autowired
    private OtoRepository otoRepository;
//    public List<ChiTietOto> getChiTietOtoById(Long id){
//        return chiTietOtoRepository.findByIdOto(id);
//    }

    public List<ChiTietOto> findByIdOto(Long idOto){
        return chiTietOtoRepository.findByIdOto(idOto);
    }

    @Transactional
    public ChiTietOto saveImageChiTietOto(String image, Long idOto){
        Optional<Oto> optionalOto = otoRepository.findById(idOto);
        if(optionalOto.isPresent()){
            ChiTietOto chiTietOto = new ChiTietOto();
            chiTietOto.setUrlImage(image);
            chiTietOto.setOto(optionalOto.get());
            return chiTietOtoRepository.save(chiTietOto);
        }
        return null;
    }
    @Transactional
    public void upDateImageChiTietOto(String image, Long id){
        Optional<ChiTietOto> chiTietOtoOptional = chiTietOtoRepository.findById(id);
        if(chiTietOtoOptional.isPresent()){
            ChiTietOto chiTietOto = chiTietOtoOptional.get();
            chiTietOto.setUrlImage(image);
            chiTietOtoRepository.save(chiTietOto);
        }
    }

    private String imageFileName(MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        String newFileName = UUID.randomUUID().toString() + "." + fileExtension;
        return newFileName;
    }
}
