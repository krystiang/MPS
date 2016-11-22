package mps.redundant.server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import mps.redundant.Config;
import mps.redundant.monitor.IMonitor;

/**
 * Der Heartbeat gehoert jeweils zu einem MpsServer und sendet regelmaeßig ein
 * "I am Alive" Signal stellvertretend an den Monitor.
 */
public class Heartbeat extends Thread {
	private String serverName;
	private String dispatcherHost;
	private int dispatcherPort;

	public Heartbeat(String serverName, String dispatcherHost,
			int dispatcherPort) {
		this.serverName = serverName;
		this.dispatcherHost = dispatcherHost;
		this.dispatcherPort = dispatcherPort;
	}

	// Sendet in einem bestimmten Intervall fuer den Server einen Heartbeat an
	// den Monitor
	public void run() {
		while (!this.isInterrupted()) {
			try {
				Registry monitorRegistry = LocateRegistry.getRegistry(
						dispatcherHost, dispatcherPort);
				IMonitor monitor = (IMonitor) monitorRegistry
						.lookup(Config.MONITOR_NAME);
				monitor.alive(serverName);
				System.out.println("HeartBeat gesendet von " + serverName);
				sleep(Config.HEARTBEAT_INTERVALL);
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			} catch (NotBoundException e) {
				e.printStackTrace();
			}
		}
	}
}