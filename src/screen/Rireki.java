package screen;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import action.DB;

public class Rireki implements ActionListener {
	JFrame rframe, lframe;
	JPanel rpnl, lpnl;
	JButton topbtn, lankpbtn, clbtn;

	public void rireki() throws SQLException {

		rframe = new JFrame("貸出/返却履歴");
		rframe.setBounds(0, 0, 1850, 950);
		rframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		rframe.setVisible(true);

		rpnl = new JPanel();
		rpnl.setLayout(null);

		topbtn = new JButton();
		topbtn.setBounds(10, 10, 110, 40);
		topbtn.setFont(new Font("Meiryo UI", Font.PLAIN, 15));
		topbtn.addActionListener(this);
		rpnl.add(topbtn);

		lankpbtn = new JButton();
		lankpbtn.setBounds(130, 10, 120, 40);
		lankpbtn.setFont(new Font("Meiryo UI", Font.PLAIN, 15));
		lankpbtn.addActionListener(this);
		rpnl.add(lankpbtn);

		JLabel msg = new JLabel("※ヘッダーの項目を選択すると並び替えを行います。");
		msg.setFont(new Font("Meiryo UI", Font.PLAIN, 18));
		msg.setBounds(10, 65, 600, 40);
		rpnl.add(msg);

		DB.connect();
		List<String[]> list = DB.getRireki();
		DB.close();

		String[] header = { "No.", "名前", "タイトル", "出版社", "著者", "ジャンル", "評価", "貸出日", "返却予定", "返却日" };
		DefaultTableModel model = new DefaultTableModel(list.toArray(new String[0][0]), header);
		JTable rtbl = new JTable(model) {

			@Override
			public Component prepareRenderer(TableCellRenderer tcr, int row, int column) {
				Component c = super.prepareRenderer(tcr, row, column);
				//返却期限過ぎても返却されていない場合に色づけ
				if((getValueAt(row, 8).toString().compareTo(Common.Today) <= 0 && column == 8)) {

					c.setBackground(Color.pink);
					Object a = getValueAt(row, 6).toString();
					if((a.equals("★") || a.equals("★★") || a.equals("★★★") || a.equals("★★★★") || a.equals("★★★★★"))) {
						c.setBackground(getBackground());
					}

				} else {
					c.setBackground(getBackground());
				}
				return c;
			}
		};

		rtbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//複数行選択できないように設定
		rtbl.setBounds(10, 30, 1750, 750);
		rtbl.setFont(new Font("Meiryo UI", Font.PLAIN, 18));
		rtbl.setRowHeight(28);

		JTableHeader jh = rtbl.getTableHeader();
		jh.setFont(new Font("Meiryo UI", Font.PLAIN, 18));

		//列の幅を調整する
		rtbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		rtbl.setAutoCreateRowSorter(true);

		int[] tblWidth = { 90, 170, 370, 200, 200, 200, 130, 145, 145, 145 };
		for(int i = 0; i < 10; i++) {
			rtbl.getColumnModel().getColumn(i).setPreferredWidth(tblWidth[i]);
		}

		JScrollPane sp = new JScrollPane(rtbl);
		sp.setBounds(10, 100, 1800, 750);

		Container rcon = rframe.getContentPane();

		rcon.add(rpnl);
		rpnl.add(sp);

	}

