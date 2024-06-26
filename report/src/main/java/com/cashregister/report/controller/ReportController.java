package com.cashregister.report.controller;


import com.cashregister.report.model.SalesInfoDto;
import com.cashregister.report.service.ReportService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/report")
@AllArgsConstructor
@Log4j2
public class ReportController {

final ReportService reportService;

    @GetMapping("get-sale-report/{id}")
    public ResponseEntity<SalesInfoDto>getSaleReport(@PathVariable long id)
    {

        log.info("ReportController: getSaleReport request received with id {} ", id);
        return  new ResponseEntity<>(reportService.getSaleInfo(id), HttpStatus.OK);

    }

    @GetMapping("create-pdf/{id}")
    public ResponseEntity<?>createPdf(@PathVariable long id) throws IOException {

        log.info("ReportController: createPdf request received with id {} ", id);
        reportService.createPdfFileSaleList(id);

        return  new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("get-all-sale")
    public ResponseEntity<Page<SalesInfoDto>>getAllSale(@RequestParam("pageSize")int pageSize, @RequestParam("pageNumber")int pageNumber) throws IOException {

        log.info("ReportController: getAllSale request received with pageNumber {} ,pageSize {}",pageNumber,pageSize);

        return  new ResponseEntity<>(reportService.getAllList(pageSize,pageNumber),HttpStatus.OK);

    }
}
