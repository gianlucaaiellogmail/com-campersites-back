package info.campersites.service;

import java.util.Locale;

public interface EmailService {

	void sendActivation(final String recipientName, final String recipientEmail, final String activationCode, final Locale locale);
	void sendRestorePwd(final String recipientName, final String recipientEmail, final String restoreCode, final Locale locale);
	void sendContatto(final String recipientName, final String recipientEmail, final String messageBody);
	
}