	public void lankPrint() throws SQLException {

		lframe = new JFrame("ランキング");
		lframe.setBounds(50, 50, 650, 800);
		lframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		lframe.setVisible(true);

		Container lcon = lframe.getContentPane();

		lpnl = new JPanel();
		lpnl.setLayout(null);
		lcon.add(lpnl);

		String bd = "-------------------------------------";
		JLabel[] border = new JLabel[6];
		for(int i = 0; i < 6; i++) {
			border[i] = new JLabel();
			border[i].setText(bd);
			border[i].setFont(new Font("Meiryo UI", Font.PLAIN, 25));
			lpnl.add(border[i]);
		}
		border[0].setBounds(110, 10, 500, 40);
		border[1].setBounds(110, 70, 500, 40);
		border[2].setBounds(110, 230, 500, 40);
		border[3].setBounds(110, 290, 500, 40);
		border[4].setBounds(100, 450, 500, 40);
		border[5].setBounds(100, 510, 500, 40);

		String m = "●";
		JLabel[] maru = new JLabel[6];
		for(int i = 0; i < 6; i++) {
			maru[i] = new JLabel();
			maru[i].setText(m);
			maru[i].setFont(new Font("Meiryo UI", Font.PLAIN, 26));
			lpnl.add(maru[i]);
		}
		maru[0].setBounds(130, 40, 40, 40);
		maru[1].setBounds(470, 40, 500, 40);
		maru[2].setBounds(140, 260, 40, 40);
		maru[3].setBounds(480, 260, 500, 40);
		maru[4].setBounds(115, 480, 40, 40);
		maru[5].setBounds(500, 480, 500, 40);

		maru[0].setForeground(Color.ORANGE);
		maru[1].setForeground(Color.ORANGE);
		maru[2].setForeground(Color.MAGENTA);
		maru[3].setForeground(Color.MAGENTA);
		maru[4].setForeground(Color.BLUE);
		maru[5].setForeground(Color.BLUE);

		clbtn = new JButton("閉");
		clbtn.setBounds(530, 680, 50, 40);
		clbtn.setFont(new Font("Meiryo UI", Font.PLAIN, 15));
		clbtn.addActionListener(this);
		lpnl.add(clbtn);

		DB.connect();
		ArrayList<String> lendtime = DB.getLendbookRank();
		//多く借りられている本ランキング
		ArrayList<String> popbook = DB.getpopularbookRank();
		//評価が高いランキング
		ArrayList<String> highevalbook = DB.gethighevalbook();
		DB.close();

		String[] title = { "借りた回数が多い人 TOP3", "読んでる人が多い本 TOP3", "読んだ人の評価が高い本　TOP3" };
		JLabel[] titleTop = new JLabel[3];
		for(int i = 0; i < 3; i++) {
			titleTop[i] = new JLabel();
			titleTop[i].setFont(new Font("Meiryo UI", Font.PLAIN, 26));
			lpnl.add(titleTop[i]);
		}

		titleTop[0].setBounds(150, 40, 500, 40);
		titleTop[1].setBounds(170, 260, 700, 40);
		titleTop[2].setBounds(150, 480, 700, 40);

		JLabel[] rank = new JLabel[9];
		JLabel[] rankNum = new JLabel[9];
		for(int i = 0; i < 9; i++) {
			rank[i] = new JLabel();
			rankNum[i] = new JLabel();
			rank[i].setFont(new Font("Meiryo UI", Font.PLAIN, 20));
			rankNum[i].setFont(new Font("Meiryo UI", Font.PLAIN, 20));
			lpnl.add(rank[i]);
			lpnl.add(rankNum[i]);
		}

		for(int i = 0; i < 3; i++) {
			rank[i].setText("【" + (i + 1) + "】　" + lendtime.get(0 + (i * 2)) + " さん");
			rankNum[i].setText(lendtime.get(1 + (i * 2)) + "冊");
			rank[i].setBounds(120, 100 + (i * 40), 700, 40);
			rankNum[i].setBounds(470, 100 + (i * 40), 700, 40);
		}

		for(int i = 3; i < 6; i++) {
			rank[i].setText("【" + (i + 1) + "】　" + popbook.get(i + 3));
			rankNum[i].setText("    " + popbook.get(i) + "回");
			rank[i].setBounds(80, 320 + (i * 40), 400, 40);
			rankNum[i].setBounds(470, 320 + (i * 40), 400, 40);
		}

		for(int i = 6; i < 9; i++) {

			rank[i].setText("【" + (i + 1) + "】 " + highevalbook.get(0 + (i * 2)));
			rankNum[i].setText("    ★" + highevalbook.get(1 + (i * 2)));
			rank[i].setBounds(80, 540 + (i * 40), 500, 40);
			rankNum[i].setBounds(470, 540 + (i * 40), 700, 40);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == clbtn) {
			lframe.dispose();
		}
		if(e.getSource() == topbtn) {
			rframe.dispose();
			LendTop lendt = new LendTop();
			try {
				lendt.lendTop();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		if(e.getSource() == lankpbtn) {
			try {
				lankPrint();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}

		}

	}
}
