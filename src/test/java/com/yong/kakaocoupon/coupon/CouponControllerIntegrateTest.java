package com.yong.kakaocoupon.coupon;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
		mockMvc.perform(get("/coupons"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").isArray())
		.andExpect(jsonPath("$[0].id").value(1004))
		.andExpect(jsonPath("$[1].id").value(1001))
		.andExpect(jsonPath("$[2].id").value(1003))
		.andExpect(jsonPath("$[3].id").value(1002))
		.andExpect(jsonPath("$[4].id").value(1005))
		.andExpect(jsonPath("$[5].id").value(1006));
	}
}