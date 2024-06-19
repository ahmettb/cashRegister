package com.cashregister.product.exception;

public class StockNotEnough extends RuntimeException{

    public StockNotEnough(String msg) {

        super(msg);
    }
}
