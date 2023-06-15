package com.cms.service;

import org.springframework.stereotype.Service;

@Service
public class SequenceGeneratorService {

private int currentSequenceNumber;
    
    public SequenceGeneratorService() {
        currentSequenceNumber = 100;
    }
    
    public String generateNextAssociateId() {
         currentSequenceNumber++;
         String convertedCurrentSequenceNumber=String.valueOf(currentSequenceNumber);
         return convertedCurrentSequenceNumber;
    }
    
}