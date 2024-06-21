package com.cashregister.sale.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "campaign")
public class Campaign {


   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long id;

   private String campaignName;






}
