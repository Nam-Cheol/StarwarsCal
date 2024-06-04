package starwarsCalender;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class StarwarsCalender extends JFrame {

	private JLabel backgroundMap;
	private TrayHandler trayHandler;
	
	private LocalDate today;
	private int dayIndex;

	// 패널 관리
	private JPanel calendar;
	private JPanel memo;
	
	// 패널 타이틀보드
	private TitledBorder titleboBorder;
	private String title = "Calendar";
	
	// 월을 그리는 라벨
	private JLabel month;

	// 월을 이동시키는 버튼
	private JButton beforeBtn;
	private JButton afterBtn;

	// 월 정보에 대한 이미지
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

	private ImageIcon monthImg[] = new ImageIcon[] {jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec};
	
	public StarwarsCalender() {
		initData();
		setInitLayout();
		addEventListener();
	}

	
	private void initData() {

		backgroundMap = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("starwarsBackground.png")));

//		setTitle("임찬님, Starwars에 오신 걸 환영합니다.");
//        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(backgroundMap);
		setSize(1200, 800);
		
		today = LocalDate.now();
		dayIndex = today.getMonthValue();
		
		calendar = new JPanel();
		memo = new JPanel();
		titleboBorder = new TitledBorder(new LineBorder(Color.yellow, 3), title);
		
		trayHandler = new TrayHandler(this); // 트레이 핸들러 초기화
		month = new JLabel(monthImg[dayIndex-1]);
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
		beforeBtn.setContentAreaFilled(false);
		add(beforeBtn);
		afterBtn.setSize(45, 50);
		afterBtn.setLocation(330, 20);
		afterBtn.setContentAreaFilled(false);
		add(afterBtn);
		
		calendar.setBackground(new Color(0,0,0,0));
		calendar.setSize(300, 400);
		calendar.setLocation(100, 100);
		calendar.setBorder(titleboBorder);
		add(calendar);
		
		memo.setBackground(new Color(0,0,0,0));
		memo.setSize(300, 400);
		memo.setLocation(500, 100);
		title = "Memo";
		memo.setBorder(titleboBorder);
		add(memo);
	}

	private void addEventListener() {
		// 트레이 최소화
//        addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                trayHandler.minimizeToTray();
//            }
//        });
		
		beforeBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(dayIndex != 1) {
					dayIndex -= 1;
					month.setIcon(monthImg[dayIndex-1]);
				}
			}
		});

		afterBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(dayIndex != 12) {
					dayIndex += 1;
					month.setIcon(monthImg[dayIndex-1]);
				}
			}
		});
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
