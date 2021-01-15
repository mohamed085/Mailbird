package Model;

import javafx.beans.property.SimpleStringProperty;

import javax.mail.Message;
import javax.mail.internet.MimeBodyPart;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmailMessageBean extends AbstractTableItem{
	
	public static Map<String, Integer> formattedValues = new HashMap<String, Integer>();
	
	private SimpleStringProperty from;
	private SimpleStringProperty subject;
	private SimpleStringProperty date;
	private SimpleStringProperty size;
	private Message messageReference;

	//Handling Attachment
	private List<MimeBodyPart> attachmentsList = new ArrayList<MimeBodyPart>();
	private StringBuffer attachmentsNames = new StringBuffer();

	public EmailMessageBean(String subject, String from, String date, int size, boolean isRead, Message messageReference) {
		super(isRead);
		this.subject = new SimpleStringProperty(subject);
		this.from = new SimpleStringProperty(from);
		this.date = new SimpleStringProperty(date);
		this.size = new SimpleStringProperty(formatSize(size));
		this.messageReference = messageReference;
	}
	
	public String getFrom(){
		return from.get();
	}

	public String getSubject(){
		return subject.get();
	}

	public String getSize(){
		return size.get();
	}

	public String getDate(){
		return date.get();
	}

	public Message getMessageReference() {
		return messageReference;
	}

	private String formatSize(int size){
		String returnValue;
		if(size<= 0){
			returnValue =  "0";}
		
		else if(size<1024){
			returnValue = size + " B";
		}
		else if(size < 1048576){
			returnValue = size/1024 + " kB";
		}else{
			returnValue = size/1048576 + " MB";
		}
		formattedValues.put(returnValue, size);
		return returnValue;
		
	}

	@Override
	public String toString() {
		return "EmailMessageBean{" +
				"sender=" + from +
				", subject=" + subject +
				", size=" + size +
				'}';
	}
}
