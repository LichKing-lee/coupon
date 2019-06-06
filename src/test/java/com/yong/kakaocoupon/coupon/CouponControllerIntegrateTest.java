package com.yong.kakaocoupon.coupon;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class CouponControllerIntegrateTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	void 쿠폰목록조회_만료일시가_가까운순_같으면_할인금액이_큰순() throws Exception {
		mockMvc.perform(get("/coupons")
			.param("amount", "50000")
			.param("dateTime", "2019-06-06 14:54:00"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$.length()").value(4))
			.andExpect(jsonPath("$[0].id").value(1001))
			.andExpect(jsonPath("$[0].status").value("NORMAL"))
			.andExpect(jsonPath("$[1].id").value(1003))
			.andExpect(jsonPath("$[1].status").value("NORMAL"))
			.andExpect(jsonPath("$[2].id").value(1002))
			.andExpect(jsonPath("$[2].status").value("NORMAL"))
			.andExpect(jsonPath("$[3].id").value(1005))
			.andExpect(jsonPath("$[3].status").value("NORMAL"));
	}

	@Test
	void 존재하지않는쿠폰_요청시_400_응답() throws Exception {
		mockMvc.perform(post("/coupons/{couponId}", 10)
			.param("amount", "50000")
			.param("dateTime", "2019-06-06 14:54:00"))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@Test
	void 사용할수없는_쿠폰_사용시_400_응답() throws Exception {
		mockMvc.perform(post("/coupons/{couponId}", 1004)
			.param("amount", "50000")
			.param("dateTime", "2019-06-06 14:54:00"))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@Test
	@Transactional
	void 쿠폰_사용() throws Exception {
		mockMvc.perform(post("/coupons/{couponId}", 1001)
			.param("amount", "50000")
			.param("dateTime", "2019-06-06 14:54:00"))
			.andDo(print())
			.andExpect(status().isOk())
		.andExpect(jsonPath("$.amount").value(50000))
		.andExpect(jsonPath("$.paymentAmount").value(47000))
		.andExpect(jsonPath("$.discountedAmount").value(3000));
	}
}