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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.model.Associate;
import com.cms.service.IAssociateService;

@RestController
@RequestMapping("/associate")
public class AssociateController {

	@Autowired
	private IAssociateService associateService;

	public AssociateController(IAssociateService associateService) {
		this.associateService = associateService;
	}
	@PostMapping("/addAssociate")
	public ResponseEntity<Associate> addAssociate(@RequestBody Associate associate) {
		try {
			Associate addedAssociate = associateService.addAssociate(associate);
			return ResponseEntity.ok(addedAssociate);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PutMapping("/updateAssociate/{associateId}/{associateAddress}")
	public ResponseEntity<Associate> updateAssociate(@PathVariable String associateId,@PathVariable String associateAddress) {
		try {
			Associate updatedAssociate = associateService.updateAssociate(associateId, associateAddress);
			return ResponseEntity.ok(updatedAssociate);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping("/viewByAssociateId/{associateId}")
	public ResponseEntity<Associate> viewByAssociateId(@PathVariable String associateId) {
		try {
			Associate associate = associateService.viewByAssociateId(associateId);
			return ResponseEntity.ok(associate);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping("/viewAll")
    public ResponseEntity<List<Associate>> viewAll() {
        try {
            List<Associate> associates = associateService.viewAll();
            return ResponseEntity.ok(associates);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}