package zzzubec.dev;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
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
    private JavaPlugin main;
    private static LogSystem log;
    //
    public FileConfig( JavaPlugin ch, LogSystem log )
    {
        this.main = ch;
        this.log = log;
    }
    
    public void readFromFile( File f )
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
            }
            in.close();
        } catch( IOException e ) {
            log.warnMessage( "ConfigFile:readFromFile", "Error read config file" );
        }
        
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
