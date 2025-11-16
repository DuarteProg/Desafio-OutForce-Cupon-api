package com.lucasduarte.cupon_api.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponRequestDTO {

    @NotBlank(message = "O campo code é obrigatório.")
    private String code;

    @NotBlank(message = "O campo description é obrigatório.")
    private String description;

    @NotNull(message = "O campo discountValue é obrigatório.")
    @DecimalMin(value = "0.5", message = "O valor mínimo do desconto é 0.5.")
    private Double discountValue;

    @NotNull(message = "O campo expirationDate é obrigatório.")
    private String expirationDate;

    private boolean published = false;
}
