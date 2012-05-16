package zzzubec.dev;

import org.bukkit.plugin.java.JavaPlugin;

/*
 * create by ZZZubec
 * @modifed: 14/05/2012
 */

public class LogSystem
{
	private static JavaPlugin main;
	public int debug = 0;
	/*
	 * 0 - only errors
	 * 1 - errors & warnings
	 * 2 - all messages
	 */

	public LogSystem( JavaPlugin jp )
	{
		main = jp;
	}
	
	public void showMessage( String function, String str )
	{
		debugMessage( 2, function, str );
	}
	
	public void warnMessage( String function, String str )
	{
		debugMessage( 1, function, str );
	}
	
	public void errorMessage( String function, String str )
	{
		debugMessage( 0, function, str );
	}
	
	public void debugMessage( int deb, String function, String str )
	{
		String msg = "";
		if( deb == 0 )
			msg += "!!!";
		if( deb == 1 )
			msg += "???";
		
		if( debug >= deb )
		{
				System.out.println( "[" + main.getDescription().getName() + "]: " + function + "->" + str );
		}
	}

}
