package info.campersites.service;

import java.util.Locale;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired 
    private JavaMailSender mailSender;
    @Autowired 
    private SpringTemplateEngine templateEngine;
    
    public void sendActivation(final String recipientName, final String recipientEmail, final String activationCode, final Locale locale) {
    	final Context ctx = new Context(locale);
    	ctx.setVariable("nickname", recipientName.toUpperCase());
        ctx.setVariable("activationCode", activationCode);
        String language = "en";
        if ("it".equals(locale.getLanguage()) ||
        	"fr".equals(locale.getLanguage())) {
        	language = locale.getLanguage();
        } 
        
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        final String htmlContent = this.templateEngine.process(language + "/email-activation.html", ctx);
        try {
            message.setSubject("CamperSites.info");
			message.setFrom("camperstop@camperstop.info");
	        message.setTo(recipientEmail);
	        message.setText(htmlContent, true /* isHtml */);
	        this.mailSender.send(mimeMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public void sendRestorePwd(final String recipientName, final String recipientEmail, final String restoreCode, final Locale locale) {
    	final Context ctx = new Context(locale);
    	ctx.setVariable("nickname", recipientName.toUpperCase());
        ctx.setVariable("restoreCode", restoreCode);
        String language = "en";
        if ("it".equals(locale.getLanguage()) ||
           	"fr".equals(locale.getLanguage())) {
           	language = locale.getLanguage();
        } 
        
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        final String htmlContent = this.templateEngine.process(language + "/email-restore-pwd.html", ctx);
        try {
            message.setSubject("CamperSites.info");
			message.setFrom("camperstop@camperstop.info");
	        message.setTo(recipientEmail);
	        message.setText(htmlContent, true /* isHtml */);
	        this.mailSender.send(mimeMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public void sendContatto(final String recipientName, final String recipientEmail, final String messageBody) {
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        final String textContent = messageBody;
        try {
            message.setSubject("Contatto: " + recipientName.toUpperCase());
			message.setFrom(recipientEmail);
	        message.setTo("camperstop@camperstop.info");
	        message.setText(textContent, false /* isHtml */);
	        this.mailSender.send(mimeMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
}
