package com.cashregister.sale.service;

import com.cashregister.sale.model.Campaign;

import java.util.List;

public interface ICampaignService {


    void addCampaign(Campaign campaign);

    List<Campaign> getCampaigns();
}
