package com.example.paymentdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.example.paymentdemo.mapper")
@EnableTransactionManagement
public class PaymentDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentDemoApplication.class, args);
	}

}
