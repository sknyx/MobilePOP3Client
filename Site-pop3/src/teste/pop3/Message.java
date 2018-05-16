package teste.pop3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Message extends POP3Client {

	private final Map<String, List<String>> headers;

	private final String body;

	protected Message(Map<String, List<String>> headers, String body) {
		this.headers = Collections.unmodifiableMap(headers);
		this.body = body;
	}

	public Map<String, List<String>> getHeaders() {
		return headers;
	}

	public String getBody() {
		return body;
	}

	protected Message getMessage(int i) throws IOException {
		String response = sendCommand("RETR " + i);
		Map<String, List<String>> headers = new HashMap<String, List<String>>();
		String headerName = null;
		// process headers
		while ((response = readResponseLine()).length() != 0) {
			if (response.startsWith("\t")) {
				continue; // no process of multiline headers
			}
			int colonPosition = response.indexOf(":");
			headerName = response.substring(0, colonPosition);
			String headerValue;
			if (headerName.length() > colonPosition) {
				headerValue = response.substring(colonPosition + 2);
			} else {
				headerValue = "";
			}
			List<String> headerValues = headers.get(headerName);
			if (headerValues == null) {
				headerValues = new ArrayList<String>();
				headers.put(headerName, headerValues);
			}
			headerValues.add(headerValue);
		}
		// process body
		StringBuilder bodyBuilder = new StringBuilder();
		while (!(response = readResponseLine()).equals(".")) {
			bodyBuilder.append(response + "\n");
		}
		return new Message(headers, bodyBuilder.toString());
	}

	public int getNumberOfNewMessages() throws IOException {
		String response = sendCommand("STAT");
		String[] values = response.split(" ");
		return Integer.parseInt(values[1]);
	}

	public List<Message> getMessages() throws IOException {
		int numOfMessages = getNumberOfNewMessages();
		List<Message> messageList = new ArrayList<Message>();
		for (int i = 1; i <= numOfMessages; i++) {
			messageList.add(getMessage(i));
		}
		return messageList;
	}
}