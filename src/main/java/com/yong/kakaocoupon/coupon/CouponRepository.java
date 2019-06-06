package com.yong.kakaocoupon.coupon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.yong.kakaocoupon.coupon.entity.Coupon;
import com.yong.kakaocoupon.coupon.enumclass.CouponStatus;

@Repository
public interface CouponRepository extends CrudRepository<Coupon, Integer> {
	List<Coupon> findAll();
	List<Coupon> findAllByStatus(CouponStatus status);
}
