package starwarsCalender;

import java.awt.SystemTray;
import java.awt.TrayIcon;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class StarwarsCalender extends JFrame{

	private JLabel backgroundMap;
	private SystemTray tray;
	private TrayIcon icon;
	
	public StarwarsCalender() {
		initData();
		setInitLayout();
		addEventListener();
	}
	
	private void initData() {
		
		backgroundMap = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("icon.png")));
		
		setTitle("임찬님, Starwars에 오신 걸 환영합니다.");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(backgroundMap);
		setSize(1200, 800);
	}
	
	private void setInitLayout() {
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
//		add(backgroundMap);
	}
	
	private void addEventListener() {
		
	}

	public static void main(String[] args) {
		new StarwarsCalender();
	}
	
}
