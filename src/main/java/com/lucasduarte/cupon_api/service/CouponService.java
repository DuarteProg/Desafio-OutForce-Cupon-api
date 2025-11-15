package com.lucasduarte.cupon_api.service;

import com.lucasduarte.cupon_api.model.Coupon;
import com.lucasduarte.cupon_api.repository.CouponRepository;

public class CouponService {

    private final CouponRepository repository;

    public CouponService(CouponRepository repository) {
        this.repository = repository;
    }

    public CouponResponseDTO findById(String id) {
        Coupon coupon = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cupom não encontrado."));

        return CouponMapper.toResponse(coupon);
    }
}
