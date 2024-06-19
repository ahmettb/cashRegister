package com.cashregister.sale;

import com.cashregister.sale.error.ProductErrorDecoder;
import com.cashregister.sale.exception.GlobalExceptionHandler;
import feign.codec.ErrorDecoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableFeignClients
public class SaleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaleApplication.class, args);
	}


	@Bean
	public RestTemplate restTemplate() {

		return  new RestTemplate() ;
	}
}
