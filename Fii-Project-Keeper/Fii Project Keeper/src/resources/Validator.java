package resources;

public class Validator {

	public static boolean validateSqlInjection(String word)
	{
		if((word!=null)&&(word.contains("'")))
				return false;
			return true;
	}
	
	public static boolean validateContaining(String input, String... args)
	{
		if(input!=null)
			for(int i=0;i<args.length;i++)
				if(input.contains(args[i]))
					return false;
		return true;
	}
	
}
