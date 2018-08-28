package resources;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class SystemConfiguration {

	
	public static String getDate()
	{
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
		//DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date date = new Date();
		return (dateFormat.format(date)); //2016/11/16 12:08:43
		//return"08/01/2018";
		
	}
}
