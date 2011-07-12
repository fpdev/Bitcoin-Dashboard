package simsecondaire.bitcoindashboard.service.alert;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import simsecondaire.bitcoindashboard.service.LocalizationService;

public class MailAlertAction implements AlertAction {

	private static LocalizationService localizer = LocalizationService
			.getInstance();

	private String recipient;
	private String sender;
	private String serverAddress;
	private String protocol;
	private String username;
	private char[] password;

	/**
	 * @param recipient
	 * @param sender
	 * @param serverAddress
	 * @param protocol
	 * @param username
	 * @param password
	 */
	public MailAlertAction(String recipient, String sender, String serverAddress,
			String protocol, String username, char[] password) {
		super();
		this.recipient = recipient;
		this.sender = sender;
		this.serverAddress = serverAddress;
		this.protocol = protocol;
		this.username = username;
		this.password = password;
	}

	public void execute(AlertDescription description) throws AlertActionException {

		try {
			postMail(localizer.getStaticText("mailActionSubject",
					"Exchange Rate Alert"), description.toString());
		} catch (MessagingException e) {
			throw new AlertActionException("sending mail failed", e);
		}

	}

	private void postMail(String subject, String message)
			throws MessagingException {

		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", this.protocol);
		props.setProperty("mail.host", this.serverAddress);
		props.setProperty("mail.user", this.username);
		props.setProperty("mail.password", this.password.toString());

		Session session = Session.getDefaultInstance(props);
		Message msg = new MimeMessage(session);
		InternetAddress from = new InternetAddress(this.sender);
		msg.setFrom(from);
		InternetAddress to = new InternetAddress(recipient);
		msg.setRecipient(Message.RecipientType.TO, to);
		msg.setSubject(subject);
		msg.setContent(message, "text/plain");

		Transport.send(msg);

	}

}
