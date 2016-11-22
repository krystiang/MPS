package mps.redundant;

/**
 * Konstante Namen fuer jegliche Registryeintraege sowie den Zeitintervall,
 * indem sich ein Server als lebendig meldet und ab dem der Monitor einen Server
 * als "tot" erklaert
 */
public class Config {

	public static final String MONITOR_NAME = "monitor";
	public static final String DISPATCHER_NAME = "dispatcher";
	public static final String HAWMPS1_NAME = "hawmps1";
	public static final String HAWMPS2_NAME = "hawmps2";
	public static final int MONITOR_SERVERTIMEOUT = 10000;
	public static final int HEARTBEAT_INTERVALL = 4000;
}