package splitwise;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		
		SplitwiseSystem wallet = new SplitwiseSystem();
		System.out.println("splitwise set up enter number of lines: ");
		Scanner scanner = new Scanner(System.in);
		int lines = scanner.nextInt();
		while(lines-- > 0) {
//			System.out.println(lines);
			int count = scanner.nextInt();
			String[] parts = new String[count];
			int idx = 0;
			while(count-- > 0) {
				parts[idx++] = (String)scanner.next();
			}
//			String inputString = scanner.next();
//			scanner.next()
			for(int i=0;i<parts.length;i++) {
				System.out.println(parts[i]);
			}
			System.out.println("Output: ");
			wallet.takeInput(parts);
		}
		
		scanner.close();
	}
}
