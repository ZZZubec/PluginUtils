package ru.salamandr.dev.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Options {
	
	LogSystem log;
	
	public HashMap<String,String> values = new HashMap<String,String>();
	
	public Options(LogSystem log, String mod_config_file) {
		this.log = log;
		loadFromFile( mod_config_file );
	}
	
	public Options() {
		// TODO Auto-generated constructor stub
	}

	public String getValue( String key ) {
		return getValue( key, key );
	}
	
	public void setKeyValue( String key, String value ) {
		
		if( values.get( key.toLowerCase() ) == null )
			values.put( key.toLowerCase(), value );
		
	}

	public String getValue( String key, String def ) {
		
		String value = values.get( key.toLowerCase() );
		if( value != null && !value.isEmpty() && !value.equals("null") )
			return values.get( key.toLowerCase() );
		else
			return def;
	}
	
	public void loadFromFile( LogSystem log, String filename ) {
		this.log = log;
		loadFromFile( filename );
	}
	
	public void loadFromFile( String filename ) {
		
		values.clear();
		InputStream input = this.getClass().getResourceAsStream( filename );
		if( input != null )
		{
			try {
				BufferedReader reader = new BufferedReader( new InputStreamReader( input ) );
				String line;
			
				while ((line = reader.readLine()) != null) {
					line = line.trim();
				   if ( line.contains( "=" ) ) {
					   String[] cols = line.split( "\\=" );
					   setKeyValue( cols[0].trim(), cols[1].trim().replace( "###", " " ) );
				   }
				}
				reader.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.errorMessage( this.getClass().getName(), "loadFromFile", e.getMessage() );
				System.out.println( ">>>read language error: " );
				e.printStackTrace();
			}
		}
		
	}

}
