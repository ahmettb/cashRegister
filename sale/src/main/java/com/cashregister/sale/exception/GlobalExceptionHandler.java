package com.cashregister.sale.exception;

import com.cashregister.sale.error.ExceptionMessage;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NotFoundProduct.class})
    public ResponseEntity<ExceptionMessage> handleProductNotFoundException(NotFoundProduct exception, HttpServletRequest request) {
        String path = request.getRequestURI();
        log.error("Product not found: {}", path);

        ExceptionMessage exceptionMessage = new ExceptionMessage(
                "NOT FOUND",
                HttpStatus.NOT_FOUND.value(),
                new Date().toString(),
                exception.getMessage(),
                path
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionMessage);
    }

    @ExceptionHandler({StockNotEnough.class})
    public ResponseEntity<ExceptionMessage> handleStockNotEnoughException(StockNotEnough exception, HttpServletRequest request) {
        String path = request.getRequestURI();
        log.debug("Stock not enough: {}", path);

        ExceptionMessage exceptionMessage = new ExceptionMessage(
                "STOCK NOT ENOUGH",
                HttpStatus.BAD_REQUEST.value(),
                new Date().toString(),
                exception.getMessage(),
                path
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionMessage);
    }

    @ExceptionHandler({SaleListNotFound.class})
    public ResponseEntity<ExceptionMessage> handleSaleListNotFoundException(SaleListNotFound exception, HttpServletRequest request) {
        String path = request.getRequestURI();
        log.debug("Sale list not found: {}", path);

        ExceptionMessage exceptionMessage = new ExceptionMessage(
                "NOT FOUND",
                HttpStatus.NOT_FOUND.value(),
                new Date().toString(),
                exception.getMessage(),
                path
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionMessage);
    }
}
