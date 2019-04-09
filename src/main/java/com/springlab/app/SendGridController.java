package com.springlab.app;

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
			@RequestHeader(name="X-API-KEY") String apiKey,
			@RequestBody final EmailEntity emailEntity
			) {
		return sendGridService.sendTransactMail(emailEntity);
	}
	
	@PostMapping(path="v1/mail/send", consumes="application/json", produces="application/json")
	public EmailResponseEntity sendNonTransactMail(
			@RequestHeader(name="X-API-KEY") String apiKey,
			@RequestBody final EmailEntity emailEntity
			) {
		return sendGridService.sendNonTransactMail(emailEntity);
	}
}
