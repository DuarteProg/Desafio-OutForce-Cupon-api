package com.lucasduarte.cupon_api.controller;

import com.lucasduarte.cupon_api.dto.CouponResponseDTO;
import com.lucasduarte.cupon_api.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService service;

    @GetMapping("/{id}")
    public ResponseEntity<CouponResponseDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }
}
