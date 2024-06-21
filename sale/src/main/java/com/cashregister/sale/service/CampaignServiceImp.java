package com.cashregister.sale.service;

import com.cashregister.sale.model.Campaign;
import com.cashregister.sale.repository.ICampaignRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class CampaignServiceImp implements ICampaignService{

    final ICampaignRepository campaignRepository;

    @Override
    public void addCampaign(Campaign campaign) {


        Campaign campaign1=new Campaign();
        campaign1.setCampaignName(campaign1.getCampaignName());
        campaignRepository.save(campaign1);
    }

    @Override
    public List<Campaign> getCampaigns() {

        return  campaignRepository.findAll();


    }
}
