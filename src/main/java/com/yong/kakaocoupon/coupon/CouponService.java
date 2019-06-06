package com.yong.kakaocoupon.coupon;

import static java.util.stream.Collectors.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.yong.kakaocoupon.coupon.dto.CouponResult;
import com.yong.kakaocoupon.coupon.entity.Coupon;
import com.yong.kakaocoupon.coupon.enumclass.CouponStatus;
import com.yong.kakaocoupon.coupon.exception.CouponNotFoundException;
import com.yong.kakaocoupon.coupon.exception.CouponUnusableException;
import com.yong.kakaocoupon.coupon.validate.ValidateCondition;
import com.yong.kakaocoupon.coupon.validate.ValidateContainer;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CouponService {
	private static final Comparator<Coupon> COUPON_COMPARATOR = Comparator.comparing(
		Coupon::getUsableUntil).thenComparing(Comparator.comparing(Coupon::getDiscountAmount).reversed());

	private CouponRepository couponRepository;
	private ValidateContainer validateContainer;

	public List<Coupon> getUsableSortedCoupons(int amount, LocalDateTime dateTime) {
		return couponRepository.findAll().stream()
			.filter(c -> validateContainer.validate(c, new ValidateCondition(amount, dateTime)))
			.sorted(COUPON_COMPARATOR)
			.collect(toList());
	}

	@Transactional
	public CouponResult use(Integer couponId, int amount, LocalDateTime dateTime) {
		Coupon coupon = couponRepository.findById(couponId)
			.orElseThrow(() -> new CouponNotFoundException("Not found coupon :: " + couponId));

		if(!validateContainer.validate(coupon, new ValidateCondition(amount, dateTime))) {
			throw new CouponUnusableException("Unusable coupon :: " + couponId);
		}

		CouponResult result = coupon.use(amount);

		couponRepository.save(coupon);

		return result;
	}
}
