package ru.salamandr.dev.utils;

/*
 * create by ZZZubec
 * @modifed: 14/05/2012
 */


import java.io.BufferedWriter;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import ru.salamandr.dev.utils.LogSystem;

/**
 *
 * @author ZZZubec
 */
public class FileConfig {

    final String version = "1.0.1";

    protected Vector<String> keys = new Vector<String>();
    protected Vector<String> values = new Vector<String>();
    protected Vector<String> descs = new Vector<String>();
    protected LogSystem log;
    //
    public FileConfig( LogSystem log )
    {
        this.log = log;
    }
    
    public void addKeyValue( String key, String value, String desc )
    {
    	keys.add( key );
        values.add( value );
        descs.add( desc );
    }
    
    public void addKeyValue( String key, int value, String desc )
    {
    	keys.add( key );
        values.add( ""+value );
        descs.add( desc );
    }
    
    public void addKeyValue(String key, int value) {
		addKeyValue( key, value, "" );
	}
    
    public void addKeyValue(String key, String value) {
		addKeyValue( key, value, "" );
	}
    
    public void setValue( String key, String value )
    {
        for( int i = 0; i < keys.size(); i++ )
        {
            if( keys.elementAt(i).equals(key) )
            {
                values.setElementAt( value, i );
                return;
            }
        }
    }
    
    protected boolean readFromFile( String pluginFolder, String filename )
    {
    	File f = new File( pluginFolder );
    	f.mkdirs();
    	f = new File( pluginFolder + "/" + filename );
        if( !f.exists() )
            f.mkdirs();
    	return readFromFile( f );
    }
    
    public boolean readFromFile( File f )
    {
        try
        {
            BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                new FileInputStream(f) ) );
            String line;
            while( (line = in.readLine()) != null )
            {
                if( !line.substring(0, 2).equals("//") )
                {
                    String[] args = line.split(":");
                    if( args.length >=2 )
                    {
                        keys.add( args[0].trim() );
                        values.add( args[1].trim() );
                    }
                    else
                    {
                        args = line.split("\\=");
                        if( args.length >=2 )
                        {
                            keys.add( args[0].trim() );
                            values.add( args[1].trim() );
                        }
                    }
                    //System.out.println( "[" + args[0].trim() + "]=" + args[1].trim() + ";" );
                }
                else
                	descs.add( line.substring( 2, line.length() ) );
            }
            in.close();
        } catch( IOException e ) {
            log.warnMessage( this.getClass().getSimpleName(), "ConfigFile:readFromFile", "Error read config file" );
            return false;
        }
        return true;
        
    }
   
    public boolean writeToFile( String pluginFolder, String filename )
    {
    	File f = new File( pluginFolder );
    	f.mkdirs();
        try{
    		  // Create file 
    		  FileWriter fstream = new FileWriter( pluginFolder + "/" + filename );
    		  BufferedWriter out = new BufferedWriter(fstream);
    		  for( int i=0; i < keys.size(); i++ )
    		  {
                  if( descs.get(i) != null && !descs.get(i).isEmpty() )
    			    out.write( "//" + descs.get( i ) + "\r\n" );
    			  out.write( keys.get( i ).replace( "null", "") + ": " + values.get( i ) + "\r\n" );
    		  }
    		  //Close the output stream
    		  out.close();
    		  fstream.close();
    		  log.showMessage( this.getClass().getSimpleName(), "FileConfig:writeToFile", "Configuration file save" );
        }catch (Exception e){//Catch exception if any
        	log.errorMessage( this.getClass().getSimpleName(), "FileConfig:writeToFile", e.getMessage());
        	return false;
        }
        return true;
        
    }
    
    public boolean getBoolean( String key )
    {
        for( int i = 0; i < keys.size(); i++ )
        {
            if( keys.elementAt(i).equals(key) )
            {
                if( values.elementAt(i).equals( "yes" ) )
                    return true;
                else
                    return false;
            }
        }
        return false;
    }
    
    public int getNumber( String key )
    {
        for( int i = 0; i < keys.size(); i++ )
        {
            if( keys.elementAt(i).equals(key) )
            {
                return Integer.parseInt( values.elementAt(i) );
            }
        }
        return -1;
    }
    
    
    public float getFloat( String key )
    {
        for( int i = 0; i < keys.size(); i++ )
        {
            if( keys.elementAt(i).equals(key) )
            {
                return Float.parseFloat( values.elementAt(i) );
            }
        }
        return -1;
    }
    
    public String getString( String key )
    {
        for( int i = 0; i < keys.size(); i++ )
        {
            if( keys.elementAt(i).equals(key) )
            {
                return values.elementAt(i);
            }
        }
        return null;
    }
    
    public List<String> getArray( String key )
    {
    	List<String> as = new ArrayList<String>();
        for( int i = 0; i < keys.size(); i++ )
        {
        	//PluginSystem.out.println( i + ") Find [" + key + "] " + keys.elementAt(i) + "=" + values.elementAt(i) );
            if( keys.elementAt(i).length() >= key.length() && keys.elementAt(i).substring( 0, key.length() ).equals(key) )
            {
            	//PluginSystem.out.println( "find" );
            	as.add( keys.elementAt(i).replace( key, "" ) );
            }
        }
        return as;
    }
    
    public boolean has( String key )
    {
        for( int i = 0; i < keys.size(); i++ )
        {
            if( keys.elementAt(i).equals(key) )
            {
                return true;
            }
        }
        return false;
    }

    public boolean exists(String s) {
        if( keys.contains( s ) )
            return true;
        return false;  //To change body of created methods use File | Settings | File Templates.
    }
}
