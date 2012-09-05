package zzzubec.dev;

import java.util.logging.Logger;
import jline.internal.Log.Level;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * create by ZZZubec
 * @modifed: 14/05/2012
 */

@SuppressWarnings("unused")
public class LogSystem
{
	private static JavaPlugin main;
	public int debug = 2;
	private static Logger log_console = Logger.getLogger( "Minecraft" );
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
			String message = "[" + main.getDescription().getName() + "]: " + function + "->" + str;
			//System.out.println( msg + " " + message );
			
			if( deb == 0 ) log_console.log( java.util.logging.Level.WARNING, message );
			if( deb == 1 ) log_console.warning( message );
			if( deb == 2 ) log_console.info( message );
		}
	}

}
