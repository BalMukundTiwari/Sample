package com.cms.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cms.model.Associate;
import com.cms.service.IAssociateService;

@RestController
public class AssociateController {

	@Autowired
	private IAssociateService iAssociateService;
	
	public AssociateController(IAssociateService iAssociateService){
		this.iAssociateService = iAssociateService;
	}
	
	@PostMapping("/associate/addAssociate")
	public ResponseEntity<Associate> addAssociate(@RequestBody Associate associate)
	{
		try {
			Associate associateAdded=iAssociateService.addAssociate(associate);
			return ResponseEntity.ok(associateAdded);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@PutMapping("/associate/updateAssociate/{associateId}/{associateAddr}")
	public ResponseEntity<Associate>updateAssociate(@PathVariable String associateId,@PathVariable String associateAddr)
	{
		try {
			Associate updatedAssociate = iAssociateService.updateAssociate(associateId, associateAddr);
			return ResponseEntity.ok(updatedAssociate);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		}
		
		@GetMapping("/associate/viewByAssociateId/{associateId}")
		public ResponseEntity<Associate> viewByAssociateId(@PathVariable String associateId){
			try {
				Associate associate = iAssociateService.viewByAssociateId(associateId);
				return ResponseEntity.ok(associate);
		       }
			catch(Exception e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
	      }
		
		@GetMapping("/associate/viewAll")
		public ResponseEntity<List<Associate>> viewAll(){
	 try {
         List<Associate> associate = iAssociateService.viewAll();
         return ResponseEntity.ok(associate); 
     } catch (Exception e) {
         return ResponseEntity.notFound().build();
     }
		}
		
	
}





