package com.springlab.app;

public interface EmailService {

	public EmailResponseEntity sendTransactMail(EmailEntity email);
	
	public EmailResponseEntity sendNonTransactMail(EmailEntity email);
}
