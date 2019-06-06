package com.yong.kakaocoupon;

import static java.util.stream.Collectors.*;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yong.kakaocoupon.coupon.CouponRepository;
import com.yong.kakaocoupon.coupon.entity.Coupon;

@SpringBootApplication
public class KakaoCouponApplication {

	public static void main(String[] args) {
		SpringApplication.run(KakaoCouponApplication.class, args);
	}

	@Bean
	CommandLineRunner initialize(CouponRepository couponRepository, ObjectMapper objectMapper) {
		return (String... args) -> {
			ClassPathResource classPathResource = new ClassPathResource("mock.json");

			if(classPathResource.exists()) {
				String json = getJson(classPathResource);

				List<Coupon> coupons = objectMapper.readValue(json, new TypeReference<List<Coupon>>() {
				});

				couponRepository.saveAll(coupons);
			}
		};
	}

	private String getJson(ClassPathResource classPathResource) throws IOException {
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(classPathResource.getInputStream()))) {
			return reader.lines().collect(joining());
		}
	}
}
