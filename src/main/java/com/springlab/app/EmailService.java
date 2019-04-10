package com.springlab.app;

import java.util.HashMap;

public interface EmailService {

	public EmailResponseEntity sendTransactMail(EmailEntity email, HashMap<String, String> props);
	
	public EmailResponseEntity sendNonTransactMail(EmailEntity email, HashMap<String, String> props);
}
