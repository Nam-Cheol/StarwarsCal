package starwarsCalender;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

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

	int day;

	// 패널 관리
	private JPanel calendar;
	List<MemoPanel> memos;

	// 패널 타이틀보드
	private TitledBorder titleboBorder;

	// 월을 그리는 라벨
	private JLabel month;

	// 월을 이동시키는 버튼
	private JButton beforeBtn;
	private JButton afterBtn;

	private List<JButton> dayBtns = new ArrayList<>();

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

	private ImageIcon monthImg[] = new ImageIcon[] { jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec };

	public StarwarsCalender() {
		initData();
		setInitLayout();
		refreshCalendar();
		addEventListener();
	}

	private void initData() {

		backgroundMap = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("starwarsBackground.png")));

		setTitle("임찬님, Starwars에 오신 걸 환영합니다.");
//        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(backgroundMap);
		setSize(1200, 800);

		today = LocalDate.now();
		dayIndex = today.getMonthValue();

		calendar = new JPanel();
		titleboBorder = new TitledBorder(new LineBorder(Color.yellow, 3));

		trayHandler = new TrayHandler(this); // 트레이 핸들러 초기화
		month = new JLabel(monthImg[dayIndex - 1]);
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

		calendar.setLayout(new GridLayout(0, 7));
		calendar.setBackground(new Color(0, 0, 0, 0));
		calendar.setSize(800, 550);
		calendar.setLocation(50, 180);
		calendar.setBorder(titleboBorder);
		add(calendar);

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
				if (dayIndex != 1) {
					dayIndex -= 1;
					month.setIcon(monthImg[dayIndex - 1]);
					for (MemoPanel memo : memos) {
						remove(memo);
					}
					dayBtns.removeAll(dayBtns);
					refreshCalendar();
				}
			}
		});

		afterBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (dayIndex != 12) {
					dayIndex += 1;
					month.setIcon(monthImg[dayIndex - 1]);
					for (MemoPanel memo : memos) {
						remove(memo);
					}
					dayBtns.removeAll(dayBtns);
					refreshCalendar();
				}
			}
		});

	}

	private void refreshCalendar() {
		memos = new ArrayList<>();

		calendar.removeAll();
		calendar.setLayout(new GridLayout(0, 7, 10, 5));

		String[] daysOfWeek = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };

		for (String day : daysOfWeek) {
			JLabel dayLabel = new JLabel(day, JLabel.CENTER);
			dayLabel.setForeground(Color.yellow);

			if (day.equals("Sun")) {
				dayLabel.setForeground(Color.PINK);
			} else if (day.equals("Sat")) {
				dayLabel.setForeground(Color.CYAN);
			}

			dayLabel.setBorder(new LineBorder(Color.YELLOW, 1));
			dayLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
			calendar.add(dayLabel);
		}

		LocalDate firstDayOfMonth = LocalDate.of(today.getYear(), dayIndex, 1);
		int startDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue() % 7;

		for (int i = 0; i < startDayOfWeek; i++) {
			// 첫 날이 시작되는 날까지의 빈 칸 추가
			calendar.add(new JLabel());
		}

		YearMonth yearMonth = YearMonth.of(today.getYear(), dayIndex);
		int daysInMonth = yearMonth.lengthOfMonth();

		for (day = 1; day <= daysInMonth; day++) {
			JLabel dayLabel = new JLabel();
			JButton dayBtn = new JButton(day + "");
			dayBtn.setSize(50, 20);
			dayBtn.setLocation(2, 2);
			dayBtn.setBackground(new Color(163, 47, 47));
			dayBtn.setForeground(Color.yellow);
			dayBtns.add(dayBtn);
			dayLabel.add(dayBtn);
			dayLabel.setForeground(Color.yellow);
			dayLabel.setBorder(new LineBorder(Color.YELLOW, 1));
			calendar.add(dayLabel);

			MemoPanel memoPanel = new MemoPanel(today.getYear(), dayIndex, day);
			memos.add(memoPanel);
			dayBtn.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// 이전에 추가된 메모 패널을 제거
					for (MemoPanel memo : memos) {
						remove(memo);
					}

					// TODO 관리할 수 있도록 dayBtn의 배열이 필요
					int memoIndex = Integer.parseInt(dayBtn.getText()) - 1; // 메모 인덱스 계산
					MemoPanel memoPanel = memos.get(memoIndex); // 해당 날짜의 MemoPanel 가져오기
					memoPanel.setOpaque(false); // MemoPanel의 배경을 투명하게 설정
					memoPanel.setSize(300, 550); // MemoPanel의 크기 설정
					memoPanel.setLocation(860, 180); // MemoPanel의 위치 설정
					memoPanel.setBorder(titleboBorder); // MemoPanel의 테두리 설정

					// MemoPanel을 프레임에 추가
					add(memoPanel);

					// 프레임을 다시 그리도록 갱신
					revalidate();
					repaint();
					System.out.println(dayBtns.size());
				}
			});
		}
		revalidate();
		repaint();
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
