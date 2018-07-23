package resources;

public class Validator {

	public static boolean validateSqlInjection(String word)
	{
		if(word.contains("'"))
			return false;
		return true;
	}
	
}
