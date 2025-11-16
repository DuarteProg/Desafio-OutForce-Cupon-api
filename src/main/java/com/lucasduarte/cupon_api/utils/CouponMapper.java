package com.lucasduarte.cupon_api.utils;

import com.lucasduarte.cupon_api.dto.CouponRequestDTO;
import com.lucasduarte.cupon_api.dto.CouponResponseDTO;
import com.lucasduarte.cupon_api.enums.CouponStatus;
import com.lucasduarte.cupon_api.model.Coupon;

import java.time.LocalDateTime;

public class CouponMapper {

    public static CouponResponseDTO toResponse(Coupon coupon) {
        return CouponResponseDTO.builder()
                .id(coupon.getId())
                .code(coupon.getCode())
                .description(coupon.getDescription())
                .discountValue(coupon.getDiscountValue())
                .expirationDate(coupon.getExpirationDate())
                .status(coupon.getStatus())
                .published(coupon.isPublished())
                .redeemed(coupon.isRedeemed())
                .build();
    }

    public static Coupon toEntity(CouponRequestDTO dto,
                                  String sanitizedCode,
                                  LocalDateTime expirationDate) {
        return Coupon.builder()
                .code(sanitizedCode)
                .description(dto.getDescription())
                .discountValue(dto.getDiscountValue())
                .expirationDate(expirationDate)
                .status(CouponStatus.ACTIVE)
                .published(dto.isPublished())
                .redeemed(false)
                .deleted(false)
                .build();
    }
}