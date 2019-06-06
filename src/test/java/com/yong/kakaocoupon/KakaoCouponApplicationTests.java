package com.yong.kakaocoupon;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.yong.kakaocoupon.coupon.CouponRepository;
import com.yong.kakaocoupon.coupon.entity.Coupon;

@SpringBootTest
public class KakaoCouponApplicationTests {
	@Autowired
	private CouponRepository couponRepository;

	@Test
	public void contextLoads() {
	}

	@Test
	public void initialize_success() {
		List<Coupon> coupons = couponRepository.findAll();

		assertThat(coupons.size()).isEqualTo(6);
	}
}
