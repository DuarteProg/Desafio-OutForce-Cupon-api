package com.lucasduarte.cupon_api.dto;

import com.lucasduarte.cupon_api.enums.CouponStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CouponResponseDTO {

        private String id;
        private String code;
        private String description;
        private Double discountValue;
        private LocalDateTime expirationDate;
        private CouponStatus status;
        private boolean published;
        private boolean redeemed;
}
