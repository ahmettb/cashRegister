package com.cashregister.product.exception;


import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {


    @ExceptionHandler({ProductNotFound.class})
    public ResponseEntity<Object> handleProductNotFoundException(ProductNotFound exception) {
        log.error("Product not found ");
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler({CategoryNotFound.class})
    public ResponseEntity<Object> handleCategoryNotFoundException(ProductNotFound exception) {
        log.error("Category not found ");
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler({ProductAlreadyExist.class})
    public ResponseEntity<Object> handleProductAlreadyExistException(ProductAlreadyExist exception) {
        log.error("Product already exist ");
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }

    @ExceptionHandler({StockNotEnough.class})
    public ResponseEntity<Object> handleRuntimeException2(StockNotEnough exception) {
        log.error("Stock Not Enough");

        {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(exception.getMessage());

        }
    }

}
