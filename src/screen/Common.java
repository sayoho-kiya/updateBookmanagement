package screen;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {

	//本日の日付取得
	static Date today = new Date(); //java.util.Date型
	static SimpleDateFormat DataFmt = new SimpleDateFormat("yyyy-MM-dd");
	static String Today = DataFmt.format(today);

}
