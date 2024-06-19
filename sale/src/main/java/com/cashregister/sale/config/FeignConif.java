package com.cashregister.sale.config;

import com.cashregister.sale.error.ProductErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class FeignConif {




        @Bean
        public ErrorDecoder errorDecoder() {
            return new ProductErrorDecoder();
        }


}
