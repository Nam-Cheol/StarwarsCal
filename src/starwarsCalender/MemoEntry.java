package starwarsCalender;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MemoEntry extends JPanel {
	private JLabel timeLabel;
	private JTextField memoField;

	private JButton saveButton;
	private JButton editButton;

	private Font font;

	private MemoPanel mContext;

	private String time;
	private int day; // 일자를 저장할 변수 추가

	public MemoEntry(String time, MemoPanel mContext) {
		this.time = time;
		this.mContext = mContext;
		initData();
		setInitLayout();
		addEventListener();
		loadData();
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
		
		memoField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					memoField.setEditable(false);
					saveButton.setEnabled(false);
					editButton.setEnabled(true);
					saveData();
				}
			}
		});
		
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				memoField.setEditable(false);
				saveButton.setEnabled(false);
				editButton.setEnabled(true);
				saveData();
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

	public void saveData() {
	    Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    String filePath = mContext.getToday() + ".json";

	    JsonArray jsonArray;
	    try {
	        if (checkFile(filePath)) {
	            // 파일이 존재할 경우, 파일에서 JsonArray를 읽어옴
	            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	                StringBuilder sb = new StringBuilder();
	                String line;
	                while ((line = br.readLine()) != null) {
	                    sb.append(line);
	                }
	                JsonElement element = JsonParser.parseString(sb.toString());
	                jsonArray = element.getAsJsonArray();
	            }
	        } else {
	            // 파일이 존재하지 않을 경우, 새로운 JsonArray 생성
	            jsonArray = new JsonArray();
	        }

	        boolean timeExists = false;
	        // 현재 시간 정보와 비교
	        for (JsonElement jsonElement : jsonArray) {
	            JsonObject object = jsonElement.getAsJsonObject();
	            if (object.has("time") && object.get("time").getAsString().equals(this.time)) {
	                // 시간이 일치하면 텍스트 업데이트
	                object.addProperty("text", memoField.getText());
	                timeExists = true;
	                break;
	            }
	        }

	        if (!timeExists) {
	            // 시간이 일치하지 않으면 새로운 시간과 텍스트 추가
	            JsonObject newObject = new JsonObject();
	            newObject.addProperty("time", this.time);
	            newObject.addProperty("text", memoField.getText());
	            jsonArray.add(newObject);
	        }

	        // JsonArray를 파일에 기록
	        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
	            gson.toJson(jsonArray, bw);
	            bw.flush();
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}



	public void loadData() {
	    Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    String filePath = mContext.getToday() + ".json";

	    if (!checkFile(filePath)) {
	        // 파일이 존재하지 않으면 메서드를 종료
	        return;
	    }

	    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = br.readLine()) != null) {
	            sb.append(line);
	        }

	        JsonElement element = JsonParser.parseString(sb.toString());
	        if (element.isJsonArray()) {
	            JsonArray jsonArray = element.getAsJsonArray();
	            for (JsonElement jsonElement : jsonArray) {
	                JsonObject object = jsonElement.getAsJsonObject();
	                if (object.has("time") && object.get("time").getAsString().equals(this.time)) {
	                    if (object.has("text")) {
	                    	
	                    	if(object.get("text").getAsString().equals("")) {
	                    		break;
	                    	}
	                    	
	                        memoField.setText(object.get("text").getAsString());
	                        memoField.setEditable(false);
	                        saveButton.setEnabled(false);
	                        editButton.setEnabled(true);
	                        break;
	                    }
	                }
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void firstShift() {
        // time 변수가 "08:00"부터 "17:00" 사이인 경우 근무로 설정
        if (time.compareTo("08:00") >= 0 && time.compareTo("17:00") <= 0) {
            memoField.setText("근무"); // 근무로 텍스트 설정
            memoField.setEditable(false); // 수정 불가능하도록 설정
            saveButton.setEnabled(false); // 저장 버튼 비활성화
            editButton.setEnabled(true); // 수정 버튼 비활성화
            saveData(); // 데이터 저장
        }
    }
	
	public void middleShift() {
        // time 변수가 "11:00"부터 "20:00" 사이인 경우 근무로 설정
        if (time.compareTo("11:00") >= 0 && time.compareTo("20:00") <= 0) {
            memoField.setText("근무"); // 근무로 텍스트 설정
            memoField.setEditable(false); // 수정 불가능하도록 설정
            saveButton.setEnabled(false); // 저장 버튼 비활성화
            editButton.setEnabled(true); // 수정 버튼 비활성화
            saveData(); // 데이터 저장
        }
    }
	
	public void nightShift() {
        // time 변수가 "17:00"부터 "01:00" 사이인 경우 근무로 설정
        if (time.compareTo("17:00") >= 0 && time.compareTo("24:00") <= 0) {
            memoField.setText("근무"); // 근무로 텍스트 설정
            memoField.setEditable(false); // 수정 불가능하도록 설정
            saveButton.setEnabled(false); // 저장 버튼 비활성화
            editButton.setEnabled(true); // 수정 버튼 비활성화
            saveData(); // 데이터 저장
        }
    }
	
	public void shiftReset() {
		if(memoField.getText().equals("근무")) {
			memoField.setText(null);
			memoField.setEditable(true);
            saveButton.setEnabled(true);
            editButton.setEnabled(false);
            saveData();
		}
	}

	public JTextField getMemoField() {
		return memoField;
	}

	private boolean checkFile(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

	class Data {

		String time;
		String text;

		public Data(String time, String text) {
			super();
			this.time = time;
			this.text = text;
		}

	}
}
