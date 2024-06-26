package com.cashRegister.gateway.routers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Routes {


    List<String> saleUrl = new ArrayList<>();


    List<String> userManagementUrl = new ArrayList<>();

    List<String> report = new ArrayList<>();


    public Map<String, List<String>> roleEndpoints = new HashMap<>();

    public Routes() {
        saleUrl.add("/api/sale/getSaleInfoById");
        saleUrl.add("/api/sale/addItemToCard");
        saleUrl.add("api/sale/create-sale");
        report.add("/api/report");
        userManagementUrl.add("/api/user-management");
        roleEndpoints.put("ROLE_KASIYER", saleUrl);
        roleEndpoints.put("ROLE_ADMIN", userManagementUrl);
        roleEndpoints.put("ROLE_MAGAZA_MUDUR", report);
    }

    public Map<String, List<String>> getRoleEndpoints() {
        return roleEndpoints;

    }
}

