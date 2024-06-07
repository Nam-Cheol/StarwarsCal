package starwarsCalender;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class main {
	
	public static void main(String[] args) {
//		LocalDate today = LocalDate.now();
//		System.out.println(today);
//		
//		int [][] month = {{1,2}, {1,2,3}};
	
		File file = new File("Test.txt");
		try {
			if(file.exists()) {
				BufferedReader br = new BufferedReader(new FileReader("Test.txt"));
				System.out.println(br.readLine());
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter("Test.txt"));
			
			
			bw.write("안녕하세요");
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
