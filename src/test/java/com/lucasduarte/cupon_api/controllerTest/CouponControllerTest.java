package com.lucasduarte.cupon_api.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucasduarte.cupon_api.controller.CouponController;
import com.lucasduarte.cupon_api.dto.CouponRequestDTO;
import com.lucasduarte.cupon_api.dto.CouponResponseDTO;
import com.lucasduarte.cupon_api.exception.BadRequestException;
import com.lucasduarte.cupon_api.exception.CouponNotFoundException;
import com.lucasduarte.cupon_api.enums.CouponStatus;
import com.lucasduarte.cupon_api.service.CouponService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CouponController.class)
public class CouponControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CouponService service;

    @Autowired
    private ObjectMapper objectMapper;

    private CouponResponseDTO response;

    @BeforeEach
    void setup() {
        response = CouponResponseDTO.builder()
                .id("123")
                .code("ABC123")
                .description("desc")
                .discountValue(1.0)
                .expirationDate(LocalDateTime.now().plusDays(1))
                .status(CouponStatus.ACTIVE)
                .published(false)
                .redeemed(false)
                .build();
    }

    @Test
    void deveRetornarCupomPorId() throws Exception {
        Mockito.when(service.findById("123")).thenReturn(response);

        mockMvc.perform(get("/coupon/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.code").value("ABC123"));
    }

    @Test
    void deveRetornarNotFoundQuandoCupomNaoExiste() throws Exception {
        Mockito.when(service.findById("999"))
                .thenThrow(new CouponNotFoundException("Cupom não encontrado."));

        mockMvc.perform(get("/coupon/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Cupom não encontrado."));
    }

    @Test
    void deveCriarCupomComSucesso() throws Exception {
        CouponRequestDTO dto = CouponRequestDTO.builder()
                .code("ABC-123")
                .description("desc")
                .discountValue(1.0)
                .expirationDate("2030-01-01T10:00:00")
                .published(false)
                .build();

        Mockito.when(service.create(any(CouponRequestDTO.class)))
                .thenReturn(response);

        mockMvc.perform(post("/coupon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("ABC123"));
    }

    @Test
    void deveRetornarBadRequestQuandoDescontoInvalido() throws Exception {
        CouponRequestDTO dto = CouponRequestDTO.builder()
                .code("ABC123")
                .description("desc")
                .discountValue(0.1)
                .expirationDate("2030-01-01T10:00:00")
                .build();

        Mockito.when(service.create(any(CouponRequestDTO.class)))
                .thenThrow(new BadRequestException("O valor mínimo do desconto é 0.5."));

        mockMvc.perform(post("/coupon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("O valor mínimo do desconto é 0.5."));
    }

    @Test
    void deveDeletarCupomComSucesso() throws Exception {
        mockMvc.perform(delete("/coupon/123"))
                .andExpect(status().isNoContent());
    }

    @Test
    void naoDeveDeletarCupomJaDeletado() throws Exception {
        doThrow(new BadRequestException("Este cupom já está deletado."))
                .when(service).delete("123");

        mockMvc.perform(delete("/coupon/123"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Este cupom já está deletado."));
    }

    @Test
    void deveRetornarNotFoundAoDeletarCupomInexistente() throws Exception {
        doThrow(new CouponNotFoundException("Cupom não encontrado."))
                .when(service).delete("999");

        mockMvc.perform(delete("/coupon/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Cupom não encontrado."));
    }
}
