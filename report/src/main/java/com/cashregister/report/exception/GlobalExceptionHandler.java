package com.cashregister.report.exception;


import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler  extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);




        @ExceptionHandler({SaleListNotFound.class})
        public ResponseEntity<ExceptionMessage> handleSaleListNotFoundException(SaleListNotFound exception, ServletRequest request) {

            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String path=((HttpServletRequest) request).getRequestURI();

            ExceptionMessage exceptionMessage=new ExceptionMessage(
                    "NOT FOUND",
                    404,
                    new Date().toString(),
                    exception.getMessage(),
                    path
            );


            {


                return
                        ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionMessage);

            }
    }

}
