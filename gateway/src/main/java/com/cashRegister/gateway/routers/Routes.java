package com.cashRegister.gateway.routers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public  class Routes {


    List<String> productsUrl=new ArrayList<>();
    List<String> saleUrl=new ArrayList<>();


    List<String> userManagementUrl=new ArrayList<>();

    List<String> report=new ArrayList<>();



    public  Map<String, List<String >> roleEndpoints = new HashMap<>();

    public Routes() {
        productsUrl.add("/api/v1/product/getAll");
        productsUrl.add("/api/v1/product/save");
        productsUrl.add("/api/v1/product/getProductById/{id}");
        saleUrl.add("/api/v3/sale/getSaleInfoById/");
        saleUrl.add("/api/v3/sale/addItemToCard/");

        roleEndpoints.put("ROLE_ADMIN", productsUrl);
        roleEndpoints.put("ROLE_KASIYER", saleUrl);

    }

    public  Map<String, List<String >> getRoleEndpoints() {
        return  roleEndpoints;

    }
}

