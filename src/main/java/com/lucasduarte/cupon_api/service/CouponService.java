package com.lucasduarte.cupon_api.service;

import com.lucasduarte.cupon_api.dto.CouponRequestDTO;
import com.lucasduarte.cupon_api.dto.CouponResponseDTO;
import com.lucasduarte.cupon_api.exception.CouponNotFoundException;
import com.lucasduarte.cupon_api.model.Coupon;
import com.lucasduarte.cupon_api.repository.CouponRepository;
import com.lucasduarte.cupon_api.utils.CouponMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository repository;



    public CouponResponseDTO findById(String id) {
        Coupon coupon = repository.findById(id)
                .orElseThrow(() -> new CouponNotFoundException("Cupom não encontrado."));

        return CouponMapper.toResponse(coupon);
    }

    public CouponResponseDTO create(CouponRequestDTO dto) {
        Coupon coupon = CouponMapper.toEntity(dto);

        coupon = repository.save(coupon);

        return CouponMapper.toResponse(coupon);
    }
}
