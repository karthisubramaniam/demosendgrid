package com.springlab.app;

import org.springframework.stereotype.Service;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Personalization;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

@Service
public class SendGridServiceImpl implements EmailService {

	private final String EMAIL_SUCCESS = "Email has been sent successfully";
	private final String EMAIL_FAILURE = "Sorry, Unable to send Email. Please try again later.";
	
	private final String TEMPLATE_ID = "";
	private final String API_KEY = "";
	
	/**
	 * Sample Email Template
	 * Dear {{ RECIVER_NAME }}
		{{{ EMAIL_BODY }}}
		Regards
		{{ SENDER_NAME }}
	 */
	
	public EmailResponseEntity sendTransactMail(EmailEntity email) {		
		EmailResponseEntity emailResponseEntity = new EmailResponseEntity();
		Mail mail = new Mail();
		
		Email from = new Email(email.getEmailFrom(),email.getSenderName());
		Email to = new Email(email.getEmailTo(),email.getReceiverName());
		
		Personalization personalization = new Personalization();
		personalization.addTo(to);
		
		personalization.addDynamicTemplateData("EMAIL_SUBJECT", email.getEmailSubject());
		personalization.addDynamicTemplateData("RECIVER_NAME", email.getReceiverName());
		personalization.addDynamicTemplateData("SENDER_NAME", email.getSenderName());
		personalization.addDynamicTemplateData("EMAIL_BODY", email.getEmailBody());
		
		mail.setFrom(from);
		mail.setSubject(email.getEmailSubject());
		mail.addPersonalization(personalization);
		mail.setTemplateId(TEMPLATE_ID);
		
		try {
			SendGrid sendGrid = new SendGrid(API_KEY);
			Request request = new Request();			
			request.setEndpoint("mail/send");
			request.setMethod(Method.POST);
			request.setBody(mail.build());
			Response response = sendGrid.api(request);
			emailResponseEntity.setResponseCode(response.getStatusCode());
			emailResponseEntity.setResponseMsg(EMAIL_SUCCESS);
			
		} catch (Exception e) {
			e.printStackTrace();
			emailResponseEntity.setResponseCode(500);
			emailResponseEntity.setResponseMsg(EMAIL_FAILURE);
		}
		
		return emailResponseEntity;
	}
	
	public EmailResponseEntity sendNonTransactMail(EmailEntity email) {		
		EmailResponseEntity emailResponseEntity = new EmailResponseEntity();
		Mail mail = new Mail();
		
		Email from = new Email(email.getEmailFrom(),email.getSenderName());
		Email to = new Email(email.getEmailTo(),email.getReceiverName());
		
		Personalization personalization = new Personalization();
		personalization.addTo(to);
		
		mail.setFrom(from);
		mail.setReplyTo(to);
		mail.setSubject(email.getEmailSubject());
		mail.addContent(new Content("text/html", email.getEmailBody()));
		mail.addPersonalization(personalization);

		try {
			SendGrid sendGrid = new SendGrid(API_KEY);
			Request request = new Request();
			request.setEndpoint("mail/send");
			request.setMethod(Method.POST);
			request.setBody(mail.build());
			Response response = sendGrid.api(request);
			emailResponseEntity.setResponseCode(response.getStatusCode());
			emailResponseEntity.setResponseMsg(EMAIL_SUCCESS);
			
		} catch (Exception e) {
			e.printStackTrace();
			emailResponseEntity.setResponseCode(500);
			emailResponseEntity.setResponseMsg(EMAIL_FAILURE);
		}
		
		return emailResponseEntity;
	}	
}
