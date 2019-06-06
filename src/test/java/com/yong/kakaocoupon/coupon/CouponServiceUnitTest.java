package com.yong.kakaocoupon.coupon;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.yong.kakaocoupon.coupon.entity.Coupon;

@ExtendWith(MockitoExtension.class)
class CouponServiceUnitTest {
	@InjectMocks
	private CouponService couponService;
	@Mock
	private CouponRepository couponRepository;

	@Test
	void 쿠폰목록조회_만료일시가_가까운순_정렬() {
		given(couponRepository.findAll())
			.willReturn(Arrays.asList(mockCouponDay(1,6), mockCouponDay(2,5), mockCouponDay(3,7)));

		List<Coupon> coupons = couponService.getUsableSortedCoupons();

		assertThat(coupons.get(0).getId()).isEqualTo(2);
		assertThat(coupons.get(1).getId()).isEqualTo(1);
		assertThat(coupons.get(2).getId()).isEqualTo(3);
	}

	@Test
	void 쿠폰목록조회_만료일시가_같으면_할인금액_큰순_정렬() {
		given(couponRepository.findAll())
			.willReturn(Arrays.asList(mockCouponDiscountAmount(1,500), mockCouponDiscountAmount(2,5000), mockCouponDiscountAmount(3,700)));

		List<Coupon> coupons = couponService.getUsableSortedCoupons();

		assertThat(coupons.get(0).getId()).isEqualTo(2);
		assertThat(coupons.get(1).getId()).isEqualTo(3);
		assertThat(coupons.get(2).getId()).isEqualTo(1);
	}

	private Coupon mockCouponDay(int id, int day) {
		Coupon coupon = new Coupon();

		coupon.setId(id);
		coupon.setUsableUntil(LocalDateTime.of(2019, 6, day, 12, 0));

		return coupon;
	}

	private Coupon mockCouponDiscountAmount(int id, int discountAmount) {
		Coupon coupon = new Coupon();

		coupon.setId(id);
		coupon.setUsableUntil(LocalDateTime.of(2019, 6, 6, 12, 0));
		coupon.setDiscountAmount(discountAmount);

		return coupon;
	}
}