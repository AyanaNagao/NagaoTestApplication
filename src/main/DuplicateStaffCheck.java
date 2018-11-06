package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DuplicateStaffCheck {

	private static final int NUM_STAF_CD = 0;
	private static final int NUM_BIRTH_DAY = 1;
	private static final int NUM_LAST_NM = 2;
	private static final int NUM_FIRST_NM = 3;
	private static final int NUM_LAST_NM_KN = 4;
	private static final int NUM_FIRST_NM_KN = 5;
	private static final int NUM_OLD_LAST_NM = 6;
	private static final int NUM_OLD_LAST_NM_KN = 7;
	private static final int NUM_TEL = 8;
	private static final int NUM_CARY_TEL = 9;
	private static final int NUM_MAIL1 = 10;
	private static final int NUM_MAIL2 = 11;
	private static final int NUM_ADDRESS = 12;

	public void doDuplicateCheck() throws IOException {
		try {
			File input = new File("input.csv");// 取り込みのCSVファイル
			File output = new File("output.csv");// 4点合致のスタッフ情報を出力するCSVファイル
			File output2 = new File("output2.csv");// 3点合致のスタッフ情報を出力するCSVファイル

			FileWriter fw = new FileWriter(output);
			BufferedWriter bw = new BufferedWriter(fw);
			FileWriter fw2 = new FileWriter(output2);
			BufferedWriter bw2 = new BufferedWriter(fw2);
			makeCsvHeader(bw);
			makeCsvHeader(bw2);

			BufferedReader br = new BufferedReader(new FileReader(input));
			int rowNum = 0;
			String line;
			while ((line = br.readLine()) != null) {// 95万件一気に処理したかったので、OOM対策として読みつつ比較する
				rowNum++;
				System.out.println("処理件数" + rowNum + "件目････");
				line = line.replace("\"", "");
				String[] datas = line.split(",", -1);
				File f2 = new File("input.csv");
				BufferedReader br2 = new BufferedReader(new FileReader(f2));
				String line2;
				int rowNum2 = 0;
				while ((line2 = br2.readLine()) != null) {
					rowNum2++;
					if (rowNum >= rowNum2)
						continue;
					line2 = line2.replace("\"", "");
					String[] datas2 = line2.split(",", -1);
					if (!datas[NUM_BIRTH_DAY].equals(datas2[NUM_BIRTH_DAY]))
						break;

					if (!datas[NUM_FIRST_NM_KN].equals(datas2[NUM_FIRST_NM_KN])
							&& !datas[NUM_FIRST_NM].equals(datas2[NUM_FIRST_NM]))
						break;

					if (!datas[NUM_LAST_NM_KN].equals(datas2[NUM_LAST_NM_KN])
							&& !datas[NUM_OLD_LAST_NM_KN].equals(datas2[NUM_LAST_NM_KN])
							&& !datas[NUM_LAST_NM_KN].equals(datas2[NUM_OLD_LAST_NM_KN])
							&& ((datas[NUM_OLD_LAST_NM_KN].equals("") && datas2[NUM_OLD_LAST_NM_KN].equals(""))
									|| !datas[NUM_OLD_LAST_NM_KN].equals(datas2[NUM_OLD_LAST_NM_KN]))

							&& !datas[NUM_LAST_NM].equals(datas2[NUM_LAST_NM])
							&& !datas[NUM_OLD_LAST_NM].equals(datas2[NUM_LAST_NM])
							&& !datas[NUM_LAST_NM].equals(datas2[NUM_OLD_LAST_NM])
							&& ((datas[NUM_OLD_LAST_NM].equals("") && datas2[NUM_OLD_LAST_NM].equals(""))
									|| !datas[NUM_OLD_LAST_NM].equals(datas2[NUM_OLD_LAST_NM])))
						continue;

					String dupColumn = "";// 合致しているカラム情報
					int equalCnt = 0; // 何個項目が合致しているのかのカウント
					if (isEqualColumn(datas[NUM_TEL], datas2[NUM_TEL], datas[NUM_CARY_TEL], datas2[NUM_CARY_TEL])) {
						dupColumn = dupColumn + "電話番号:";
						equalCnt++;
					}
					if (isEqualColumn(datas[NUM_MAIL1], datas2[NUM_MAIL1], datas[NUM_MAIL2], datas2[NUM_MAIL2])) {
						dupColumn = dupColumn + "メールアドレス:";
						equalCnt++;
					}
					if (datas[NUM_ADDRESS].equals(datas2[NUM_ADDRESS])) {
						dupColumn = dupColumn + "住所:";
						equalCnt++;
					}
					if (equalCnt == 0)
						continue;
					if (equalCnt == 1)
						makeCsvData(bw2, datas, datas2, dupColumn);
					if (equalCnt >= 2)
						makeCsvData(bw, datas, datas2, dupColumn);
					break;

				}
				br2.close();//再度1から読み直すため一旦クローズ
			}
			br.close();
			bw.close();
			bw2.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	private boolean isEqualColumn(String str1, String str2, String str3, String str4) {
		if (!str1.equals("")) {
			if (str1.equals(str2) || str1.equals(str4)) {
				return true;
			}
		} else if (!str2.equals("") && str2.equals(str3)) {
			return true;
		} else if (!str3.equals("") && str3.equals(str4)) {
			return true;
		}
		return false;
	}

	private void makeCsvHeader(BufferedWriter bw) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("スタッフコード1").append(",");
		sb.append("スタッフコード2").append(",");
		sb.append("姓かな1").append(",");
		sb.append("姓かな2").append(",");
		sb.append("名かな1").append(",");
		sb.append("名かな2").append(",");
		sb.append("旧姓かな1").append(",");
		sb.append("旧姓かな2").append(",");
		sb.append("姓1").append(",");
		sb.append("姓2").append(",");
		sb.append("名1").append(",");
		sb.append("名2").append(",");
		sb.append("旧姓1").append(",");
		sb.append("旧姓2").append(",");
		sb.append("生年月日1").append(",");
		sb.append("生年月日2").append(",");
		sb.append("メールアドレス1_1").append(",");
		sb.append("メールアドレス1_2").append(",");
		sb.append("メールアドレス2_1").append(",");
		sb.append("メールアドレス2_2").append(",");
		sb.append("電話番号1").append(",");
		sb.append("電話番号2").append(",");
		sb.append("携帯電話番号1").append(",");
		sb.append("携帯電話番号2").append(",");
		sb.append("住所1").append(",");
		sb.append("住所2").append(",");
		sb.append("合致項目");
		bw.write(sb.toString());
		bw.newLine();
	}

	private void makeCsvData(BufferedWriter bw, String[] datas, String[] datas2, String dupColumn) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append(datas[NUM_STAF_CD]).append(",");
		sb.append(datas2[NUM_STAF_CD]).append(",");
		sb.append(datas[NUM_FIRST_NM_KN]).append(",");
		sb.append(datas2[NUM_FIRST_NM_KN]).append(",");
		sb.append(datas[NUM_LAST_NM_KN]).append(",");
		sb.append(datas2[NUM_LAST_NM_KN]).append(",");
		sb.append(datas[NUM_OLD_LAST_NM_KN]).append(",");
		sb.append(datas2[NUM_OLD_LAST_NM_KN]).append(",");
		sb.append(datas[NUM_FIRST_NM]).append(",");
		sb.append(datas2[NUM_FIRST_NM]).append(",");
		sb.append(datas[NUM_LAST_NM]).append(",");
		sb.append(datas2[NUM_LAST_NM]).append(",");
		sb.append(datas[NUM_OLD_LAST_NM]).append(",");
		sb.append(datas2[NUM_OLD_LAST_NM]).append(",");
		sb.append(datas[NUM_BIRTH_DAY]).append(",");
		sb.append(datas2[NUM_BIRTH_DAY]).append(",");
		sb.append(datas[NUM_MAIL1]).append(",");
		sb.append(datas2[NUM_MAIL1]).append(",");
		sb.append(datas[NUM_MAIL2]).append(",");
		sb.append(datas2[NUM_MAIL2]).append(",");
		sb.append(datas[NUM_TEL]).append(",");
		sb.append(datas2[NUM_TEL]).append(",");
		sb.append(datas[NUM_CARY_TEL]).append(",");
		sb.append(datas2[NUM_CARY_TEL]).append(",");
		sb.append(datas[NUM_ADDRESS]).append(",");
		sb.append(datas2[NUM_ADDRESS]).append(",");
		sb.append(dupColumn);
		bw.write(sb.toString());
		System.out.println("□■同一人物の可能性が高いです：" + sb.toString());
		bw.newLine();
	}
}
