package validate;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Validate {
	//フォント
	static void setFont(Component[] components, Font font) {
		if(components == null) {
			return;
		}
		for(int i = 0; i < components.length; i++) {
			components[i].setFont(font);
			setFont(
					((JComponent) components[i]).getComponents(),
					font);
		}
	}

	public static void Null() {
		JOptionPane optionPane = new JOptionPane("入力されていない項目があります", JOptionPane.ERROR_MESSAGE);
		JDialog dialog = optionPane.createDialog(null, "エラー_1000");
		setFont(dialog.getContentPane().getComponents(), new Font("Meiryo UI", Font.PLAIN, 18));
		dialog.setBounds(800, 400, 500, 150);
		dialog.setVisible(true);
	}

	public static void NotOK() {
		JOptionPane optionPane = new JOptionPane("正しい書籍を選択してください", JOptionPane.ERROR_MESSAGE);
		JDialog dialog = optionPane.createDialog(null, "エラー_1010");
		setFont(dialog.getContentPane().getComponents(), new Font("Meiryo UI", Font.PLAIN, 18));
		dialog.setBounds(800, 400, 500, 150);
		dialog.setVisible(true);

	}

	public static void DateCheck() {
		JOptionPane optionPane = new JOptionPane("正しい日付を入力してください", JOptionPane.ERROR_MESSAGE);
		JDialog dialog = optionPane.createDialog(null, "エラー_1020");
		setFont(dialog.getContentPane().getComponents(), new Font("Meiryo UI", Font.PLAIN, 18));
		dialog.setBounds(800, 400, 500, 150);
		dialog.setVisible(true);

	}

	public static void CannotRsv() {
		JOptionPane optionPane = new JOptionPane("その日付では貸出が行えません", JOptionPane.ERROR_MESSAGE);
		JDialog dialog = optionPane.createDialog(null, "エラー_1030");
		setFont(dialog.getContentPane().getComponents(), new Font("Meiryo UI", Font.PLAIN, 18));
		dialog.setBounds(800, 400, 400, 150);
		dialog.setVisible(true);
	}

	public static void hankaku() {
		JOptionPane optionPane = new JOptionPane("日付は半角数字で入力してください", JOptionPane.ERROR_MESSAGE);
		JDialog dialog = optionPane.createDialog(null, "エラー_1040");
		setFont(dialog.getContentPane().getComponents(), new Font("Meiryo UI", Font.PLAIN, 18));
		dialog.setBounds(800, 400, 400, 150);
		dialog.setVisible(true);
	}

	public static void cntover() {
		JOptionPane optionPane = new JOptionPane("文字数制限を超えている項目があります", JOptionPane.ERROR_MESSAGE);
		JDialog dialog = optionPane.createDialog(null, "エラー_1060");
		setFont(dialog.getContentPane().getComponents(), new Font("Meiryo UI", Font.PLAIN, 18));
		dialog.setBounds(800, 400, 400, 150);
		dialog.setVisible(true);
	}

	public static void numcheck() {
		JOptionPane optionPane = new JOptionPane("定価は半角数字で8桁以内で入力してください", JOptionPane.ERROR_MESSAGE);
		JDialog dialog = optionPane.createDialog(null, "エラー_1070");
		setFont(dialog.getContentPane().getComponents(), new Font("Meiryo UI", Font.PLAIN, 18));
		dialog.setBounds(800, 400, 500, 150);
		dialog.setVisible(true);
	}

	public static void noSelect() {
		JOptionPane optionPane = new JOptionPane("書籍が選択されていません", JOptionPane.ERROR_MESSAGE);
		JDialog dialog = optionPane.createDialog(null, "エラー_1080");
		setFont(dialog.getContentPane().getComponents(), new Font("Meiryo UI", Font.PLAIN, 18));
		dialog.setBounds(800, 400, 400, 150);
		dialog.setVisible(true);
	}

}