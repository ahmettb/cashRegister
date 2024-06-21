package com.cashregister.sale.controller;

import com.cashregister.sale.service.CampaignServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v3/campaign")
@AllArgsConstructor
public class CampaignController {


    final CampaignServiceImp campaignService;

   @GetMapping("get-campaigns")
    public ResponseEntity<?>getAllCampaigns()
    {
        return  new ResponseEntity<>(campaignService.getCampaigns(), HttpStatus.OK);


    }


}
