package com.lucasduarte.cupon_api.serviceTest;

import com.lucasduarte.cupon_api.dto.CouponRequestDTO;
import com.lucasduarte.cupon_api.dto.CouponResponseDTO;
import com.lucasduarte.cupon_api.enums.CouponStatus;
import com.lucasduarte.cupon_api.exception.BadRequestException;
import com.lucasduarte.cupon_api.exception.CouponNotFoundException;
import com.lucasduarte.cupon_api.model.Coupon;
import com.lucasduarte.cupon_api.repository.CouponRepository;
import com.lucasduarte.cupon_api.service.CouponService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class CouponServiceTest {

    private CouponRepository repository;
    private CouponService service;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(CouponRepository.class);
        service = new CouponService(repository);
    }

    @Test
    void deveBuscarPorIdComSucesso() {
        Coupon coupon = Coupon.builder()
                .id("123")
                .code("ABC123")
                .description("desc")
                .discountValue(1.0)
                .expirationDate(LocalDateTime.now().plusDays(1))
                .status(CouponStatus.ACTIVE)
                .build();

        Mockito.when(repository.findById("123")).thenReturn(Optional.of(coupon));

        CouponResponseDTO response = service.findById("123");

        assertEquals("123", response.getId());
        assertEquals("ABC123", response.getCode());
    }

    @Test
    void deveRetornarErroQuandoCupomNaoExiste() {
        Mockito.when(repository.findById("x")).thenReturn(Optional.empty());

        assertThrows(CouponNotFoundException.class,
                () -> service.findById("x"));
    }

    @Test
    void deveCriarCupomComSucesso() {
        CouponRequestDTO dto = CouponRequestDTO.builder()
                .code("ABC-123")
                .description("teste")
                .discountValue(1.0)
                .expirationDate("2030-01-01T10:00:00")
                .published(false)
                .build();

        Mockito.when(repository.save(any(Coupon.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        CouponResponseDTO response = service.create(dto);

        assertEquals("ABC123", response.getCode()); // sanitizado
        assertEquals(CouponStatus.ACTIVE, response.getStatus());
    }

    @Test
    void deveLancarErroQuandoDescontoMenorQueMinimo() {
        CouponRequestDTO dto = CouponRequestDTO.builder()
                .code("AAA123")
                .description("d")
                .discountValue(0.3)
                .expirationDate("2030-01-01T10:00:00")
                .build();

        assertThrows(BadRequestException.class, () -> service.create(dto));
    }

    @Test
    void deveLancarErroQuandoDataEhPassado() {
        CouponRequestDTO dto = CouponRequestDTO.builder()
                .code("AAA123")
                .description("d")
                .discountValue(1.0)
                .expirationDate("2000-01-01T10:00:00")
                .build();

        assertThrows(BadRequestException.class, () -> service.create(dto));
    }

    @Test
    void deveDeletarCupom() {
        Coupon coupon = Coupon.builder()
                .id("123")
                .deleted(false)
                .status(CouponStatus.ACTIVE)
                .build();

        Mockito.when(repository.findById("123"))
                .thenReturn(Optional.of(coupon));

        Mockito.when(repository.save(any(Coupon.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        service.delete("123");

        assertTrue(coupon.isDeleted());
        assertEquals(CouponStatus.DELETED, coupon.getStatus());
    }

    @Test
    void naoDeveDeletarCupomJaDeletado() {
        Coupon coupon = Coupon.builder()
                .id("123")
                .deleted(true)
                .status(CouponStatus.DELETED)
                .build();

        Mockito.when(repository.findById("123"))
                .thenReturn(Optional.of(coupon));

        assertThrows(BadRequestException.class,
                () -> service.delete("123"));
    }
}
