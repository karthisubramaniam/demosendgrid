package com.springlab.app;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendGridController {

	@Autowired
	SendGridServiceImpl sendGridService;
		
	@PostMapping(path="v1/mail/transact/send", consumes="application/json", produces="application/json")
	public EmailResponseEntity sendTransactMail(
			@RequestHeader(name="sg-api-key") String sgAPIKey,
			@RequestHeader(name="sg_tid") String sgTemplateId,
			@RequestBody final EmailEntity emailEntity
			) {
		HashMap<String, String> props = new HashMap<String, String>();
		props.put("prop.sgAPIKey", sgAPIKey);
		props.put("prop.sgTemplateId", sgTemplateId);
		return sendGridService.sendTransactMail(emailEntity, props);
	}
	
	@PostMapping(path="v1/mail/send", consumes="application/json", produces="application/json")
	public EmailResponseEntity sendNonTransactMail(
			@RequestHeader(name="sg-api-key") String sgAPIKey,
			@RequestBody final EmailEntity emailEntity
			) {
		HashMap<String, String> props = new HashMap<String, String>();
		props.put("prop.sgAPIKey", sgAPIKey);		
		return sendGridService.sendNonTransactMail(emailEntity, props);
	}
}
