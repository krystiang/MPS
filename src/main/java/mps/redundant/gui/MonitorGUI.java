package mps.redundant.gui;

import mps.redundant.Config;
import mps.redundant.dispatcher.Dispatcher;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MonitorGUI {
	public JPanel monitorGUI = new JPanel();
	private static JLabel mps1_anfragen = new JLabel("0");
	private static JLabel mps2_anfragen = new JLabel("0");
	private static JPanel mps2_panel = new JPanel();
	private static JPanel mps1_panel = new JPanel();
	private static JToggleButton mps1_btn = new JToggleButton();
	private static JToggleButton mps2_btn = new JToggleButton();
	private static JLabel mps1_lab_time = new JLabel("0");
	private static JLabel mps2_lab_time = new JLabel("0");
	private static JLabel mps1_time = new JLabel("0");
	private static JLabel mps2_time = new JLabel("0");
	@SuppressWarnings("unused")
	private static JFrame jFrame;
	private static MonitorGUI instance;
	private Zustand zustand_mps1;
	private Zustand zustand_mps2;
	private long mps1_timestamp;
	private long mps2_timestamp;
	@SuppressWarnings("unused")
	private Dispatcher dispatcher;

	public MonitorGUI(JFrame frame, Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
		instance = this;
		jFrame = frame;

		mps1_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!mps1_btn.isSelected()) {
					// TODO MPS1 Server in der roundrobben queue aktivieren
					mps1_btn.setText("Aktiviert");
					dispatcher.deaktiviereServerInstanz(Config.HAWMPS1_NAME,
							false);
					if (dispatcher.isServerOnline(Config.HAWMPS1_NAME))
						changeZustandMPS1(Zustand.online);

				} else {
					// TODO MPS1 Server in der roundrobben queue deaktivieren
					mps1_btn.setText("Deaktiviert");
					dispatcher.deaktiviereServerInstanz(Config.HAWMPS1_NAME,
							true);
					changeZustandMPS1(Zustand.deaktiviert);

				}
			}
		});
		mps2_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!mps2_btn.isSelected()) {
					// TODO MPS2 Server in der roundrobben queue aktivieren
					mps2_btn.setText("Aktiviert");
					dispatcher.deaktiviereServerInstanz(Config.HAWMPS2_NAME,
							false);
					if (dispatcher.isServerOnline(Config.HAWMPS2_NAME))
						changeZustandMPS2(Zustand.online);
				} else {
					// TODO MPS2 Server in der roundrobben queue deaktivieren
					mps2_btn.setText("Deaktiviert");
					dispatcher.deaktiviereServerInstanz(Config.HAWMPS2_NAME,
							true);
					changeZustandMPS2(Zustand.deaktiviert);
				}
			}
		});
		changeZustandMPS2(Zustand.offline);
		changeZustandMPS1(Zustand.offline);
		mps1_timestamp = System.currentTimeMillis();
		mps2_timestamp = System.currentTimeMillis();

		monitorGUI.setLayout(null);
		mps1_panel.setLayout(null);
		mps2_panel.setLayout(null);
		frame.setSize(205, 430);

		mps1_panel.setBounds(0, 0, 200, 200);
		mps2_panel.setBounds(0, 200, 200, 200);
		mps1_btn.setBounds(20, 20, 150, 20);
		mps2_btn.setBounds(20, 20, 150, 20);
		mps1_anfragen.setBounds(20, 40, 150, 20);
		mps1_lab_time.setBounds(20, 60, 150, 20);
		mps1_time.setBounds(20, 80, 150, 20);
		mps2_anfragen.setBounds(20, 40, 150, 20);
		mps2_lab_time.setBounds(20, 60, 150, 20);
		mps2_time.setBounds(20, 80, 150, 20);

		mps1_btn.setText("Deaktiviert");
		mps2_btn.setText("Deaktiviert");

		monitorGUI.add(mps1_panel);
		monitorGUI.add(mps2_panel);
		mps1_panel.add(mps1_btn);
		mps2_panel.add(mps2_btn);
		mps1_panel.add(mps1_anfragen);
		mps1_panel.add(mps1_lab_time);
		mps1_panel.add(mps1_time);
		mps2_panel.add(mps2_anfragen);
		mps2_panel.add(mps2_lab_time);
		mps2_panel.add(mps2_time);

		frame.setVisible(true);

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					changeZustandMPS2(zustand_mps2);
					changeZustandMPS1(zustand_mps1);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		}).start();
	}

	public static MonitorGUI getInstance() {
		return instance;
	}

	public enum Zustand {
		online, offline, deaktiviert;
	}

	private static final TitledBorder DEAKTIVIERT = new TitledBorder(
			new LineBorder(Color.YELLOW, 2), "deaktiviert");
	private static final TitledBorder ONLINE = new TitledBorder(new LineBorder(
			Color.GREEN, 2), "online");
	private static final TitledBorder OFFLINE = new TitledBorder(
			new LineBorder(Color.RED, 2), "offline");

	private void changeZustandPanel(Zustand zustand, JPanel panel) {
		TitledBorder border = null;
		switch (zustand) {
		case online:
			border = ONLINE;
			break;
		case offline:
			border = OFFLINE;
			break;
		case deaktiviert:
			border = DEAKTIVIERT;
			break;
		}
		panel.setBorder(border);
	}

	public synchronized void changeZustandMPS1(Zustand zustand) {
		if (!zustand.equals(zustand_mps1)) {
			zustand_mps1 = zustand;
			mps1_timestamp = System.currentTimeMillis();
		}
		switch (zustand) {
		case online:
			setMPS1Running(mps1_timestamp);
			mps1_btn.setText("Aktiviert");
			break;
		case offline:
			setMPS1Down(mps1_timestamp);
			mps1_btn.setText("Deaktiviert");
			break;
		case deaktiviert:
			setMPS1Deactiv(mps1_timestamp);
			mps1_btn.setText("Deaktiviert");
			break;
		}
		changeZustandPanel(zustand, mps1_panel);
	}

	public synchronized void changeZustandMPS2(Zustand zustand) {
		if (!zustand.equals(zustand_mps2)) {
			zustand_mps2 = zustand;
			mps2_timestamp = System.currentTimeMillis();
		}
		switch (zustand) {
		case online:
			setMPS2Running(mps2_timestamp);
			mps2_btn.setText("Aktiviert");
			break;
		case offline:
			setMPS2Down(mps2_timestamp);
			mps2_btn.setText("Deaktiviert");
			break;
		case deaktiviert:
			setMPS2Deactiv(mps2_timestamp);
			mps2_btn.setText("Deaktiviert");
			break;
		}
		changeZustandPanel(zustand, mps2_panel);
	}

	private void erhoeheAnfragen(JLabel label) {
		label.setText(String.valueOf((Integer.valueOf(label.getText()))
				.intValue() + 1));
	}

	public void erhoeheAnfragenVonMPS1() {
		erhoeheAnfragen(mps1_anfragen);
	}

	public void erhoeheAnfragenVonMPS2() {
		erhoeheAnfragen(mps2_anfragen);
	}

	private void setTimeInLabel(JLabel label_text, String updownString,
			JLabel label_time, long timestamp) {
		label_time.setText(String.valueOf((long) ((double) (System
				.currentTimeMillis() - timestamp) / 1000d) + " sec"));
		label_text.setText(updownString);
	}

	public void setMPS2Running(long timestamp) {
		setTimeInLabel(mps2_lab_time, "Online seit: ", mps2_time, timestamp);
	}

	public void setMPS2Down(long timestamp) {
		setTimeInLabel(mps2_lab_time, "Offline seit: ", mps2_time, timestamp);
	}

	public void setMPS1Down(long timestamp) {
		setTimeInLabel(mps1_lab_time, "Offline seit: ", mps1_time, timestamp);
	}

	public void setMPS1Running(long timestamp) {
		setTimeInLabel(mps1_lab_time, "Online seit: ", mps1_time, timestamp);
	}

	public void setMPS2Deactiv(long timestamp) {
		setTimeInLabel(mps2_lab_time, "Deaktiviert seit: ", mps2_time,
				timestamp);
	}

	public void setMPS1Deactiv(long timestam) {
		setTimeInLabel(mps1_lab_time, "Deaktiviert seit: ", mps1_time, timestam);
	}

}