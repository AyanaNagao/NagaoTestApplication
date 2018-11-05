import java.io.IOException;

import main.DuplicateStaffCheck;

public class Main {

	public static void main(String args[]) {
		DuplicateStaffCheck dp = new DuplicateStaffCheck();
		try {
			dp.doDuplicateCheck();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
