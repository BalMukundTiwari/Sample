package com.cms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "associate")
@Getter
@Setter
@NoArgsConstructor
public class Associate {

	@Id
	private String associateId;
	private String associateName;
	private String associateAddress;
	private String associateEmailId;

	
}
