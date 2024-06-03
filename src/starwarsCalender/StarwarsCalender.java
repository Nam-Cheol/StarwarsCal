package starwarsCalender;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class StarwarsCalender extends JFrame {

	private JLabel backgroundMap;

	private TrayHandler trayHandler;

	// 월을 그리는 라벨
	private JLabel month;

	// 월을 이동시키는 버튼
	private JButton beforeBtn;
	private JButton afterBtn;

	// 월 정보에 대한 이미지
	private String monthImg[] = { "Jan.png", "Feb.png", "Mar.png", "Apr.png", "May.png", "Jun.png", "Jul.png",
			"Aug.png", "Sep.png", "Oct.png", "Nov.png", "Dec.png" };
	private ImageIcon jan = new ImageIcon(getClass().getClassLoader().getResource("Jan.png"));
	private ImageIcon feb = new ImageIcon(getClass().getClassLoader().getResource("Feb.png"));
	private ImageIcon mar = new ImageIcon(getClass().getClassLoader().getResource("Mar.png"));
	private ImageIcon apr = new ImageIcon(getClass().getClassLoader().getResource("Apr.png"));
	private ImageIcon may = new ImageIcon(getClass().getClassLoader().getResource("May.png"));
	private ImageIcon jun = new ImageIcon(getClass().getClassLoader().getResource("Jun.png"));
	private ImageIcon jul = new ImageIcon(getClass().getClassLoader().getResource("Jul.png"));
	private ImageIcon aug = new ImageIcon(getClass().getClassLoader().getResource("Aug.png"));
	private ImageIcon sep = new ImageIcon(getClass().getClassLoader().getResource("Sep.png"));
	private ImageIcon oct = new ImageIcon(getClass().getClassLoader().getResource("Oct.png"));
	private ImageIcon nov = new ImageIcon(getClass().getClassLoader().getResource("Nov.png"));
	private ImageIcon dec = new ImageIcon(getClass().getClassLoader().getResource("Dec.png"));

	public StarwarsCalender() {
		initData();
		setInitLayout();
		addEventListener();
	}

	private void initData() {

		backgroundMap = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("starwarsBackground.png")));

		setTitle("임찬님, Starwars에 오신 걸 환영합니다.");
//        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(backgroundMap);
		setSize(1200, 800);

		trayHandler = new TrayHandler(this); // 트레이 핸들러 초기화
		month = new JLabel(jan);
		beforeBtn = new JButton(new ImageIcon(getClass().getClassLoader().getResource("beforeBtn.png")));
		afterBtn = new JButton(new ImageIcon(getClass().getClassLoader().getResource("afterBtn.png")));
	}

	private void setInitLayout() {
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);

		month.setSize(230, 100);
		month.setLocation(100, 0);
		add(month);

		beforeBtn.setSize(45, 50);
		beforeBtn.setLocation(50, 20);
		beforeBtn.setBorderPainted(false);
		beforeBtn.setContentAreaFilled(false);
		add(beforeBtn);

		afterBtn.setSize(45, 50);
		afterBtn.setLocation(330, 20);
		afterBtn.setBorderPainted(false);
		afterBtn.setContentAreaFilled(false);
		add(afterBtn);
	}

	private void addEventListener() {
		// 트레이 최소화
//        addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                trayHandler.minimizeToTray();
//            }
//        });

	}

	public static void main(String[] args) {
		if (!FileLockHandler.lockInstance()) {
			JOptionPane.showMessageDialog(null, "이미 실행 중 입니다.");
			System.exit(0);
		} else {
			new StarwarsCalender();
		}
	}
}
