package mps.redundant.server;

import mps.redundant.Config;
import mps.redundant.monitor.IMonitor;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * MpsServer Prozess, welcher die eingehenden Anfragen ausfuehrt.
 */
public class MpsServer extends Fassade implements IMpsServer {
	private String serverName;
	@SuppressWarnings("unused")
	private Heartbeat heartBeat;
	private boolean isDeaktiviert = false;
	public Registry serverRegistry;
	private static MpsServer mpsServer;
	/**
	 * @param serverName
	 * @param heartBeat
	 * @param ownPort
	 * @throws RemoteException
	 * 
	 *             Konstruktor des MpsServer, welcher eine neue Registry fuer
	 *             den MpsServer erzeugt und sich selbst nach außen exportiert
	 *             um eingehende Aufrufe wahrnehmen zu koennen. Daraufhin wird
	 *             der MpsServer an die uebergebene Servernamen Referenz
	 *             gebunden.
	 */
	private MpsServer(String serverName, Heartbeat heartBeat, int ownPort)
			throws RemoteException {
		this.serverName = serverName;
		this.heartBeat = heartBeat;
		this.serverRegistry = LocateRegistry.createRegistry(ownPort);
		IMpsServer stub = (IMpsServer) UnicastRemoteObject
				.exportObject(this, 0);
		serverRegistry.rebind(serverName, stub);
	}

	/**
	 * @param serverName
	 * @param ownHost
	 * @param ownPort
	 * @param dispatcherHost
	 * @param dispatcherPort
	 * @return
	 * @throws RemoteException
	 * @throws NotBoundException
	 * 
	 *             Erzeugt ein MpsServer und entsprechendes Heartbeat Objekt,
	 *             holt sich die Registry des Dispatchers und daraufhin das
	 *             darin eingetragene Monitor Objekt. Ubergibt dem Monitor,
	 *             ueber die getMpsServerRegistry Methode, die relevanten Daten,
	 *             damit dieser den MpsServer erreichen kann.
	 */
	public static MpsServer create(String serverName, String ownHost,
			int ownPort, String dispatcherHost, int dispatcherPort)
			throws RemoteException, NotBoundException {
		Heartbeat heartBeat = new Heartbeat(serverName, dispatcherHost,
				dispatcherPort);
		mpsServer = new MpsServer(serverName, heartBeat, ownPort);

		Registry dispatcherRegistry = LocateRegistry.getRegistry(
				dispatcherHost, dispatcherPort);
		IMonitor monitor = (IMonitor) dispatcherRegistry
				.lookup(Config.MONITOR_NAME);
		monitor.getMpsServerRegistry(serverName, ownHost, ownPort);
		heartBeat.start();

		return mpsServer;
	}

	/**
	 * @return Gibt den Boolean zurueck welcher angibt ob der MpsServer
	 *         deaktiviert ist.
	 */
	@Override
	public boolean isDeaktiviert() {
		return isDeaktiviert;
	}

	/**
	 * Setzt den Boolean welcher angibt ob der MpsServer deaktiviert ist auf den
	 * uebergebenen Wert.
	 */
	@Override
	public void setisDeaktiviert(boolean b) {
		isDeaktiviert = b;
	}

	/**
	 * @return Gibt den Namen des MpsServers zurueck.
	 */
	@Override
	public String getName() throws RemoteException {
		return serverName;
	}

	public static void main(String[] args) throws RemoteException,
			NotBoundException, MalformedURLException {
		// if (System.getSecurityManager() == null) {
		// System.setSecurityManager(new SecurityManager());
		// }
		// try {
		if (args.length == 5) {
			if (args[0].equals(Config.HAWMPS1_NAME)) {
				MpsServer.create(Config.HAWMPS1_NAME, args[1],
						Integer.parseInt(args[2]), args[3],
						Integer.parseInt(args[4]));
			} else if (args[0].equals(Config.HAWMPS2_NAME)) {
				MpsServer.create(Config.HAWMPS2_NAME, args[1],
						Integer.parseInt(args[2]), args[3],
						Integer.parseInt(args[4]));
			}

			else
				System.err
						.println("choose \"hawmps1\" or \"hawmps2\" as the Servername");
		} else
			System.err
					.println("Parameters: Servername, Server Adress, Server Port,Dispatcher Adress, Dispatcher Port");
		// } catch (Exception e) {
		// System.err.println("Dispatcher exception:");
		// e.printStackTrace();
		// }

	}
}