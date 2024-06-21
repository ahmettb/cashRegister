package com.cashregister.sale.feignclient;


import com.cashregister.sale.config.FeignConif;
import com.cashregister.sale.model.UserInfo;
import feign.Headers;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "authorization",url = "http://localhost:8500",path = "/api/v6/auth",configuration = FeignConif.class)
public interface AuthClient {


    @GetMapping("get-current-user")
     ResponseEntity<UserInfo> getUserInfo(@RequestHeader("Authorization")String token );
}
