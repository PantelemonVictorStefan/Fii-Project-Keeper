package resources;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;

public class Validator {

	
	public static boolean stringContains(String input, String... args)
	{
		if(input!=null)
			for(int i=0;i<args.length;i++)
				if(input.contains(args[i]))
					return true;
		return false;
	}
	

	
}
