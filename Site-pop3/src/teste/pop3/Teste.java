package teste.pop3;

import java.io.IOException;
import java.util.List;
import teste.pop3.Message;

public class Teste {

	public static void main(String[] args) throws IOException {
		POP3Client client = new POP3Client();
		client.setDebug(true);
		client.connect("pop3.myserver.com");
		client.login("name@myserver.com", "password");
		System.out.println("Number of new emails: " + client.getNumberOfNewMessages());
		List<Message> messages = client.getMessages();
		for (int index = 0; index < messages.size(); index++) {
		System.out.println("--- Message num. " + index + " ---");
		System.out.println(messages.get(index).getBody());
		}
		client.logout();
		client.disconnect();
		}
}
