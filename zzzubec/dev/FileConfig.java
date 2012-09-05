package zzzubec.dev;

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
import java.util.Vector;

import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author ZZZubec
 */
public class FileConfig
{
    private Vector<String> keys = new Vector<String>();
    private Vector<String> values = new Vector<String>();
    private Vector<String> descs = new Vector<String>();
    @SuppressWarnings("unused")
    private JavaPlugin main;
    private LogSystem log;
    //
    public FileConfig( JavaPlugin ch, LogSystem log )
    {
        this.main = ch;
        this.log = log;
    }
    
    public void addKeyValue( String key, String value, String desc )
    {
    	keys.add( key );
        values.add( value );
        descs.add( desc );
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
    
    public boolean readFromFile( String pluginFolder, String filename )
    {
    	File f = new File( pluginFolder );
    	f.mkdirs();
    	f = new File( pluginFolder + "/" + filename );
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
                    keys.add( args[0].trim() );
                    values.add( args[1].trim() );
                    System.out.println( "[" + args[0].trim() + "]=" + args[1].trim() + ";" );
                }
                else
                	descs.add( line.substring( 2, line.length() ) );
            }
            in.close();
        } catch( IOException e ) {
            log.warnMessage( "ConfigFile:readFromFile", "Error read config file" );
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
    			  out.write( "//" + descs.get( i ) + "\r\n" );
    			  out.write( keys.get( i ) + ": " + values.get( i ) + "\r\n" );
    		  }
    		  //Close the output stream
    		  out.close();
    		  log.showMessage( "FileConfig:writeToFile", "Configuration file save" );
        }catch (Exception e){//Catch exception if any
        	log.errorMessage( "FileConfig:writeToFile", e.getMessage());
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
        return 0;
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
        return "";
    }
    
    public String[] getArray( String key )
    {
        for( int i = 0; i < keys.size(); i++ )
        {
            if( keys.elementAt(i).equals(key) )
            {
                return values.elementAt(i).split(",");
            }
        }
        String[] as = {""};
        return as;
    }
}
