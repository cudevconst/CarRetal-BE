package com.example.demo.controller;

import com.example.demo.config.Config;
import com.example.demo.dto.response.PaymentResponse;
import com.example.demo.entity.Oto;
import com.example.demo.entity.PhieuDatXe;
import com.example.demo.repository.KhachHangRepository;
import com.example.demo.service.OtoService;
import com.example.demo.service.PhieuDatXeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping(path = "/api/payment/vnpay")

public class PaymentVNPayController {

    @Autowired
    private OtoService otoService;
    @Autowired
    private PhieuDatXeService phieuDatXeService;


    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestBody PhieuDatXe phieuDatXe, HttpServletRequest request) throws UnsupportedEncodingException {

        String serverUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String returnUrl = serverUrl + Config.vnp_Returnurl;
        Oto oto = otoService.findById(phieuDatXe.getOto().getId());
        PhieuDatXe phieuDatXe1 = phieuDatXeService.createPhieuDatXe(phieuDatXe);
        Long amount = oto.getGiaThue();
        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_IpAddr = "127.0.0.1";
        String vnp_TmnCode = Config.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", Config.vnp_Version);
        vnp_Params.put("vnp_Command", Config.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount*100));
        vnp_Params.put("vnp_CurrCode", "VND");
//        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", phieuDatXe1.getId().toString());
        vnp_Params.put("vnp_ReturnUrl", returnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.put("vnp_OrderType", "order-type");
        Instant currentTimestamp = Instant.now();
        ZoneId zoneId = ZoneId.systemDefault(); // Múi giờ tại máy tính
        ZoneId targetZoneId = ZoneId.of("GMT+7"); // Múi giờ +7
        ZonedDateTime currentDateTime = currentTimestamp.atZone(zoneId);
        ZonedDateTime createDateTime = currentDateTime.withZoneSameInstant(targetZoneId);
        ZonedDateTime expireDateTime = createDateTime.plusMinutes(15);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String vnp_CreateDate = createDateTime.format(formatter);
        String vnp_ExpireDate = expireDateTime.format(formatter);

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;

        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setStatus("OK");
        paymentResponse.setMessage("success");
        paymentResponse.setUrl(paymentUrl);

        return ResponseEntity.ok(paymentResponse);
    }
    @GetMapping("/payment-info")
    public void infoPayment(
            @RequestParam("vnp_ResponseCode") String responseCode,
            @RequestParam("vnp_TransactionStatus") String transactionStatus,
            @RequestParam("vnp_OrderInfo") String orderId,
            HttpServletResponse response
    ) throws IOException {
        if(responseCode.equals("00") && transactionStatus.equals("00")){
            PhieuDatXe phieuDatXe = phieuDatXeService.findPhieuDatXeByid(Long.parseLong(orderId));
            otoService.updateTrangThai(phieuDatXe.getOto().getId(), "off");
            response.sendRedirect("http://localhost:3000/thanh-toan/success");
        }
        else{
            phieuDatXeService.deleteById(Long.parseLong(orderId));
            response.sendRedirect("http://localhost:3000/thanh-toan/error");
        }
    }
}

