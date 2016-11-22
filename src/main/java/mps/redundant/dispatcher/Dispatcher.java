package mps.redundant.dispatcher;

import mps.redundant.Config;
import mps.redundant.gui.MonitorGUI;
import mps.redundant.monitor.Monitor;
import mps.redundant.server.IMpsServer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

/**
 * Der Dispatcher verteilt die Anfragen des Clients Round Robin an die
 * MPSServerprozesse.
 */
public class Dispatcher implements IDispatcher {

	public Registry dispatcherRegistry;
	public HashMap<String, IMpsServer> serverList;
	// Hilfsvariable fuer die Round Robin Aufgabenverteilung
	public int roundRobinCounter = 0;
	private static Dispatcher dispatcher;

	/**
	 * @param dispatcherPort
	 * @throws RemoteException
	 *             Konstruktor des Dispatchers, welcher eine neue Registry
	 *             erzeugt sowie eine HashMap fuer die MpsServer
	 */
	public Dispatcher(int dispatcherPort) throws RemoteException {
		dispatcherRegistry = LocateRegistry
				.createRegistry(dispatcherPort);
		this.serverList = new HashMap<String, IMpsServer>();
	}

	/**
	 * @param dispatcherPort
	 * @return
	 * @throws RemoteException
	 * 
	 *             Erzeugt ein Dispatcher Objekt und exportiert es nach außen um
	 *             eingehende Aufrufe wahrnehmen zu koennen. Außerdem wird es an
	 *             die in der Config festgelegte Referenz gebunden.
	 */
	public static Dispatcher create(int dispatcherPort) throws RemoteException {
		dispatcher = new Dispatcher(dispatcherPort);
		IDispatcher stub = (IDispatcher) UnicastRemoteObject.exportObject(
				dispatcher, 0);
		dispatcher.dispatcherRegistry.rebind(Config.DISPATCHER_NAME, stub);
		return dispatcher;
	}

	/**
	 * @return
	 * @throws RemoteException
	 *             | InterruptedException
	 * 
	 *             Gibt einen Server zurueck, indem er getNextActiveServer()
	 *             aufruft. Falls alle Server deaktiviert sind, wartet er und
	 *             versucht es im 5 Sekunden Takt immer wieder einen
	 *             erreichbaren Server zu bekommen.
	 */
	@Override
	public synchronized IMpsServer getRemoteServerInstance()
			throws RemoteException {
		IMpsServer server = null;
		try {
			server = getNextActiveServer();
			System.out.println("Server: " + server.getName()
					+ " uebernimmt die Anfrage.");
			while (server == null) {
				Thread.sleep(5000);
				server = getNextActiveServer();
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return server;
	}

	/**
	 * @return
	 * @throws RemoteException
	 *             Holt sich mit Round Robin den jeweils anderen Server wenn
	 *             beide aktiv sind sonst immer den selben. Erhoeht die
	 *             jeweilige Anfragenanzahl in der GUI.
	 */
	private IMpsServer getNextActiveServer() throws RemoteException {
		roundRobinCounter %= serverList.size();
		IMpsServer server;
		while (roundRobinCounter < serverList.size()) {
			ArrayList<IMpsServer> servers = new ArrayList<IMpsServer>(
					serverList.values());
			server = servers.get(roundRobinCounter);
			if (!server.isDeaktiviert()) {
				if (server.getName().equals(Config.HAWMPS1_NAME)) {
					MonitorGUI.getInstance().erhoeheAnfragenVonMPS1();
				}
				if (server.getName().equals(Config.HAWMPS2_NAME)) {
					MonitorGUI.getInstance().erhoeheAnfragenVonMPS2();
				}
				roundRobinCounter++;
				return server;
			}
			roundRobinCounter++;
			if (roundRobinCounter == serverList.size())
				roundRobinCounter = 0;
		}
		return null;
	}

	/**
	 * @param serverInstance
	 * @throws RemoteException
	 * 
	 *             Fuegt den lebendigen Server unter seinem Namen der HashMap
	 *             hinzu falls noch nicht enthalten.
	 */
	public synchronized void alive(IMpsServer serverInstance)
			throws RemoteException {
		if (!serverList.values().contains(serverInstance)) {
			serverList.put(serverInstance.getName(), serverInstance);
		}
	}

	/**
	 * @param serverName
	 * 
	 *            Entfernt den "toten" Server aus der HashMap
	 */
	public synchronized void notAlive(String serverName) {
		if (serverList.containsKey(serverName)) {
			serverList.remove(serverName);
		}
	}

	/**
	 * @param servername
	 * @param b
	 * 
	 *            Deaktiviert den Server in der HashMap (setzt Boolean auf true)
	 */
	public void deaktiviereServerInstanz(String servername, boolean b) {
		try {
			if (serverList.containsKey(servername)) {
				serverList.get(servername).setisDeaktiviert(b);
			}

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param servername
	 * @return
	 * 
	 *         Prueft ob ein Server in der HashMap ist, also ob dieser nicht
	 *         offline ist.
	 */
	public boolean isServerOnline(String servername) {

		if (serverList.containsKey(servername)) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) throws RemoteException,
			InterruptedException, NotBoundException {
		// if (System.getSecurityManager() == null) {
		// System.setSecurityManager(new SecurityManager());
		// }
		// try {
		if (args.length == 1) {

			Dispatcher dispatcher = Dispatcher
					.create(Integer.parseInt(args[0]));
			Monitor.create(dispatcher);

			JFrame frame = new JFrame("MonitorGUI");
			MonitorGUI x = new MonitorGUI(frame, dispatcher);
			frame.setContentPane(x.monitorGUI);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(false);
			frame.setVisible(true);

		} else
			System.err.println("please specify your port as parameters");

		// } catch (Exception e) {
		// System.err.println("Dispatcher exception:");
		// e.printStackTrace();
		// }
	}

}