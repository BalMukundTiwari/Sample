package com.cms.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cms.model.Associate;

//@Service
@FeignClient(name="AssociateService", url="http://localhost:9092")
public interface ServiceProxy {
	
	@GetMapping("/associate/viewByAssociateId/{associateId}")
	public ResponseEntity<Associate> viewByAssociateId(@PathVariable String associateId);
}