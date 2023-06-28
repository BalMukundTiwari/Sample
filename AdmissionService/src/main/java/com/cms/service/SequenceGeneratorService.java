package com.cms.service;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

@Service
public class SequenceGeneratorService {

	private static final long INITIAL_SEQ = 300;
	private static final long INCREMENT_BY = 1;
	
	private AtomicLong sequence = new AtomicLong(INITIAL_SEQ);
    
	public long getNextAssociateId() {
		long nextSequence = sequence.getAndAdd(INCREMENT_BY);
		return nextSequence;
	}
}