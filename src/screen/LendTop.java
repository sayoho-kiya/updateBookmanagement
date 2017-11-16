package screen;

import static property.Name.checkStar;
import static property.Name.comnt6;
import static property.Name.comnt7;
import static property.Name.msg1;
import static property.Name.msg5;
import static property.Name.msg6;
import static property.Name.msg7;
import static property.Name.noCheckStar;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import action.DB;
import validate.Validate;

public class LendTop implements ActionListener, MouseListener {

	JFrame[] fm = new JFrame[5];
	JPanel[] pn = new JPanel[5];
	JButton[] bt = new JButton[2];
	JButton[] b = new JButton[7];
	JRadioButton[] rb = new JRadioButton[8];
	JLabel[] star = new JLabel[5];
	JLabel[] lb = new JLabel[9];

	JSpinner spinner1, spinner2, spinner3, spinner4;
	JTextField tx1, tx11;
	SpinnerDateModel model1, model2, model3, model4;
	JComboBox serchcmb;

	String totalPrice;
	String cancelnum1 = "";
	String cancelnum2 = "";
	String cancelnum3 = "";

	JTable tbl;
	Container con;
	DefaultTableModel model;
	//mouseclisck用(返却時の評価）
	int cnt1 = 0;
	int cnt2 = 0;
	int cnt3 = 0;
	int cnt4 = 0;
	int cnt5 = 0;
	int eval1 = 1;
	int eval2 = 1;
	int eval3 = 1;
	int eval4 = 0;
	int eval5 = 0;
	int stars = 0; //評価の条件用
	int st = 5;	//在庫の条件用
	int st2 = 5;	//在庫の条件用

