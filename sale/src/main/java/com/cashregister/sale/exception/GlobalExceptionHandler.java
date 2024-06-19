package com.cashregister.sale.exception;



import com.cashregister.sale.error.ExceptionMessage;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Date;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler  extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({NotFoundProduct.class})
    public ResponseEntity<ExceptionMessage> handleProductNotFoundException(NotFoundProduct exception) {
    log.debug("handleProductNotFoundException {}",exception);

        return
                ResponseEntity.status(exception.getExceptionMessage().staus).body(exception.getExceptionMessage());
    }

    @ExceptionHandler({StockNotEnough.class})
    public ResponseEntity<ExceptionMessage> handleStockNotEnoughException(StockNotEnough exception) {

        {
            log.debug("handleStockNotEnoughException {}", exception);


            return
                    ResponseEntity.status(exception.getExceptionMessage().staus).body(exception.getExceptionMessage());

        }
    }

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
                log.debug("handleSaleListNotFoundException {}",exception);


                return
                        ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionMessage);

            }
    }

}
