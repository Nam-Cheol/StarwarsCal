package starwarsCalender;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MemoPanel extends JPanel {
    
    private int year;
    private int month;
    private int day;
    
    private String today; 
    
    private JLabel dateLabel; // 날짜를 표시할 라벨 추가
    
    private List<MemoEntry> memoEntries;
    private JPanel memoListPanel;
    private JScrollPane scrollPane;

    public MemoPanel(int year, int month, int day) {
    	this.year = year;
    	this.month = month;
    	this.day = day;
        initData();
        setInitLayout();
    }
    public MemoPanel() {
    	initData();
    	setInitLayout();
    }

    private void initData() {
        setLayout(new BorderLayout());
        memoEntries = new ArrayList<>();
        memoListPanel = new JPanel();
        memoListPanel.setLayout(new BoxLayout(memoListPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(memoListPanel);
        
        // 날짜를 표시할 라벨 초기화
        dateLabel = new JLabel(year + "년 " + month + "월 " + day + "일"); // 예시로 "2024-07-01"을 설정
        today = year + "-" + month + "-" + day;
    }

    private void setInitLayout() {
        memoListPanel.setOpaque(false);
        scrollPane.setOpaque(false);
        
        // BorderLayout.NORTH에 날짜 라벨 추가
        dateLabel.setFont(new Font("SansSerif", Font.BOLD, 16)); // 폰트 설정
        dateLabel.setForeground(Color.yellow);
        
        add(dateLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        
        for (int hour = 0; hour < 24; hour++) {
            String time = String.format("%02d:00", hour);
            MemoEntry memoEntry = new MemoEntry(time);
            memoEntries.add(memoEntry);
            memoListPanel.add(memoEntry);
        }
    }

    public List<MemoEntry> getMemoEntries() {
        return memoEntries;
    }
}
