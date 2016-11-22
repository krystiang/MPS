package extern.hapsaaPayment;






import java.io.IOException;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class Send {

	private static final int ARGV_SIZE = 3;
	private final static String QUEUE_NAME = "hapsaaPayment";

	public static void main(String[] argv) throws IOException {

		
		if(argv.length == ARGV_SIZE) {
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(argv[2]);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		String message = argv[0] + " " + argv[1];
		
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
		System.out.println(" [x] Sent '" + message + "'");
		

		channel.close();
		connection.close();
		}
		else {
			System.out.println("USAGE: java -jar Send.jar <rechnungsnr> <betrag> <host>");
		}
		
	}
}
