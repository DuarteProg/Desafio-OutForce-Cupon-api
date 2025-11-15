package com.lucasduarte.cupon_api.repository;

import com.lucasduarte.cupon_api.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CouponRepository extends JpaRepository<Coupon, String> {
}
