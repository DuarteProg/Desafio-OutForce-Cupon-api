package com.lucasduarte.cupon_api.service;

import com.lucasduarte.cupon_api.dto.CouponRequestDTO;
import com.lucasduarte.cupon_api.dto.CouponResponseDTO;
import com.lucasduarte.cupon_api.enums.CouponStatus;
import com.lucasduarte.cupon_api.exception.BadRequestException;
import com.lucasduarte.cupon_api.exception.CouponNotFoundException;
import com.lucasduarte.cupon_api.model.Coupon;
import com.lucasduarte.cupon_api.repository.CouponRepository;
import com.lucasduarte.cupon_api.utils.CouponMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


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
        if (dto.getDiscountValue() < 0.5) {
            throw new BadRequestException("O valor mínimo do desconto é 0.5.");
        }

        String sanitizedCode = sanitizeCode(dto.getCode());

        LocalDateTime expiration = LocalDateTime.parse(dto.getExpirationDate());
        if (expiration.isBefore(LocalDateTime.now())) {
            throw new BadRequestException("A data de expiração não pode ser no passado.");
        }

        Coupon coupon = CouponMapper.toEntity(dto, sanitizedCode, expiration);

        coupon = repository.save(coupon);

        return CouponMapper.toResponse(coupon);
    }

    public void delete(String id) {
        Coupon coupon = repository.findById(id)
                .orElseThrow(() -> new CouponNotFoundException("Cupom não encontrado."));

        if (coupon.isDeleted()) {
            throw new BadRequestException("O cupom já está deletado.");
        }

        coupon.setDeleted(true);
        coupon.setStatus(CouponStatus.DELETED);

        repository.save(coupon);
    }



    private String sanitizeCode(String code) {
        if (code == null) return null;

        String codeWithNoSpecialCaractersAndWithSixCaracters = code.replaceAll("[^A-Za-z0-9]", "");

        if (codeWithNoSpecialCaractersAndWithSixCaracters.length() > 6) {
            codeWithNoSpecialCaractersAndWithSixCaracters = codeWithNoSpecialCaractersAndWithSixCaracters.substring(0, 6);
        } else if (codeWithNoSpecialCaractersAndWithSixCaracters.length() < 6) {
            codeWithNoSpecialCaractersAndWithSixCaracters = String.format("%-6s", codeWithNoSpecialCaractersAndWithSixCaracters).replace(" ", "X");
        }

        return codeWithNoSpecialCaractersAndWithSixCaracters;
    }
}
