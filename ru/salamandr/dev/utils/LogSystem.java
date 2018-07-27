package ru.salamandr.dev.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.*;

/*
 * create by ZZZubec
 * @created: 14/05/2012
 * @modifed: 22/12/2012
 */

public class LogSystem
{
	public int debug = 1;
	public boolean writeOut = true;
	public static Logger log_console;
	FileHandler fh;
	/*
	 * 0 - only errors
	 * 1 - errors & warnings
	 * 2 - all messages
	 */

	public LogSystem( String windowName, String fileName ) {
        log_console = Logger.getLogger( windowName );
        try {
			fh = new FileHandler( fileName );
			log_console.addHandler(fh);
			fh.setFormatter(new Formatter() {
				@Override
				public String format(LogRecord record) {
					SimpleDateFormat logTime = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
	                Calendar cal = new GregorianCalendar();
	                cal.setTimeInMillis(record.getMillis());
	                return ""
	                        + logTime.format(cal.getTime())
	                        + " || "
	                        + record.getMessage() + "\n";
				}
	        });
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch ( FileNotFoundException e ) {
			writeOut = true;
            log_console = null;
			errorMessage( "LogSystem", "MAIN", "ERROR: " + e.getMessage() );
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public void showMessage( String plugin, String function, String str )
	{
		debugMessage( plugin, 2, function, str );
	}

	public void warnMessage( String plugin, String function, String str )
	{
		debugMessage( plugin, 1, function, str );
	}

	public void errorMessage( String plugin, String function, String str )
	{
		debugMessage( plugin, 0, function, str );
	}

	public String debugMessage( String plugin, int deb, String function, String message )
	{
		String msg = "debug";
		if( deb == 0 )
			msg += "!!!";
		if( deb == 1 )
			msg += "???";

		if( debug >= deb )
		{
            //SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "hh:mm:ss" );
            //message = "{" + plugin + "}[" + msg + "] " + simpleDateFormat.format(d) + ": " + function + "->" + str;
            //message = "{" + plugin + "}[" + msg + "] " + function + "()->" + str;
            if( writeOut || log_console == null )
            	System.out.println( message );

            /*
			if( deb == 0 ) log_console.log( Level.SEVERE, message );
			if( deb == 1 ) log_console.warning( message );
			if( deb == 2 ) log_console.info( message );
			*/

            if( log_console != null ) {
                if (deb == 0)
                    log_console.log(Level.WARNING, message);
                else
                    log_console.log(Level.INFO, message);
            }
		}
		return message;
	}

}
