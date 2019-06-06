package com.yong.kakaocoupon.coupon;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.yong.kakaocoupon.coupon.dto.CouponResult;
import com.yong.kakaocoupon.coupon.entity.Coupon;
import com.yong.kakaocoupon.coupon.exception.CouponException;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class CouponController {
	private CouponService couponService;

	@GetMapping("/coupons")
	public List<Coupon> coupons(
		@RequestParam int amount,
		@RequestParam(required = false)
		@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dateTime) {
		return couponService.getUsableSortedCoupons(amount, ifNullNow(dateTime));
	}

	@PostMapping("/coupons/{couponId}")
	public CouponResult use(
		@PathVariable Integer couponId,
		@RequestParam int amount,
		@RequestParam(required = false)
		@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dateTime) {
		return couponService.use(couponId, amount, ifNullNow(dateTime));
	}

	private LocalDateTime ifNullNow(LocalDateTime dateTime) {
		return dateTime == null ? LocalDateTime.now() : dateTime;
	}

	@ExceptionHandler(CouponException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String exceptionHandler(CouponException e) {
		return e.getMessage();
	}
}
