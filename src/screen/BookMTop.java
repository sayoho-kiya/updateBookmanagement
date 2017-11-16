package screen;

import static property.Name.comnt1;
import static property.Name.comnt2;
import static property.Name.comnt3;
import static property.Name.comnt4;
import static property.Name.comnt5;
import static property.Name.comnt8;
import static property.Name.comnt9;
import static property.Name.msg1;
import static property.Name.msg2;
import static property.Name.msg3;
import static property.Name.msg4;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import action.DB;
import validate.Validate;

public class BookMTop implements ActionListener {
	JFrame[] fm = new JFrame[2];
	JPanel[] pn = new JPanel[2];
	JButton[] b = new JButton[11];
	JTextField[] tf = new JTextField[5];
	String[] bookInfo = new String[5];
	JLabel[] rg = new JLabel[7];
	JLabel shiftPID;
	JTextField getkeyword;
	JTable tbl;
	DefaultTableModel model;
	JComboBox<Object> combo, cor6;
	String[] header = { "PID", "タイトル", "出版社", "著者", "ジャンル", "評価", "エリア", "定価" };

	//図書管理メイン画面
	public void display_top() throws SQLException {
		UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Meiryo UI", Font.PLAIN, 20)));
		UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("Meiryo UI", Font.PLAIN, 16)));

		//フレーム（メイン・登録画面）
		for(int i = 0; i < 2; i++) {
			fm[i] = new JFrame();
			fm[i].setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			fm[i].setVisible(false);
		}
		//パネル
		for(int i = 0; i < 2; i++) {
			pn[i] = new JPanel();
			pn[i].setLayout(null);
			pn[i].setVisible(true);
		}

		fm[0].setBounds(0, 0, 1650, 1050);
		fm[0].setVisible(true);
		pn[0].setBounds(10, 10, 1600, 750);

		Container con = fm[0].getContentPane();
		con.add(pn[0]);

		DB.connect();
		List<String[]> bookinfo = DB.getbooklist();//書籍一覧を取得
		DB.close();
		//テーブルの作成
		model = new DefaultTableModel(bookinfo.toArray(new String[0][0]), header);
		tbl = new JTable(model);
		tbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//複数行選択できないように設定
		tbl.setBounds(10, 250, 1550, 600);
		tbl.setFont(new Font("Meiryo UI", Font.PLAIN, 20));
		tbl.setRowHeight(28);

		JTableHeader jh = tbl.getTableHeader();
		jh.setFont(new Font("Meiryo UI", Font.PLAIN, 18));
		tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		//列の幅を調整する
		int[] tblWidth = { 100, 400, 210, 210, 210, 190, 80, 125 };
		for(int i = 0; i < 8; i++) {
			tbl.getColumnModel().getColumn(i).setPreferredWidth(tblWidth[i]);
		}

		//右揃え(定価）
		DefaultTableCellRenderer rigth = new DefaultTableCellRenderer();
		rigth.setHorizontalAlignment(SwingConstants.RIGHT);
		tbl.getColumnModel().getColumn(7).setCellRenderer(rigth);

		//中央揃え（エリア）
		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(SwingConstants.CENTER);
		tbl.getColumnModel().getColumn(6).setCellRenderer(center);

		JScrollPane sp = new JScrollPane(tbl);
		sp.setBounds(30, 250, 1550, 600);
		pn[0].add(sp);

		String[] combodata = { "タイトル", "出版社", "著者", "ジャンル" };
		JComboBox<Object> jComboBox = new JComboBox<>(combodata);
		combo = jComboBox;
		combo.setBounds(30, 100, 200, 50);
		combo.setFont(new Font("Meiryo UI", Font.PLAIN, 20));

		getkeyword = new JTextField();
		getkeyword.setBounds(230, 100, 300, 50);
		getkeyword.setFont(new Font("Meiryo UI", Font.PLAIN, 20));

		pn[0].add(combo);
		pn[0].add(getkeyword);

		String[] btnName = { "TOP", "実行", "リセット", "新規登録", "編集", "削除", "【引用】新規登録", "登録", "登録", "登録", "取消" };
		for(int i = 0; i < 11; i++) {
			b[i] = new JButton();
			b[i].setText(btnName[i]);
			b[i].setFont(new Font("Meiryo UI", Font.PLAIN, 18));
			b[i].addActionListener(this);
		}
		for(int i = 0; i < 7; i++) {
			pn[0].add(b[i]);
		}

		b[0].setBounds(30, 10, 100, 40);
		b[1].setBounds(550, 100, 100, 40);
		b[2].setBounds(670, 100, 100, 40);
		b[3].setBounds(530, 900, 120, 40);
		b[4].setBounds(850, 900, 120, 40);
		b[5].setBounds(1000, 900, 120, 40);
		b[6].setBounds(680, 900, 140, 40);

		JLabel[] lb = new JLabel[3];
		String[] lbMsg = { msg2, msg3, msg4 };
		for(int i = 0; i < 3; i++) {
			lb[i] = new JLabel();
			lb[i].setText(lbMsg[i]);
			lb[i].setFont(new Font("Meiryo UI", Font.PLAIN, 18));
			pn[0].add(lb[i]);
		}

		lb[0].setBounds(30, 70, 600, 30);
		lb[1].setBounds(30, 190, 800, 20);
		lb[2].setBounds(30, 210, 800, 20);

	}

	//登録・編集画面
	public void openRegisterScreen() {
		fm[1].setVisible(true);
		fm[1].setBounds(50, 50, 650, 670);
		fm[1].add(pn[1]);

		JLabel[] lb = new JLabel[6];
		String[] labelName = { "タイトル：", "出版社：", "著者：", "ジャンル：", "定価：", "エリア：" };
		for(int i = 0; i < 6; i++) {
			lb[i] = new JLabel();
			lb[i].setText(labelName[i]);
			lb[i].setFont(new Font("Meiryo UI", Font.PLAIN, 25));
			pn[1].add(lb[i]);
		}

		lb[0].setBounds(15, 40, 100, 25);
		lb[1].setBounds(15, 120, 100, 25);
		lb[2].setBounds(15, 200, 100, 25);
		lb[3].setBounds(15, 280, 120, 25);
		lb[4].setBounds(15, 360, 100, 25);
		lb[5].setBounds(15, 440, 100, 25);

		for(int i = 0; i < 5; i++) {
			tf[i] = new JTextField();
			tf[i].setFont(new Font("Meiryo UI", Font.PLAIN, 20));
			pn[1].add(tf[i]);
		}
		tf[0].setBounds(15, 70, 400, 32);
		tf[1].setBounds(15, 150, 400, 32);
		tf[2].setBounds(15, 230, 400, 32);
		tf[3].setBounds(15, 310, 400, 32);
		tf[4].setBounds(15, 390, 400, 32);

		String[] rgMsg = { comnt1, comnt1, comnt1, comnt1, comnt2, comnt3, comnt4, comnt5, comnt5, comnt5 };

		JLabel[] rg = new JLabel[10];
		for(int i = 0; i < 10; i++) {
			rg[i] = new JLabel();
			rg[i].setText(rgMsg[i]);
			pn[1].add(rg[i]);
			rg[i].setFont(new Font("Meiryo UI", Font.PLAIN, 15));
		}
		rg[0].setBounds(115, 43, 400, 25);
		rg[1].setBounds(115, 123, 400, 25);
		rg[2].setBounds(115, 203, 400, 25);
		rg[3].setBounds(115, 283, 400, 25);
		rg[4].setBounds(115, 363, 400, 25);
		rg[5].setBounds(115, 443, 400, 25);
		rg[6].setBounds(435, 70, 400, 32);
		rg[7].setBounds(435, 150, 400, 32);
		rg[8].setBounds(435, 230, 400, 32);
		rg[9].setBounds(435, 310, 400, 32);

		JLabel labelr7 = new JLabel(); //編集時受け渡し用

		String[] arealist = { "A", "B", "C", "D", "E", "F", "G", "H" };
		cor6 = new JComboBox(arealist);
		cor6.setFont(new Font("Meiryo UI", Font.PLAIN, 20));
		cor6.setBounds(15, 470, 400, 32);

		b[11].setBounds(440, 550, 100, 40);
		b[11].setFont(new Font("Meiryo UI", Font.PLAIN, 20));

		pn[1].add(labelr7);
		pn[1].add(b[8]);
		pn[1].add(cor6);
	}

	public String[] registercheck() throws UnsupportedEncodingException {
		String registerinfo[] = null;
		//入力された値とバイト数の取得
		int[] byt = new int[5];
		for(int i = 0; i < 4; i++) {
			bookInfo[i] = tf[i].getText();
			byt[i] = bookInfo[i].getBytes("Shift-JIS").length;
		}

		//半角数字チェック
		Pattern p = Pattern.compile("[^0123456789]");
		Matcher m = p.matcher(bookInfo[4]);
		int[] mojicount = { 40, 20, 20, 20 };

		if(bookInfo[0].length() == 0 || bookInfo[1].length() == 0 || bookInfo[2].length() == 0 || bookInfo[3].length() == 0 || bookInfo[4].length() == 0) {
			Validate.Null();
		} else if(bookInfo[4].length() > 9) {
			Validate.numcheck();
		} else if(m.find()) {//半角数字チェック
			Validate.numcheck();
		} else if(byt[0] > 40 || byt[1] > 20 || byt[2] > 20 || byt[3] > 20) {
			Validate.cntover();
			for(int i = 0; i < 4; i++) {
				rg[i + 6].setForeground(Color.red);
				if(byt[i] > mojicount[i]) {
					rg[i].setText(byt[i] - mojicount[i] + comnt8);
				} else {
					rg[i].setText(comnt9);
				}
			}
		} else {
			registerinfo = new String[6];
			for(int i = 0; i < 5; i++) {
				registerinfo[i] = tf[i].getText();
			}
			registerinfo[5] = cor6.getSelectedItem().toString();//selectarea
		}
		return registerinfo;
	}

	public void setbookTable() throws SQLException {
		DB.connect();
		List<String[]> bookinfo = DB.getbooklist();
		DB.close();

		model.setRowCount(0);
		for(int i = 0; i < bookinfo.size(); i++) {
			model.addRow(bookinfo.get(i));
		}

	}

	public void reserch() throws SQLException {
		String keyword = getkeyword.getText();	//入力したキーワード
		String item = null;
		String selectItem = (String) combo.getSelectedItem(); //プルダウンから選択した項目
		if(selectItem.equals("タイトル")) {
			item = "title";
		} else if(selectItem.equals("出版社")) {
			item = "publisher";
		} else if(selectItem.equals("著者")) {
			item = "author";
		} else if(selectItem.equals("ジャンル")) {
			item = "genre";
		}
		DB.connect();
		List<String[]> bookreserchinfo = DB.reserchbook(keyword, item);
		DB.close();
		if(bookreserchinfo.isEmpty()) {
			JOptionPane.showMessageDialog(fm[1], "条件に一致する書籍はありません。");
		} else {
			model.setRowCount(0);
			for(int i = 0; i < bookreserchinfo.size(); i++) {
				model.addRow(bookreserchinfo.get(i));
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// ここにコードを挿入
		int row = tbl.getSelectedRow(); //選択された行

		if(e.getSource() == b[0]) {		//TOP
			fm[0].dispose();
			Top top = new Top();
			try {
				top.Top();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else if(e.getSource() == b[1]) {		//検索実行ボタン
			try {
				reserch();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else if(e.getSource() == b[2]) {		//検索リセットボタン
			try {
				setbookTable();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			getkeyword.setText("");

		} else if(e.getSource() == b[3]) {	//新規登録ボタン
			openRegisterScreen();
			b[9].setBounds(320, 550, 100, 40);
			pn[1].add(b[8]);

		} else if(e.getSource() == b[4]) {	//引用ー新規登録ボタン
			row = tbl.getSelectedRow();
			if(row == -1) {		//どのセルも選択されていない場合のエラー処理
				Validate.noSelect();
			} else {
				String[] selectBook = new String[6];
				for(int i = 1; i < 6; i++) {
					selectBook[i] = tbl.getValueAt(row, i).toString();
				}
				openRegisterScreen();
				b[10].setBounds(320, 550, 100, 40);
				pn[1].add(b[9]);

				//テキストフィールドに値をセットする
				for(int i = 0; i < 4; i++) {
					tf[i].setText(selectBook[i]);
				}
				tf[4].setText((selectBook[4].substring(1)).replace(",", ""));//通貨形式⇒数値への変換
				cor6.setSelectedItem(selectBook[5]);

			}
		} else if(e.getSource() == b[5]) {	//編集ボタン
			row = tbl.getSelectedRow();
			String PID = tbl.getValueAt(row, 0).toString();
			if(row == -1) {		//どのセルも選択されていない場合のエラー処理
				Validate.noSelect();
			} else {
				String[] selectBook = new String[6];
				for(int i = 1; i < 6; i++) {
					selectBook[i] = tbl.getValueAt(row, i).toString();

				}
				openRegisterScreen();

				b[10].setBounds(320, 550, 100, 40);
				pn[1].add(b[11]);
				shiftPID = new JLabel(PID);

				//テキストフィールドに値をセットする
				for(int i = 0; i < 4; i++) {
					tf[i].setText(selectBook[i]);
				}
				tf[4].setText((selectBook[4].substring(1)).replace(",", ""));//通貨形式⇒数値への変換
				cor6.setSelectedItem(selectBook[5]);
			}

		} else if(e.getSource() == b[6]) {//削除ボタン
			row = tbl.getSelectedRow();

			if(row == -1) {
				Validate.noSelect();
			} else {
				String PID = tbl.getValueAt(row, 0).toString();
				int option = JOptionPane.showConfirmDialog(null, "本当に削除しますか？",
						"DELETE確認", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(option == JOptionPane.OK_OPTION) {
					try {
						DB.connect();
						DB.delete(PID);
						DB.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					//削除した行を削除する
					model.removeRow(row);
				}
			}
		} else if(e.getSource() == b[8] || e.getSource() == b[9]) {	//新規登録-登録ボタン

			try {
				String[] registerinfo = registercheck();
				if(registerinfo != null) {
					DB.connect();
					DB.bookregister(registerinfo[0], registerinfo[1], registerinfo[2], registerinfo[3], registerinfo[4], registerinfo[5]);
					DB.close();
					fm[1].dispose();
					setbookTable();
					JOptionPane.showMessageDialog(fm[0], msg1);
				}
			} catch (UnsupportedEncodingException | SQLException e1) {
				e1.printStackTrace();
			}

		} else if(e.getSource() == b[11]) {	//新規登録-取消ボタン
			fm[1].dispose();
		} else if(e.getSource() == b[10]) {	//編集-登録ボタン
			String PID = shiftPID.getText();
			try {
				String[] registerinfo = registercheck();
				if(registerinfo != null) {
					DB.connect();
					DB.bookupdate(registerinfo[0], registerinfo[1], registerinfo[2], registerinfo[3], registerinfo[4], registerinfo[5], PID);
					DB.close();
					fm[1].dispose();
					setbookTable();
					JOptionPane.showMessageDialog(fm[0], msg1);
				}
			} catch (UnsupportedEncodingException | SQLException e1) {
				e1.printStackTrace();
			}

		}
	}
}
