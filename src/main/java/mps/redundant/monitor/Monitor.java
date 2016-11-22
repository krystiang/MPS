package mps.redundant.monitor;

import mps.redundant.Config;
import mps.redundant.dispatcher.Dispatcher;
import mps.redundant.gui.MonitorGUI;
import mps.redundant.server.IMpsServer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Der Monitor ueberwacht den Zustand der MPS Server und teilt diese dem
 * Dispatcher und der GUI mit.
 */
public class Monitor implements IMonitor {
	public Dispatcher dispatcher;
	public HashMap<String, Timer> aliveTimer;
	public Registry hawmps1reg;
	public Registry hawmps2reg;
	private static Monitor monitor;

	/**
	 * @param dispatcher
	 * 
	 *            Konstruktor des Monitors: setzt eine Referenz fuer den
	 *            dazugehoerigen Dispatcher und erzeugt eine HashmMap mit den
	 *            Servernamen und ihren entsprechenden Timern, die anzeigen wie
	 *            lange sie sich nicht mehr zurueckgemeldet haben
	 */
	private Monitor(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
		this.aliveTimer = new HashMap<String, Timer>();
	}

	/**
	 * @param dispatcher
	 * @return
	 * @throws RemoteException
	 * 
	 *             Erzeugt ein Monitor Objekt und exportiert es nach außen um
	 *             eingehende Aufrufe wahrnehmen zu koennen. Außerdem wird es an
	 *             die in der Config festgelegte Referenz gebunden.
	 */
	public static Monitor create(Dispatcher dispatcher) throws RemoteException {
		monitor = new Monitor(dispatcher);
		IMonitor stub = (IMonitor) UnicastRemoteObject.exportObject(monitor, 0);
		dispatcher.dispatcherRegistry.rebind(Config.MONITOR_NAME, stub);
		return monitor;
	}

	/**
	 * @throws RemoteException
	 *             Setzt auf die entsprechende Referenz die Registry des
	 *             jeweiligen MpsServers.
	 */
	public void getMpsServerRegistry(String serverName, String host, int port)
			throws RemoteException {
		if (serverName.equals(Config.HAWMPS1_NAME)) {
			hawmps1reg = LocateRegistry.getRegistry(host, port);
		} else {
			hawmps2reg = LocateRegistry.getRegistry(host, port);
		}
	}

	/**
	 * @throws RemoteException
	 *             | NotBoundException Wird von Heartbeat aufgerufen um zu
	 *             signalisieren, dass der entsprechende Server unter dem
	 *             Servernamen noch lebt. Daraufhin wird der Timer, wie lange
	 *             sich ein Server nicht mehr gemeldet hat, wieder reseted und
	 *             der Dispatcher mit seiner alive() Methode informiert.
	 */
	@Override
	public void alive(String serverName) {
		System.out.println("HeartBeat empfangen: " + serverName);
		if (aliveTimer.containsKey(serverName)) {
			aliveTimer.get(serverName).cancel();
			aliveTimer.remove(serverName);
		}
		aliveTimer.put(serverName, startTimer(serverName));
		changeZustand(serverName, MonitorGUI.Zustand.online);

		try {
			if (serverName.equals(Config.HAWMPS1_NAME)) {
				dispatcher.alive((IMpsServer) hawmps1reg.lookup(serverName));
			} else {
				dispatcher.alive((IMpsServer) hawmps2reg.lookup(serverName));
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param serverName
	 * 
	 *            Wird aufgerufen wenn der Timer eines Servers
	 *            MONITOR_SERVERTIMEOUT Grenze ueberschreitet. Der Dispatcher
	 *            wird ueber seine notAlive() Methode darueber informiert. In
	 *            der GUI wird der Zustand auf Offline gesetzt und der Eintrag
	 *            in der aliveTimer HashMap wird entfernt.
	 */
	public void notAlive(String serverName) {
		dispatcher.notAlive(serverName);
		aliveTimer.remove(serverName);
		changeZustand(serverName, MonitorGUI.Zustand.offline);
	}

	private Timer startTimer(final String serverName) {
		final Monitor monitor = this;
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				monitor.notAlive(serverName);
			}
		}, Config.MONITOR_SERVERTIMEOUT);
		return timer;
	}

	/**
	 * @param serverName
	 * @param zustand
	 * 
	 *            Aendert den Zustand des jeweiligen Servers in der MonitorGUI.
	 */
	private void changeZustand(String serverName, MonitorGUI.Zustand zustand) {
		IMpsServer derServer = null;
		try {
			if (dispatcher.serverList.containsKey(serverName)) {
				derServer = dispatcher.serverList.get(serverName);
			}

			if ((derServer != null && !derServer.isDeaktiviert())
					|| zustand.equals(MonitorGUI.Zustand.offline)) {
				if (serverName.equals(Config.HAWMPS1_NAME)) {
					MonitorGUI.getInstance().changeZustandMPS1(zustand);
				} else if (serverName.equals(Config.HAWMPS2_NAME)) {
					MonitorGUI.getInstance().changeZustandMPS2(zustand);
				} else {
					throw new UnknownError("Unbekannter Servername: "
							+ serverName);
				}
			}

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}