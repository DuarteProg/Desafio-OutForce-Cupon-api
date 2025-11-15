package com.lucasduarte.cupon_api;

import com.lucasduarte.cupon_api.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CouponRepository extends JpaRepository<Coupon, String> {
}
