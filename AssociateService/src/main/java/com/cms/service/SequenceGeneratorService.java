package com.cms.service;


public class SequenceGeneratorService {

private int currentSequenceNumber;
    
    public SequenceGeneratorService() {
        currentSequenceNumber = 100;
    }
    
    public String getNextAssociateId() {
         currentSequenceNumber++;
         String convertedCurrentSequenceNumber=String.valueOf(currentSequenceNumber);
         return convertedCurrentSequenceNumber;
    }
    
}