package school5;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * 
 * @author lyf
 * @time 2015.11.04
 * @测试结果：加密结果    ZTJlNGU1OGU5MTBlMjU1YTE2OTY1MTM0MzQ2ZWY0MTg=  没问题
 *
 */
public class TestDigestUtils {

	public static void main(String args[]){
		
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(date);
		String time2 = "2015-09-18";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date1 = null;
		java.util.Date date2 = null;
		try {
			date1 =  sdf.parse(time);
			date2 =  sdf.parse(time2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		System.out.println("date1======"+date1);
		System.out.println("date2======"+date2);
	}
}
