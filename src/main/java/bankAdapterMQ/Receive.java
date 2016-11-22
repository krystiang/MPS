package bankAdapterMQ;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import mps.redundant.Config;
import mps.redundant.dispatcher.IDispatcher;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class Receive {

	private final static String QUEUE_NAME = "hapsaaPayment";

	public static IDispatcher getDispatcher(String dispatcherHost,
			int dispatcherPort) throws RemoteException, NotBoundException {
		Registry dispatcherRegistry = LocateRegistry.getRegistry(
				dispatcherHost, dispatcherPort);
		IDispatcher remoteDispatcher = (IDispatcher) dispatcherRegistry
				.lookup(Config.DISPATCHER_NAME);
		return remoteDispatcher;
	}

	public static void main(String[] args) throws Exception {

		if (args.length == 2) {
			IDispatcher remoteDispatcher = getDispatcher(args[0],
					Integer.parseInt(args[1]));

			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();

			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			System.out
					.println(" [*] Waiting for messages. To exit press CTRL+C");

			QueueingConsumer consumer = new QueueingConsumer(channel);
			channel.basicConsume(QUEUE_NAME, true, consumer);
			Long rechnungsnummer = 0L;
			int betrag = 0;

			while (true) {
				QueueingConsumer.Delivery delivery = consumer.nextDelivery();
				String message = new String(delivery.getBody());
				if (message.matches("^[0-9]{1,9} [0-9]{1,9}$")) {
					String[] s = message.split(" ");
					rechnungsnummer = Long.valueOf(s[0]);
					betrag = Integer.valueOf(s[1]);
					System.out.println(" [x] Received '" + "Rechnungsnummer: "
							+ rechnungsnummer + " Betrag: " + betrag + "'");
					remoteDispatcher.getRemoteServerInstance().zahlungsEingang(betrag, rechnungsnummer);
				} else {
					System.out.println("Fehlerhafte Eingabe. Bitte Format \"<rechnungsnr> <betrag>\" einhalten");
				}

			}
		} else
			System.err
					.println("please specify the ip adress and port of the server on which the dispatcher is running");

	}
}
