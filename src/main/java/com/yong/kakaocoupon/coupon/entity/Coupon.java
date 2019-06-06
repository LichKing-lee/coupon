package com.yong.kakaocoupon.coupon.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yong.kakaocoupon.coupon.enumclass.CouponStatus;
import lombok.Data;

@Data
@Entity
public class Coupon {
	@Id
	private Integer id;
	private int useMinAmount;
	private int discountAmount;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime usableFrom;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime usableUntil;
	private CouponStatus status;
}