	//貸出管理TOP画面
	public void lendTop() throws SQLException {

		UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Meiryo UI", Font.PLAIN, 20)));
		UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("Meiryo UI", Font.PLAIN, 16)));
		//フレーム
		for(int i = 0; i < 5; i++) {
			fm[i] = new JFrame();
			fm[i].setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			fm[i].setVisible(false);
		}
		//パネル
		for(int i = 0; i < 5; i++) {
			pn[i] = new JPanel();
			pn[i].setLayout(null);
		}
		//【共通】登録＆取消ボタン
		for(int i = 0; i < 2; i++) {
			bt[i] = new JButton();
			bt[i].setFont(new Font("Meiryo UI", Font.PLAIN, 20));
		}
		bt[0].setText("登録");
		bt[1].setText("取消");

		fm[0].setBounds(0, 0, 1650, 1000);
		fm[0].setVisible(true);

		pn[0].setBounds(10, 10, 1600, 880);
		pn[0].setVisible(true);

		con = fm[0].getContentPane();
		con.add(pn[0]);

		//テーブルの作成
		String[] header = { "PID", "タイトル", "出版社", "著者", "ジャンル", "評価", "貸出状況", "エリア" };
		DB.connect();
		List<String[]> bookinfo = DB.makelendtopTable();
		DB.close();
		model = new DefaultTableModel(bookinfo.toArray(new String[0][0]), header);

		tbl = new JTable(model);
		tbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//複数行選択できないように設定
		tbl.setBounds(10, 300, 1600, 600);
		tbl.setFont(new Font("Meiryo UI", Font.PLAIN, 20));
		tbl.setRowHeight(28);
		tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JTableHeader jh = tbl.getTableHeader();
		jh.setFont(new Font("Meiryo UI", Font.PLAIN, 18));

		//列の幅を調整する
		int[] tblWidth = { 100, 400, 210, 210, 210, 190, 160, 85 };
		for(int i = 0; i < 8; i++) {
			tbl.getColumnModel().getColumn(i).setPreferredWidth(tblWidth[i]);
		}

		//中央揃え(貸出状況・エリア）
		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(SwingConstants.CENTER);
		tbl.getColumnModel().getColumn(6).setCellRenderer(center);
		tbl.getColumnModel().getColumn(7).setCellRenderer(center);

		JScrollPane sp = new JScrollPane(tbl);
		sp.setBounds(10, 200, 1600, 600);
		pn[0].add(sp);
		tbl.addMouseListener(this);

		//検索部分
		String[] serchList = { "タイトル", "出版社", "著者", "ジャンル" };
		serchcmb = new JComboBox(serchList);
		serchcmb.setBounds(700, 50, 150, 40);
		serchcmb.setFont(new Font("Meiryo UI", Font.PLAIN, 20));

		String[] rbLabel = { "在庫中", "貸出中", "予約中", "すべて", "★★2以上", "★★★3以上", "★★★★4以上", "すべて" };
		for(int i = 0; i < 8; i++) {
			rb[i] = new JRadioButton(rbLabel[i]);
			rb[i].setFont(new Font("Meiryo UI", Font.PLAIN, 18));
			rb[i].addActionListener(this);
			pn[0].add(rb[i]);
		}
		rb[0].setBounds(770, 130, 100, 40);
		rb[1].setBounds(880, 130, 100, 40);
		rb[2].setBounds(990, 130, 100, 40);
		rb[3].setBounds(1100, 130, 100, 40);
		rb[4].setBounds(770, 90, 110, 40);
		rb[5].setBounds(890, 90, 130, 40);
		rb[6].setBounds(1030, 90, 160, 40);
		rb[7].setBounds(1190, 90, 110, 40);

		ButtonGroup zaikoGp = new ButtonGroup();
		ButtonGroup hyoukaGp = new ButtonGroup();
		for(int i = 0; i < 4; i++) {
			zaikoGp.add(rb[i]);
		}
		for(int i = 4; i < 8; i++) {
			hyoukaGp.add(rb[i]);
		}

		//検索ワード入力用
		tx1 = new JTextField();
		tx1.setBounds(850, 50, 300, 40);
		tx1.setFont(new Font("Meiryo UI", Font.PLAIN, 20));

		JLabel[] slb = new JLabel[5];
		String[] slbText = { comnt6, comnt7, msg5, msg6, msg7 };
		for(int i = 0; i < 5; i++) {
			slb[i] = new JLabel(slbText[i]);
			slb[i].setFont(new Font("Meiryo UI", Font.PLAIN, 20));
			pn[0].add(slb[i]);
		}
		slb[0].setBounds(700, 90, 150, 40);
		slb[1].setBounds(700, 130, 150, 40);
		slb[2].setBounds(10, 135, 600, 40);
		slb[3].setBounds(10, 160, 600, 40);
		slb[4].setBounds(700, 15, 600, 40);

		pn[0].add(serchcmb);
		pn[0].add(tx1);

		String[] btnName = { "TOP", "実行", "リセット", "貸出/予約", "返却", "延長", "貸出履歴" };
		for(int i = 0; i < 7; i++) {
			b[i] = new JButton(btnName[i]);
			b[i].setFont(new Font("Meiryo UI", Font.PLAIN, 18));
			pn[0].add(b[i]);
			b[i].addActionListener(this);
		}

		b[0].setBounds(20, 20, 100, 40);
		b[1].setBounds(1300, 70, 100, 40);
		b[2].setBounds(1300, 120, 100, 40);
		b[3].setBounds(650, 850, 120, 40);
		b[4].setBounds(800, 850, 120, 40);
		b[5].setBounds(950, 850, 120, 40);
		b[6].setBounds(150, 20, 110, 40);

	}

	//貸出/予約登録
	public void Borrow() {

		fm[1].setBounds(50, 50, 700, 380);
		fm[1].setVisible(true);
		fm[1].add(pn[1]);
		pn[1].setVisible(true);

		String[] lbText = { "■タイトル：", "■貸出日：", "■借用者名：", "■返却予定日：", "", "", "※貸出日より後の日付で入力してください", "" };
		JLabel[] lb = new JLabel[8];
		for(int i = 0; i < 8; i++) {
			lb[i] = new JLabel(lbText[i]);
			lb[i].setFont(new Font("Meiryo UI", Font.PLAIN, 30));
			pn[1].add(lb[i]);
		}
		lb[0].setBounds(15, 30, 150, 40);
		lb[1].setBounds(15, 80, 150, 40);
		lb[2].setBounds(15, 130, 200, 40);
		lb[3].setBounds(15, 180, 200, 40);
		lb[4].setBounds(155, 30, 500, 40);//タイトル表示用
		//lb[5]いらないかも。
		lb[6].setBounds(15, 220, 400, 40);
		lb[7].setBounds(340, 75, 400, 40);
		lb[7].setForeground(Color.blue);

		tx11 = new JTextField();
		tx11.setFont(new Font("Meiryo UI", Font.PLAIN, 20));
		tx11.setBounds(170, 130, 200, 35); 	//借用者名

		Calendar calendar = Calendar.getInstance();
		Date initDate = calendar.getTime();//今日の日付を初期値にする
		calendar.set(2017, 1, 1, 0, 0);
		Date startDate = calendar.getTime();
		model1 = new SpinnerDateModel(initDate, startDate, null, Calendar.DAY_OF_MONTH);

		//貸出日
		spinner1 = new JSpinner(model1);
		JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner1, "yyyy-MM-dd");
		spinner1.setEditor(editor);
		spinner1.setBounds(160, 80, 160, 35);
		spinner1.setFont(new Font("Meiryo UI", Font.PLAIN, 20));

		//返却予定日
		Calendar calendar2 = Calendar.getInstance();
		calendar2.add(Calendar.DAY_OF_MONTH, 7);
		Date inischeDate = calendar2.getTime();
		model2 = new SpinnerDateModel(inischeDate, startDate, null, Calendar.DAY_OF_MONTH);
		JSpinner spinner2 = new JSpinner(model2);
		JSpinner.DateEditor editor2 = new JSpinner.DateEditor(spinner2, "yyyy-MM-dd");
		spinner2.setEditor(editor2);
		spinner2.setBounds(190, 180, 180, 35);
		spinner2.setFont(new Font("Meiryo UI", Font.PLAIN, 20));

		bt[0].setBounds(320, 250, 100, 40);
		bt[1].setBounds(440, 250, 100, 40);
		pn[1].add(bt[0]);
		pn[1].add(bt[1]);
		pn[1].add(tx11);

		pn[1].add(spinner1);
		pn[1].add(spinner2);

	}

	//返却
	public void Return() throws SQLException {

		fm[2].setBounds(50, 50, 700, 400);
		fm[2].setVisible(true);

		pn[2].setVisible(true);
		fm[2].add(pn[2]);

		String[] lbText = { "□名前：", "□タイトル：", "□返却日：", "□評価：", "", "", "", "（☆をクリックして5段階で評価してください）" };
		JLabel[] lb = new JLabel[8];
		for(int i = 0; i < 8; i++) {
			lb[i] = new JLabel();
			lb[i].setText(lbText[i]);
			lb[i].setFont(new Font("Meiryo UI", Font.PLAIN, 30));
			pn[2].add(lb[i]);
		}
		lb[0].setBounds(15, 40, 100, 40);
		lb[1].setBounds(15, 90, 130, 40);
		lb[2].setBounds(15, 140, 200, 40);
		lb[3].setBounds(15, 190, 200, 40);
		lb[4].setBounds(155, 40, 400, 40);
		lb[6].setBounds(155, 90, 600, 40);
		lb[7].setBounds(25, 225, 600, 40);
		lb[7].setForeground(Color.blue);

		for(int i = 0; i < 5; i++) {
			star[i] = new JLabel();
			star[i].setFont(new Font("Meiryo UI", Font.PLAIN, 30));
			pn[2].add(star[i]);
			star[i] = new JLabel(checkStar);
			star[i].addMouseListener(this);
		}

		star[3].setText(noCheckStar);
		star[4].setText(noCheckStar);

		star[0].setBounds(145, 190, 30, 40);
		star[1].setBounds(175, 190, 30, 40);
		star[2].setBounds(205, 190, 30, 40);
		star[3].setBounds(235, 190, 30, 40);
		star[4].setBounds(265, 190, 30, 40);

		int row = tbl.getSelectedRow();
		String selectPID = tbl.getValueAt(row, 0).toString();//選択している行のPID
		String selectTitle = tbl.getValueAt(row, 1).toString();//選択している行のタイトル
		lb[5].setText(selectTitle);

		//貸出者を取得する
		DB.connect();
		String[] info = DB.getLendName(selectPID);
		DB.close();
		lb[4].setText(info[0]);
		lb[6].setText(info[1]);

		Calendar calendar = Calendar.getInstance();
		Date initDate = calendar.getTime();//今日の日付を初期値にする
		calendar.set(2017, 1, 1, 0, 0);
		Date startDate = calendar.getTime();
		model3 = new SpinnerDateModel(initDate, startDate, null, Calendar.DAY_OF_MONTH);
		JSpinner spinner3 = new JSpinner(model3);
		JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner3, "yyyy-MM-dd");
		spinner3.setEditor(editor);
		spinner3.setBounds(155, 140, 160, 40);
		spinner3.setFont(new Font("Meiryo UI", Font.PLAIN, 20));

		bt[0].setBounds(300, 270, 100, 40);
		bt[1].setBounds(420, 270, 100, 40);

		pn[2].add(bt[0]);
		pn[2].add(bt[1]);
		pn[2].add(spinner3);

	}

	//延長
	public void Extend() throws SQLException {

		fm[3].setBounds(50, 50, 700, 350);
		fm[3].setVisible(true);

		pn[3].setVisible(true);
		fm[3].add(pn[3]);

		String[] lbText = { "■タイトル：", "■名前：", "■返却予定日：", "", "", "" };
		JLabel[] lb = new JLabel[6];
		for(int i = 0; i < 6; i++) {
			lb[i] = new JLabel();
			lb[i].setFont(new Font("Meiryo UI", Font.PLAIN, 25));
			lb[i].setText(lbText[i]);
			pn[3].add(lb[i]);
		}
		lb[0].setBounds(15, 40, 150, 40);
		lb[1].setBounds(15, 90, 100, 40);
		lb[2].setBounds(15, 140, 200, 40);
		lb[3].setBounds(155, 40, 500, 40);
		lb[4].setBounds(155, 90, 200, 40);

		bt[0].setBounds(300, 200, 100, 40);
		bt[1].setBounds(420, 200, 100, 40);

		pn[3].add(bt[0]);
		pn[3].add(bt[1]);

		int row = tbl.getSelectedRow();
		String selectPID = tbl.getValueAt(row, 0).toString();//選択している行のPID
		String selectTitle = tbl.getValueAt(row, 1).toString();//選択している行のタイトル
		lb[3].setText(selectTitle);

		//貸出者を取得する
		DB.connect();
		String[] info = DB.getLendName(selectPID);
		DB.close();
		lb[4].setText(info[0]);//name
		lb[5].setText(info[1]);//num

		//String⇒intへの変換
		int yy = (Integer.parseInt(info[2].substring(0, 4)));
		int mm = (Integer.parseInt(info[2].substring(5, 7)));
		int dd = (Integer.parseInt(info[2].substring(8, 10)));

		Calendar calendar4 = Calendar.getInstance();
		calendar4.set(yy, mm - 1, dd);	//調整

		Date iniDate = calendar4.getTime();
		calendar4.set(2017, 1, 1, 0, 0);
		Date startDate = calendar4.getTime();

		model4 = new SpinnerDateModel(iniDate, startDate, null, Calendar.DAY_OF_MONTH);
		JSpinner spinner4 = new JSpinner(model4);
		JSpinner.DateEditor editor4 = new JSpinner.DateEditor(spinner4, "yyyy-MM-dd");
		spinner4.setEditor(editor4);
		spinner4.setBounds(155, 140, 160, 40);
		spinner4.setFont(new Font("Meiryo UI", Font.PLAIN, 20));

		spinner4.setBounds(190, 140, 160, 35);
		spinner4.setFont(new Font("Meiryo UI", Font.PLAIN, 20));
		pn[3].add(spinner4);

	}

	//テーブルのセット
	public void setTable() throws SQLException {
		DB.connect();
		List<String[]> bookinfo = DB.makelendtopTable();
		DB.close();
		model.setRowCount(0);
		for(int i = 0; i < bookinfo.size(); i++) {
			model.addRow(bookinfo.get(i));
		}

		cnt1 = 0;//すべて初期値に戻す
		cnt2 = 0;
		cnt3 = 0;
		cnt4 = 0;
		cnt5 = 0;
		eval1 = 1;
		eval2 = 1;
		eval3 = 1;
		eval4 = 0;
		eval5 = 0;
	}

	//検索
	public void reserch() throws SQLException {
		String item = null;
		String a = "";
		String b = "";
		String c = "";
		String d = "";
		String Reserchstr = null;
		String keyword = tx1.getText();
		String selectItem = (String) serchcmb.getSelectedItem();
		if(selectItem.equals("タイトル")) {
			item = "title";
		} else if(selectItem.equals("出版社")) {
			item = "publisher";
		} else if(selectItem.equals("著者")) {
			item = "author";
		} else if(selectItem.equals("ジャンル")) {
			item = "genre";
		}
		if(rb[4].isSelected()) {//検索用項目
			stars = 2;
		} else if(rb[5].isSelected()) {
			stars = 3;
		} else if(rb[6].isSelected()) {
			stars = 4;
		} else if(rb[7].isSelected()) {
			stars = 0;
		}
		if(rb[0].isSelected()) {
			st = 0;
			st2 = 5;
		} else if(rb[1].isSelected()) {
			st = 1;
			st2 = 5;
		} else if(rb[2].isSelected()) {
			st = 5;
			st2 = 2;
		} else if(rb[3].isSelected()) {
			st = 5;
			st2 = 5;
		}

		if((keyword.length() != 0)) {
			a = "A";
		}
		if(stars == 0) {
			b = "B";
		}
		if(st == 5) {
			c = "C";
		}
		if(st2 == 5) {
			d = "D";
		}
		Reserchstr = a + b + c + d;
		String Reserchsql = "";
		switch (Reserchstr) {
		//①キーワード②評価③在庫or貸出④予約

		case "ABCD"://①あり②null③null④null
			Reserchsql = "SELECT*FROM BOOK_LIST WHERE " + item + " LIKE '%" + keyword + "%';";
			break;
		case "ABC"://①あり②null③null④予約中
			Reserchsql = "SELECT*FROM BOOK_LIST WHERE " + item + " LIKE '%" + keyword + "%'" + " and STATE2=2;";
			break;
		case "ABD"://①あり②null③貸出or在庫チェックあり
			Reserchsql = "SELECT*FROM BOOK_LIST WHERE " + item + " LIKE '%" + keyword + "%'" + " and STATE=" + st + ";";
			break;
		case "ACD"://①あり②評価あり③null④null
			Reserchsql = "SELECT*FROM BOOK_LIST WHERE " + item + " LIKE '%" + keyword + "%' and AVE_EVA >=" + stars + ";";
			break;
		case "AC"://①あり②評価あり③null④予約中
			Reserchsql = "SELECT*FROM BOOK_LIST WHERE " + item + " LIKE '%" + keyword + "%' and AVE_EVA >=" + stars + " and STATE2=" + st2 + ";";
			break;
		case "A"://①あり②評価あり③貸出or在庫チェックあり
			Reserchsql = "SELECT*FROM BOOK_LIST WHERE " + item + " LIKE '%" + keyword + "%' and AVE_EVA >=" + stars + " and STATE=" + st + ";";
			break;
		case "BCD"://①条件にキーワード入力なし②評価なし③貸出or在庫チェックなし④なし
			Reserchsql = "SELECT*FROM BOOK_LIST;";
			break;
		case "BC"://①条件にキーワード入力なし②評価なし③貸出or在庫チェックなし④予約あり
			Reserchsql = "SELECT*FROM BOOK_LIST WHERE STATE2='2';";
			break;
		case "BD"://①条件にキーワード入力なし②評価なし③貸出or在庫チェックあり
			Reserchsql = "SELECT*FROM BOOK_LIST WHERE STATE=" + st + ";";
			break;
		case "CD"://①条件にキーワード入力なし②評価チェックあり③在庫のチェックなし④予約チェックなし
			Reserchsql = "SELECT*FROM BOOK_LIST WHERE AVE_EVA >=" + stars + ";";
			break;
		case "C"://①条件にキーワード入力なし②評価チェックあり③在庫のチェックなし④予約チェックあり
			Reserchsql = "SELECT*FROM BOOK_LIST WHERE  AVE_EVA >=" + stars + " and STATE2=" + st2 + ";";
			break;
		case "D"://①条件にキーワード入力なし②評価チェックあり③在庫のチェックあり④予約チェックなし
			Reserchsql = "SELECT*FROM BOOK_LIST WHERE  AVE_EVA >=" + stars + " and STATE=" + st + ";";
			break;
		case ""://①条件にキーワード入力なし②評価チェックあり③在庫チェックあり④予約チェックあり
			Reserchsql = "SELECT*FROM BOOK_LIST WHERE  AVE_EVA >=" + stars + " and STATE=" + st + ";";
			break;
		}
		DB.connect();
		List<String[]> bookreserchinfo = DB.reserch(Reserchsql);
		DB.close();
		if(bookreserchinfo.isEmpty()) {
			JOptionPane.showMessageDialog(fm[5], "条件に一致する書籍はありません。");
		} else {
			model.setRowCount(0);
			for(int i = 0; i < bookreserchinfo.size(); i++) {
				model.addRow(bookreserchinfo.get(i));
			}

		}
	}

	//貸出・予約情報の表示
	public void showLendinfo(int row) throws SQLException {

		String PID = tbl.getValueAt(row, 0).toString();//選択している行のPID
		String selectTitle = tbl.getValueAt(row, 1).toString();//選択している行のタイトル

		fm[5].setBounds(850, 50, 650, 500);
		fm[5].setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fm[5].setVisible(true);

		Container con = fm[5].getContentPane();
		con.add(pn[5]);

		JLabel[] inf = new JLabel[3];
		String[] infoText = { "＜貸出情報＞", "========================", "＜予約情報＞" };
		for(int i = 0; i < 3; i++) {
			inf[i] = new JLabel();
			inf[i].setFont(new Font("Meiryo UI", Font.PLAIN, 27));
			inf[i].setText(infoText[i]);
		}

		inf[0].setBounds(15, 20, 300, 40);
		inf[1].setBounds(15, 220, 550, 40);
		inf[2].setBounds(15, 270, 300, 40);

		pn[5].add(inf[0]);
		pn[5].add(inf[1]);
		pn[5].add(inf[2]);

		DB.connect();
		String[] info = DB.getlendInfo(PID);

		if(info[0] == null) {
			JLabel lendinfo = new JLabel("貸出情報はありません。");
			lendinfo.setFont(new Font("Meiryo UI", Font.PLAIN, 25));
			lendinfo.setBounds(15, 100, 550, 40);
			pn[5].add(lendinfo);
		} else {

			String[] lbText = { "タイトル：", "借用者：", "貸出日：", "返却予定日：", selectTitle, info[0], info[1], info[2], "" };
			for(int i = 0; i < 9; i++) {
				lb[i] = new JLabel();
				lb[i].setText(lbText[i]);
				lb[i].setFont(new Font("Meiryo UI", Font.PLAIN, 25));
				pn[5].add(lb[i]);
			}

			if(info[2].compareTo(Common.Today) < 0) {
				lb[8].setText("※返却期限を過ぎています");
			}

			lb[0].setBounds(15, 60, 100, 40);
			lb[1].setBounds(15, 100, 100, 40);
			lb[2].setBounds(15, 140, 100, 40);
			lb[3].setBounds(15, 180, 160, 40);
			lb[4].setBounds(120, 60, 500, 40);
			lb[5].setBounds(120, 100, 200, 40);
			lb[6].setBounds(120, 140, 200, 40);
			lb[7].setBounds(175, 180, 200, 40);
			lb[8].setBounds(340, 180, 200, 40);
			lb[8].setForeground(Color.red);

		}
		JButton[] b = new JButton[4];
		for(int i = 0; i < 4; i++) {
			b[i] = new JButton();
			b[i].setFont(new Font("Meiryo UI", Font.PLAIN, 15));
			b[i].addActionListener(this);
			b[i].setText("予約取消");
		}

		b[0].setBounds(400, 330, 100, 40);
		b[1].setBounds(400, 530, 100, 40);
		b[2].setBounds(400, 730, 100, 40);
		b[3].setBounds(470, 10, 70, 40);
		b[3].setText("閉");
		pn[5].add(b[3]);

		//貸出者・貸出日・返却予定日を取得する

		int cnt = DB.getRsvCnt(PID);

		if(cnt == 0) {
			JLabel rsvmsg = new JLabel("予約情報はありません。");
			rsvmsg.setBounds(20, 310, 300, 40);
			rsvmsg.setFont(new Font("Meiryo UI", Font.PLAIN, 25));
			pn[5].add(rsvmsg);
			DB.close();
		} else {
			ArrayList<String> rsvinfo = DB.getrsvinfo(PID);

			for(int i = 0; i < cnt; i++) {
				fm[5].setBounds(850, 50, 600, 400 + (200 * cnt));

				String[] lbText = { "貸出者：", "貸出日：", "返却予定日：", "No." };
				JLabel[] lb = new JLabel[4];
				for(int j = 0; j < 4; j++) {
					lb[j] = new JLabel();
					lb[j].setText(lbText[i]);
					lb[j].setFont(new Font("Meiryo UI", Font.PLAIN, 25));
					pn[5].add(lb[j]);
				}

				lb[0].setBounds(15, 350 + (180 * i), 100, 40);
				lb[1].setBounds(15, 390 + (180 * i), 100, 40);
				lb[2].setBounds(15, 430 + (180 * i), 150, 40);
				lb[3].setBounds(15, 310 + (180 * i), 150, 40);

				JLabel[] naiyou = new JLabel[4];
				for(int k = 0; k < 4; k++) {
					naiyou[k] = new JLabel();
					naiyou[k].setText(rsvinfo.get(k + (4 * i)));
					naiyou[k].setFont(new Font("Meiryo UI", Font.PLAIN, 25));
					pn[5].add(naiyou[k]);
				}

				naiyou[0].setBounds(145, 350 + (180 * i), 200, 40);
				naiyou[1].setBounds(145, 390 + (180 * i), 200, 40);
				naiyou[2].setBounds(175, 430 + (180 * i), 250, 40);
				naiyou[3].setBounds(80, 310 + (180 * i), 250, 40);

				if(i == 0) {
					pn[5].add(b[0]);
					cancelnum1 = rsvinfo.get(3);
				} else if(i == 1) {
					pn[5].add(b[1]);
					cancelnum2 = rsvinfo.get(7);
				} else if(i == 2) {
					pn[5].add(b[2]);
					cancelnum3 = rsvinfo.get(11);
				}
			}
			DB.close();
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == b[0]) {	//TOP
			fm[0].dispose();
			Top top = new Top();
			try {
				top.Top();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else if(e.getSource() == b[6]) {	//履歴
			fm[0].dispose();
			Rireki rireki = new Rireki();
			try {
				rireki.rireki();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else if(e.getSource() == b[1]) {	//実行ボタン
			try {
				reserch();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
		} else if(e.getSource() == b[2]) {	//リセットボタン
			try {
				setTable();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			tx1.setText("");
			stars = 0; //評価の条件用
			st = 5;	//在庫の条件用
			st2 = 5;	//在庫の条件用
			rb[3].setSelected(true);
			rb[7].setSelected(true);
		} else if(e.getSource() == b[3]) {	//貸出/予約ボタン
			int row = tbl.getSelectedRow();
			if(row == -1) {
				Validate.noSelect();
			} else {
				String selectState = tbl.getValueAt(row, 6).toString();//選択している行のstate
				if(selectState.equals("貸出中 ") || selectState.equals("貸出中 予約中")) {//680行目にあわせて、貸出中＋半角スペース必須
					String selectTitle = tbl.getValueAt(row, 1).toString();//選択している行のタイトル
					Borrow();
					lb[7].setText("※貸出中のため予約のみ可能※");
					lb[4].setText(selectTitle);
				} else {
					String selectTitle = tbl.getValueAt(row, 1).toString();//選択している行のタイトル
					Borrow();
					lb[4].setText(selectTitle);

				}
			}
		} else if(e.getSource() == b[4]) {	//返却
			int row = tbl.getSelectedRow();
			if(row == -1) {
				Validate.noSelect();
			} else {
				String selectstate = tbl.getValueAt(row, 6).toString();//選択している行のstate
				if(selectstate.equals(" ")) {
					Validate.NotOK();
				} else {
					try {
						Return();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		} else if(e.getSource() == b[5]) {//延長
			int row = tbl.getSelectedRow();
			if(row == -1) {
				Validate.noSelect();
			} else {
				String selectstate = tbl.getValueAt(row, 6).toString();//選択している行のstate
				if(selectstate.equals(" ") || selectstate.equals(" 予約中")) {
					Validate.NotOK();
				} else {
					try {
						Extend();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

				}
			}
		} else if(e.getSource() == bt[1]) { //貸出登録ボタン
			int row = tbl.getSelectedRow();
			String selectPID = tbl.getValueAt(row, 0).toString();//選択している行のPID
			String selectState = tbl.getValueAt(row, 6).toString();//選択している行のstate

			//入力された値の取得
			String name = tx11.getText();
			Date brd = model1.getDate();
			Date rtn = model2.getDate();

			String bdate = Common.DataFmt.format(brd);
			String rtndate = Common.DataFmt.format(rtn);

			Pattern p = Pattern.compile("[^0123456789-]");
			Matcher m = p.matcher(bdate);
			Matcher m2 = p.matcher(rtndate);

			if(name.length() == 0 || rtndate.length() == 0 || bdate.length() == 0) {
				Validate.Null();
			} else if(rtndate.compareTo(bdate) <= 0) {//返却日が貸出日より過去（同日含む）
				Validate.DateCheck();
			} else if((bdate.compareTo(Common.Today) < 0)) {//貸出日が今日より過去
				Validate.DateCheck();
			} else if(m.find() || m2.find()) {
				Validate.hankaku();
			} else {//エラーがない場合

				switch (selectState) {

				case "貸出中 ":
					//返却予定日を取得する
					String sche_dt = null;
					try {
						DB.connect();
						sche_dt = DB.getRtndate(selectPID);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					if(sche_dt.compareTo(bdate) > 0) {
						Validate.CannotRsv();
					} else {//通常処理

						try {
							DB.borrowregister(selectPID, name, bdate, rtndate);
						} catch (SQLException e1) {

							e1.printStackTrace();
						}
						//BOOK_LIST ステータスの確認・変更
						if(bdate.compareTo(Common.Today) == 0) {
							try {
								DB.updatelendstate(selectPID);
							} catch (SQLException e1) {
								e1.printStackTrace();
							}

						} else if(bdate.compareTo(Common.Today) > 0) {
							try {
								DB.updatersvstate(selectPID);
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
						try {
							setTable();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						JOptionPane.showMessageDialog(fm[0], msg1 + "返却期限は" + rtndate + "です。");
						try {
							setTable();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						fm[1].dispose();

					}
					DB.close();

				case "貸出中 予約中":
				case " 予約中":

					//貸出日と返却予定日を取得する（貸出日順に並べる）
					String[] OSinfo = null;
					try {
						DB.connect();
						OSinfo = DB.getOutandSchedate(selectPID);
					} catch (SQLException e1) {

						e1.printStackTrace();
					}
					String out_dt = OSinfo[0];
					sche_dt = OSinfo[1];

					//入力中の日付が予約日と返却日のあいだ
					if(out_dt.compareTo(bdate) < 0 && sche_dt.compareTo(bdate) > 0) {
						Validate.CannotRsv();

						//入力中の返却予定日が予約の貸出日と返却予定日のあいだ
					} else if(out_dt.compareTo(rtndate) < 0 && sche_dt.compareTo(rtndate) > 0) {
						Validate.CannotRsv();

						//予約より先に貸出、予約より後に返却の場合
					} else if(out_dt.compareTo(bdate) > 0 && sche_dt.compareTo(rtndate) < 0) {
						Validate.CannotRsv();

					} else {
						try {

							DB.connect();
							DB.borrowregister(selectPID, name, bdate, rtndate);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						//BOOK_LIST ステータスの確認・変更
						if(bdate.compareTo(Common.Today) == 0) {
							try {
								DB.connect();
								DB.updatelendstate(selectPID);
								DB.close();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}

						} else if(bdate.compareTo(Common.Today) > 0) {
							try {
								DB.connect();
								DB.updatersvstate(selectPID);
								DB.close();
							} catch (SQLException e1) {

								e1.printStackTrace();
							}
							JOptionPane.showMessageDialog(fm[0], msg1 + "返却期限は" + rtndate + "です。");
							try {
								setTable();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							fm[1].dispose();
						}
					}

				case " ":

					try {

						DB.connect();
						DB.borrowregister(selectPID, name, bdate, rtndate);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

					//BOOK_LIST ステータスの確認・変更
					if(bdate.compareTo(Common.Today) == 0) {
						try {
							DB.updatelendstate(selectPID);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}

					} else if(bdate.compareTo(Common.Today) > 0) {
						try {
							DB.updatersvstate(selectPID);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
					DB.close();
					JOptionPane.showMessageDialog(fm[0], msg1 + "返却期限は" + rtndate + "です。");
					try {
						setTable();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					fm[1].dispose();
				}
			}
		} else if(e.getSource() == bt[1]) {	//	貸出/予約-取消
			fm[1].dispose();
		} else if(e.getSource() == bt[0]) {	//返却-登録
			int row = tbl.getSelectedRow();
			String selectPID = tbl.getValueAt(row, 0).toString();//選択している行のPID
			Date idt = model3.getDate();
			String indate = Common.DataFmt.format(idt);
			String name = lb[4].getText();
			String rtnnum = lb[6].getText();
			//評価
			int total_eval = eval1 + eval2 + eval3 + eval4 + eval5;//★の合計値
			//返却処理
			try {
				DB.connect();
				totalPrice = DB.returnbook(indate, total_eval, rtnnum, name, selectPID);
				DB.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(fm[0], msg1
					+ "あなたは累計" + totalPrice + "円の本を読破しました！");

			fm[2].dispose();
			try {
				setTable();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else if(e.getSource() == bt[1]) {	//	返却-取消
			fm[1].dispose();
		} else if(e.getSource() == bt[0]) {	//	延長-登録
			int row = tbl.getSelectedRow();
			String selectPID = tbl.getValueAt(row, 0).toString();//選択している行のPID
			Date edt = model4.getDate();
			String extendate = Common.DataFmt.format(edt);
			String extendnum = lb[5].getText();

			//予約件数を取得
			int getrsv = 0;
			try {
				DB.connect();
				getrsv = DB.getRsvCnt(selectPID);
				DB.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			if(getrsv == 0) {//予約がない場合
				//通常処理
				try {
					DB.connect();
					DB.extenLend(extendate, extendnum);
					DB.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(fm[0], msg1);
				fm[3].dispose();
				try {
					setTable();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} else {//予約がほかにある場合

				//貸出日と返却予定日を取得する（貸出日順に並べる）
				String[] OSinfo = null;
				try {
					DB.connect();
					OSinfo = DB.getOutandSchedate(selectPID);
					DB.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				String out_dt = OSinfo[0];
				String sche_dt = OSinfo[1];

				//入力中の日付が予約日と返却日のあいだ
				if(out_dt.compareTo(extendate) < 0 && sche_dt.compareTo(extendate) > 0) {
					Validate.CannotRsv();
					//入力中の返却予定日が予約の返却予定日以上のとき
				} else if(sche_dt.compareTo(extendate) < 0) {
					Validate.CannotRsv();
					//予約より先に貸出、予約より後に返却の場合
				} else {
					//通常処理
					try {
						DB.connect();
						DB.extenLend(extendate, extendnum);
						DB.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(fm[0], msg1);
					try {
						setTable();
						fm[3].dispose();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

				}
			}

		} else if(e.getSource() == bt[1]) {//延長-取消ボタン
			fm[3].dispose();
		}

		if(e.getSource() == b[3]) {//閉じるボタン
			fm[5].dispose();
		} else if(e.getSource() == bt[1]) {//予約取消
			String PID = null;
			int cnt = 0;
			try {
				DB.connect();
				PID = DB.getPID(cancelnum1);
				cnt = DB.getRsvCnt(PID);
				DB.cancel(cancelnum1);

			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			if(cnt == 1) {
				try {
					DB.changeRsvstate(PID);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			JOptionPane.showMessageDialog(fm[5], "予約を取り消しました。");
			try {
				setTable();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			fm[5].dispose();
			DB.close();
		} else if(e.getSource() == b[2]) {//予約取消（2件目）
			try {
				DB.connect();
				DB.cancel(cancelnum2);
				DB.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(fm[5], "予約を取り消しました。");
			try {
				setTable();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			fm[5].dispose();
		} else if(e.getSource() == b[3]) {//予約取消（3件目）
			try {
				DB.connect();
				DB.cancel(cancelnum3);
				DB.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			JOptionPane.showMessageDialog(fm[5], "予約を取り消しました。");
			try {
				setTable();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			fm[5].dispose();
		}
	}

	@Override
	//返却時評価用
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == star[1]) {
			cnt2++;
			if(cnt2 % 2 == 1) {
				star[1].setText(noCheckStar);
				eval2 = 0;
			} else if(cnt2 % 2 == 0) {
				star[1].setText(checkStar);
				eval2 = 1;
			}
		}
		if(e.getSource() == star[2]) {
			cnt3++;
			if(cnt3 % 2 == 1) {
				star[2].setText(noCheckStar);
				eval3 = 0;
			} else if(cnt3 % 2 == 0) {
				star[2].setText(checkStar);
				eval3 = 1;
			}
		}
		if(e.getSource() == star[3]) {	//初期☆
			cnt4++;
			if(cnt4 % 2 == 0) {
				star[3].setText(noCheckStar);
				eval4 = 0;
			} else if(cnt4 % 2 == 1) {
				star[3].setText(checkStar);
				eval4 = 1;
			}
		}
		if(e.getSource() == star[4]) {	//初期☆
			cnt5++;
			if(cnt5 % 2 == 0) {
				star[4].setText(noCheckStar);
				eval5 = 0;
			} else if(cnt5 % 2 == 1) {
				star[4].setText(checkStar);
				eval5 = 1;
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent e) {	//貸出・予約情報の表示
		if(e.getButton() == MouseEvent.BUTTON3) { //右クリック

			int row = tbl.getSelectedRow();
			try {
				showLendinfo(row);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}
