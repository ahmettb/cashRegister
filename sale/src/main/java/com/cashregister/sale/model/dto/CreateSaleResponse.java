package com.cashregister.sale.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSaleResponse {

private long saleId;
private String  cashierName;
private String cashierSurname;
private String infoMessage;

}
