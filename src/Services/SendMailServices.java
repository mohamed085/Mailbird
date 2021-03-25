package Services;

import Model.EmailAccountBean;
import Model.EmailAccountConstants;
import Model.EmailAccountFactory;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SendMailServices extends Service<Integer>{
	
	private int result;
	
	private EmailAccountBean emailAccountBean;
	private String subject;
	private String recipient;
	private String content;
	private List<File> attachments = new  ArrayList<File>();
	
	public SendMailServices(EmailAccountFactory emailAccountFactory, String subject, String recipient, String content, List<File> attachments) {
		this.emailAccountBean = emailAccountFactory.createEmailAccount();
		this.subject = subject;
		this.recipient = recipient;
		this.content = content;
		this.attachments = attachments;
	}
	
	@Override
	protected Task<Integer> createTask() {
		return new Task<Integer>(){
			@Override
			protected Integer call() throws Exception {				
				try {					
					//SetUp:
					Session session = emailAccountBean.getSession();
					MimeMessage message = new MimeMessage(session);
					message.setFrom(emailAccountBean.getEmailAddress());
					message.addRecipients(Message.RecipientType.TO, recipient);
					message.setSubject(subject);
					
					//Setting the content:
		    		Multipart multipart = new MimeMultipart();
		    		BodyPart messageBodyPart = new MimeBodyPart();
		    		messageBodyPart.setContent(content, "text/html");
		    		multipart.addBodyPart(messageBodyPart);
					
		    		//adding attachments:
					if(attachments.size() >0){
						for(File file: attachments){
		    				MimeBodyPart messageBodyPartAttach = new MimeBodyPart();
		    				DataSource source = new FileDataSource(file.getAbsolutePath());
		    				messageBodyPartAttach.setDataHandler(new DataHandler(source));
		    				messageBodyPartAttach.setFileName(file.getName());
		    				multipart.addBodyPart(messageBodyPartAttach);
						}
					}					
					message.setContent(multipart);
					
					//Sending the message:
		    		Transport transport = session.getTransport();
		            transport.connect(emailAccountBean.getProperties().getProperty("outgoingHost"),
		            				emailAccountBean.getEmailAddress(),
		            				emailAccountBean.getEmailPassword());
		            transport.sendMessage(message, message.getAllRecipients());
		            transport.close();
					
		            result = EmailAccountConstants.MESSAGE_SENT_OK;
				} catch (Exception e) {
					result = EmailAccountConstants.MESSAGE_SENT_ERROR;
					e.printStackTrace();
				}
				return result;
			}			
		};
	}

}
