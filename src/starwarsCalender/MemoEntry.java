package starwarsCalender;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MemoEntry extends JPanel {
	private JLabel timeLabel;
	private JTextField memoField;

	private JButton saveButton;
	private JButton editButton;

	private Font font;

	private String time;
	private int day; // 일자를 저장할 변수 추가

	public MemoEntry(String time) {
		this.time = time;
		initData();
		setInitLayout();
		addEventListener();
	}

	private void initData() {
		setLayout(new FlowLayout());

		timeLabel = new JLabel(time);
		memoField = new JTextField(9);
		
		font = new Font("굴림", Font.BOLD, 10);

		saveButton = new JButton("저장");
		editButton = new JButton("수정");

	}

	private void setInitLayout() {

		saveButton.setSize(1, 10);
		saveButton.setFont(font);
		editButton.setFont(font);
		editButton.setEnabled(false);

		add(timeLabel);
		add(memoField);
		add(saveButton);
		add(editButton);
	}

	private void addEventListener() {
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				memoField.setEditable(false);
				saveButton.setEnabled(false);
				editButton.setEnabled(true);
			}
		});

		editButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				memoField.setEditable(true);
				saveButton.setEnabled(true);
				editButton.setEnabled(false);
			}
		});
	}

	public String getTime() {
		return timeLabel.getText();
	}

	public String getMemo() {
		return memoField.getText();
	}

	public void setMemo(String memo) {
		memoField.setText(memo);
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}
}
