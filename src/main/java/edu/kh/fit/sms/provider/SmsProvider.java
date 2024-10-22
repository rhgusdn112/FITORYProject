package edu.kh.fit.sms.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Component
@PropertySource("classpath:/config.properties")
public class SmsProvider {
	
	private final DefaultMessageService messageService;
	
	@Value("${coolsms.senderNumber}")
	String sendNumber;
	
		
	public SmsProvider (
			@Value("${coolsms.api.key}") String API_KEY,
			@Value("${coolsms.api.secret}") String SECRET_KEY,
			@Value("${coolsms.domain}") String domain
			) {
		
		this.messageService = NurigoApp.INSTANCE.initialize(API_KEY, SECRET_KEY, domain);
	}
	
	public boolean sendSms(String to) {
		Message message = new Message();
		message.setFrom(sendNumber);
		message.setTo(to);
		message.setText("문자내용");
		
		SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));
		
		String statusCode = response.getStatusCode();
		boolean result = statusCode.equals("2000");
		
		return result;
	}
}
