package com.cashregister.product.exception;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler({ProductNotFound.class})
    public ResponseEntity<Object> handleProductNotFoundException(ProductNotFound exception) {
        logger.error("Product not found {}",exception.getMessage(),exception);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler({ProductAlreadyExist.class})
    public ResponseEntity<Object> handleProductAlreadyExistException(ProductAlreadyExist exception) {
        logger.error("Product already exist {}",exception.getMessage(),exception);

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

        {
            return ResponseEntity
                    .status(HttpStatus.ALREADY_REPORTED)
                    .body(exception.getMessage());

        }
    }

}
