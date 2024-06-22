package com.cashregister.report.controller;


import com.cashregister.report.model.SalesInfoDto;
import com.cashregister.report.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api/v7/report")
@AllArgsConstructor

public class ReportController {

final ReportService reportService;

    @GetMapping("get-sale-report/{id}")
    public ResponseEntity<SalesInfoDto>getSaleReport(@PathVariable long id)
    {

        return  new ResponseEntity<>(reportService.getSale(id), HttpStatus.OK);

    }

    @GetMapping("create-pdf/{id}")
    public ResponseEntity<?>createPdf(@PathVariable long id) throws IOException {

        reportService.createPdfFileSaleList(id);
        return  new ResponseEntity<>(HttpStatus.OK);

    }
}
