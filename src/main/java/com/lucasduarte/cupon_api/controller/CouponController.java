package com.lucasduarte.cupon_api.controller;

import com.lucasduarte.cupon_api.dto.CouponRequestDTO;
import com.lucasduarte.cupon_api.dto.CouponResponseDTO;
import com.lucasduarte.cupon_api.service.CouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService service;

    @GetMapping("/{id}")
    public ResponseEntity<CouponResponseDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<CouponResponseDTO> create(@Valid @RequestBody CouponRequestDTO dto) {
        CouponResponseDTO response = service.create(dto);
        return ResponseEntity.status(201).body(response);
    }
}
