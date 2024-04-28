package com.cashRegister.gateway.routers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Routes {

    List<String> productsUrl=new ArrayList<>();
    List<String> report=new ArrayList<>();

    public  Map<String, List<String >> roleEndpoints = new HashMap<>();

    public Routes() {
        productsUrl.add("/api/v1/product/getAll");
        roleEndpoints.put("ROLE_MODERATOR", productsUrl);

    }
}

