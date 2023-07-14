package com.example.demo.controller;

import com.example.demo.entity.KhachHang;
import com.example.demo.entity.NhanVien;
import com.example.demo.repository.KhachHangRepository;

import com.example.demo.repository.NhanVienReporisoty;
import com.example.demo.repository.UserRepository;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private NhanVienReporisoty nhanVienReporisoty;

    @GetMapping("/all")
    private ResponseEntity<?> getAllUser(){
        List<Map<String, Object>> list = new ArrayList<>();
        List<User> list1 = userRepository.findAllUser();
        for(User u : list1){
            Map<String, Object> map = new HashMap<>();
            KhachHang khachHang = khachHangRepository.findByIdUser(u.getId()).get();
            Map<String, Object> mapKhachHang = new HashMap<>();
            mapKhachHang.put("id", khachHang.getId());
            mapKhachHang.put("cccd", khachHang.getCccd());
            map.put("khachhang", mapKhachHang);
            map.put("user", u);
            list.add(map);
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getUserID(@PathVariable Long id){
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            return ResponseEntity.ok(userOptional.get());
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/dang-nhap")
    private ResponseEntity<?> getUsernameAndPassword(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password){
        User user = null;
        Map<String, Object> map = new HashMap<>();
        user = userRepository.findByUsernameAndPassword(username, password);

        if(user != null){
            Optional<KhachHang> khachHangOptional = khachHangRepository.findByIdUser(user.getId());
            Optional<NhanVien> nhanVienOptional = nhanVienReporisoty.findByIdUser(user.getId());


            map.put("user", user);
            map.put("message", "Đăng nhập thành công");
            if(khachHangOptional.isPresent()){
                KhachHang khachHang = khachHangOptional.get();
                Map<String, Object> mapKhachHang = new HashMap<>();
                mapKhachHang.put("id", khachHang.getId());
                mapKhachHang.put("cccd", khachHang.getCccd());
                map.put("khachhang", mapKhachHang);
                map.put("nhanvien", null);
            }
            if(nhanVienOptional.isPresent()){
                NhanVien nhanVien = nhanVienOptional.get();
                Map<String, Object> mapNhanVien = new HashMap<>();
                mapNhanVien.put("id", nhanVien.getId());
                mapNhanVien.put("chucVu", nhanVien.getChucVu());
                map.put("khachhang", null);

                map.put("nhanvien", mapNhanVien);
            }
            return ResponseEntity.ok(map);
        }
        else{
            map.put("message", "Tên tài khoản hoặc mật khẩu không chính xác");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
        }


    }
    @GetMapping("")
    private ResponseEntity<?> getUsername(@RequestParam(value = "username") String username){
        Map<String, Object> map = new HashMap<>();
        if(userRepository.existsByUsername(username)){
            map.put("message", "Tài khoản đã tồn tại");
            map.put("dangky", 1);
        }
        else{
            map.put("dangky", 0);
        }
        return ResponseEntity.ok(map);

    }
    @PostMapping("/cap-nhat/{id}")
    private ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user){
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            User user1 = optionalUser.get();
            user1.setDiaChi(user.getDiaChi());
            user1.setEmail(user.getEmail());
            user1.setHoTen(user.getHoTen());
            user1.setNgaySinh(user.getNgaySinh());
            user1.setPassword(user.getPassword());
            user1.setSdt(user.getSdt());
            userRepository.save(user1);
            return ResponseEntity.ok(user1);
        }
        else{
            return ResponseEntity.notFound().build();
        }

    }
    @PostMapping("/dang-ki")
    private User createUser(@RequestBody User user){
        userRepository.save(user);
        String role = user.getRole();
        if(role.compareTo("user") == 0){
            KhachHang khachHang = new KhachHang();
            khachHang.setUser(user);
            khachHangRepository.save(khachHang);
        }
        else{
            NhanVien nhanVien = new NhanVien();
            nhanVien.setUser(user);
            nhanVien.setChucVu("Level 1");
            nhanVienReporisoty.save(nhanVien);
        }

//        return ResponseEntity.ok().build();
        return user;
    }

}
