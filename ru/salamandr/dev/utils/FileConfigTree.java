package ru.salamandr.dev.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileConfigTree extends FileConfig {

	int tabstep = 2;
	public EnumConfig configType = EnumConfig.TREE;

    public enum EnumConfig
	{
		ONE_LINE,
		TREE;
	}

	public FileConfigTree()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void clear()
	{
		this.keys.clear();
		this.values.clear();
		this.descs.clear();
	}

    public boolean readFromFileJAR( String filename )
    {
        if( this.configType == EnumConfig.ONE_LINE )
        {
            InputStream input = this.getClass().getResourceAsStream( filename );
            if( input != null )
            {
                try {
                    BufferedReader reader = new BufferedReader( new InputStreamReader( input ) );

                    String line;
                    while( (line = reader.readLine()) != null )
                    {
                        if( !line.substring(0, 2).equals("//") )
                        {
                            String[] args = null;
                            if( line.split(":").length > line.split("=").length )
                                args = line.split("\\:");
                            else
                                args = line.split("\\=");
                            keys.add( args[0].trim() );
                            values.add( args[1].trim() );
                            //System.out.println( "[" + args[0].trim() + "]=" + args[1].trim() + ";" );
                        }
                        else
                            descs.add( line.substring( 2, line.length() ) );
                    }
                    reader.close();

                    return true;

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    LogSystem.getInstance().errorMessage( this.getClass().getName(), "loadFromFileJAR", e.getMessage() );
                    System.out.println( ">>>read language error: " );
                    e.printStackTrace();
                }
            }
        }
        else
        {
            InputStream input = this.getClass().getResourceAsStream( filename );
            if( input != null )
            {
                BufferedReader reader = new BufferedReader( new InputStreamReader( input ) );

                return readFromFile( "", filename, reader );

            }
        }
        return false;
    }

    @Override
    public boolean readFromFile( String pluginFolder, String filename )
    {
        return readFromFile( pluginFolder, filename, null );
    }

	public boolean readFromFile( String pluginFolder, String filename, BufferedReader in )
	{
		if( this.configType == EnumConfig.ONE_LINE )
			return super.readFromFile( pluginFolder, filename );
		else
		{
			File f = new File( pluginFolder );
	    	f.mkdirs();
	    	f = new File( pluginFolder + "/" + filename );
	    	try
	        {
                if( in == null )
                {
                    in = new BufferedReader(
                            new InputStreamReader(
                            new FileInputStream(f) )
                    );
                }
	            String line;
	            String path = "";
	            int current_level = 0;
	            String current_node = "";
	            boolean need_gets_values = false;
	            
	            boolean is_first = false;
	            String value = "";
        		String desc = "";
        		
        		boolean last_list = false;
	            
	            while( (line = in.readLine()) != null )
	            {
                    if( !line.trim().isEmpty() )
                    {
                        if( need_gets_values )
                        {
                            //System.out.println( "-----------------------------------" );
                            //System.out.println( "---PATH:" + path + "|node:" + current_node + "|level:" + current_level );
                            if( is_first )
                            {
                                value = "";
                                desc = "";
                                //System.out.println( "getNodeVaules" );
                                if( getLevel( line ) > current_level )
                                {
                                    String[] args = line.trim().split( "//" );
                                    if( args.length > 1 )
                                        value = args[0].trim();
                                    else
                                        value = line.trim();
                                }
                                is_first = false;
                            }
                            else
                            {
                                //System.out.println( "Line:" + line + "|Level:" + current_level + "|" + getLevel( line ) );
                                if( getLevel( line ) > current_level )
                                {
                                    String[] args = line.trim().split( "//" );
                                    if( args.length > 1 )
                                    {
                                        value += "," + args[0].trim();
                                        if( !desc.equals( args[1] ) )
                                            desc = args[1];
                                    }
                                    else
                                        value += "," + line.trim();
                                }
                                else
                                {
                                    if( !last_list )
                                    {
                                        String[] cols = path.split("\\.");
                                        cols[cols.length-1] = "";
                                        path = join(cols, ".").replace( "..", "" );
                                        last_list = true;
                                    }
                                    else
                                        last_list = false;

                                    this.keys.add( path + current_node );
                                    this.values.add( value );
                                    this.descs.add( desc );
                                    need_gets_values = false;
                                    //System.out.println( "Add key:" + path+current_node + "|value:" + value );
                                }
                            }
                        }
                        if( !need_gets_values )
                        {
                            if( !line.substring(0, 2).equals("//") )
                            {
                                //System.out.println( "-----------------------------------" );
                                String[] args = line.split(":");
                                line += " ";
                                String[] args3 = line.split(":");
                                if( args.length > 1 )
                                {
                                    if( args.length > 2 )
                                    {
                                        String t = args[1];
                                        for( int i = 2; i < args.length; i++ )
                                            t += ":" + args[i];
                                        args[1] = t;
                                    }

                                    if( args[0].charAt(0) != ' ' )
                                    {
                                        path = "";
                                        current_level = 0;
                                    }

                                    String[] args2 = args[1].split("//");
                                    //System.out.println( line + "|length:" + args.length + "|length2:" + args2.length );
                                    last_list = false;
                                    keys.add( path + args[0].trim() );
                                    if( args2.length <= 1 )
                                    {
                                        //System.out.println( "Add key:" + path + args[0].trim() + "=" + args[1].trim() );
                                        values.add( args2[0].trim() );
                                    }
                                    else
                                    {
                                        //System.out.println( "Add key:" + path + args[0].trim() + "=" + args2[0].trim() );
                                        values.add( args2[0].trim() );
                                        descs.add( args2[1].trim() );
                                    }
                                }
                                else
                                {
                                    //System.out.println( line + "|length:" + args.length );
                                    if( line.charAt(0) != ' ' )
                                    {
                                        path = "";
                                        current_level = 0;
                                    }
                                    int level = getLevel( args[0] );
                                    if( current_level < level || current_level == 0 )
                                        path += args[0].trim() + ".";
                                    else
                                    {
                                        //System.out.println( "Extract:" + current_level + "|" + getLevel( args[0] ) );
                                        if( current_level > getLevel( args[0] ) )
                                            path = replacePath( path, args[0], current_level, getLevel( args[0] ) );
                                    }
                                    current_node = args[0].trim();
                                    current_level = getLevel( args[0] );
                                    if( args3.length > 1 )
                                    {
                                        current_node = args3[0].trim();
                                        current_level = getLevel( args3[0] );
                                        //path += current_node;
                                        need_gets_values = true;
                                        is_first = true;
                                    }
                                    //System.out.println( "PATH:" + path + "|node:" + current_node + "|level:" + current_level );
                                    //System.out.println( "need_gets_values:" + need_gets_values );
                                }
                                //System.out.println( "[" + args[0].trim() + "]=" + args[1].trim() + ";" );
                            }
                            else
                                descs.add( line.substring( 2, line.length() ) );
                        }
                    }
	            }
	            in.close();
	        } catch( IOException e ) {
                LogSystem.getInstance().warnMessage( null, "ConfigFile:readFromFile", "Error read config file" );
	            e.printStackTrace();
	            return false;
	        }
		}
		return true;
		//
	}
	
	private String join(String[] cols, String string) {
		// TODO Auto-generated method stub
		String str = "";
		for( int i = 0; i < cols.length; i++ )
		{
			str += cols[i];
			if( i+1 < cols.length )
				str += string;
		}
		return str;
	}

	private String replacePath(String path, String newnode, int current_level,
			int level)
	{
		// TODO Auto-generated method stub
		System.out.println( "replacePath:" + path + "|node:" + newnode );
		String[] cols1 = (path + " ").split( "\\." );
		String newpath = "";
		
		for( int i = level; i <= current_level; i++ )
			cols1[i-1] = "";

		newpath = join( cols1, "." );
		System.out.println( "replace path:" + path + "|" + newpath );
		return newpath;
	}

	private int getLevel(String nodeee)
	{
		// TODO Auto-generated method stub
		String node = nodeee.replace( nodeee.trim(), "" );
		int res = node.length()/this.tabstep;
		//System.out.println( nodeee + "->getLevel(" + res + ")" );
		return res;
	}

    @Override
	public boolean writeToFile( String pluginFolder, String filename )
	{
		if( this.configType == EnumConfig.ONE_LINE )
			return super.writeToFile( pluginFolder, filename );
		else
		{
			File f = new File( pluginFolder );
	    	f.mkdirs();
            String[] val = null;
	        try{
	    		  // Create file 
	    		  FileWriter fstream = new FileWriter( pluginFolder + "/" + filename );
	    		  BufferedWriter out = new BufferedWriter(fstream);
	    		  String current_path = "";
	    		  boolean is_first = true;
	    		  boolean is_List = false;
	    		  String path = "";
	    		  for( int i=0; i < keys.size(); i++ ) {
                      is_List = false;
                      String[] cols = keys.get(i).split("\\.");
                      //System.out.println( i+") " + keys.get(i) );
                      //System.out.println( "---level (cols): " + cols.length );
                      path = getPath(cols);
                      //System.out.println( "---path: " + current_path + "|" + path );
                      if (!current_path.equals(path)) {
                          is_first = true;
                          is_List = false;
                          //out.write( writePath( path ) );
                          //current_path = path;
                      }

                      //Если следующие значение по этому же пути, то
                      //System.out.println( "---first: " + is_first + "| list:" + is_List );
                      if ((i + 1 < keys.size() && keys.get(i + 1).equals(keys.get(i))) || (i > 0 && keys.get(i - 1).equals(keys.get(i))))
                          is_List = true;

                      //System.out.println( "---first: " + is_first + "| list:" + is_List );
                      if (!is_List && values.get(i) != null && values.get(i).split("\\,").length > 1) {
                          is_List = true;
                      }

                      //System.out.println( "---first: " + is_first + "| list:" + is_List );

                      if (is_List) {
                          //Это список

                          if (!current_path.equals(path))
                              out.write(writePath(path));

                          if (is_first) {
                              //Это самое первое значение списка
                              if (cols.length > 1) {
                                  if (values.get(i).split("\\,").length > 1 || is_List)
                                      out.write(getSpaces(cols.length) + cols[cols.length - 1] + ":\r\n");
                                  else
                                      out.write(getSpaces(cols.length) + cols[cols.length - 1] + ":");
                              }
                              is_first = false;
                          }
                          if (values.get(i).split("\\,").length > 1) {
                              //System.out.println( "---values is list: " + values.get(i) + "|length: " + values.get(i).split(",").length );
                              String[] v = values.get(i).split("\\,");
                              for (int vl = 0; vl < v.length; vl++)
                                  out.write(getSpaces(cols.length + 1) + v[vl].trim() + " //" + descs.get(i) + "\r\n");
                          } else {
                              if (is_List)
                                  out.write(getSpaces(cols.length + 1) + values.get(i) + " //" + descs.get(i) + "\r\n");
                              else
                                  out.write(getSpaces(cols.length) + values.get(i) + " //" + descs.get(i) + "\r\n");
                          }
                      } else {
                          //Если значение не является частью списка
                          if (!current_path.equals(path))
                              out.write(writePath(path));

                          out.write(getSpaces(cols.length) + cols[cols.length - 1] + ": " + values.get(i) + " //" + descs.get(i) + "\r\n");
                      }
                      current_path = path;
                  }
	    		  //Close the output stream
	    		  out.close();
	    		  fstream.close();
	    		  LogSystem.getInstance().showMessage( this.getClass().getSimpleName(), "FileConfig:writeToFile", "Configuration file save" );
	        } catch ( NullPointerException e ) {
                LogSystem.getInstance().errorMessage( this.getClass().getSimpleName(), "FileConfig:writeToFile", e.getMessage() );
                e.printStackTrace();
                return false;
            } catch (Exception e) {//Catch exception if any
                LogSystem.getInstance().errorMessage(this.getClass().getSimpleName(), "FileConfig:writeToFile", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
        return true;
	}

	
	private String writePath(String path)
	{
		// TODO Auto-generated method stub
		String result = "";
		String[] cols = path.split( "\\." );
		for( int i = 0; i < cols.length; i++ )
			result += getSpaces( i+1 ) + cols[i] + "\r\n";

		return result;
	}

	/*
	 * звлечение пути
	 */
	private String getPath(String[] cols)
	{
		// TODO Auto-generated method stub
		//System.out.println( "getPath(" + StringUtils.join( cols, "." ) + ")" );
		if( cols.length <= 1 )
			return "";
		String path = join(cols, '.').replace( "." + cols[cols.length-1], "" );
		return path;
	}
	
	private String join(String[] cols, char c) {
		// TODO Auto-generated method stub
		return join( cols, ""+c );
	}

	private String getPath(String key)
	{
		// TODO Auto-generated method stub
		String[] cols = key.split( "\\." );
		if( cols.length <= 1 )
			return "";
		return getPath( cols );
	}

	public String getSpaces( int level )
	{
		String s = "";
		if( level > 0 )
			level--;
		for( int i = 1; i <= level*this.tabstep; i++ )
			s += " ";
		return s;
	}
	
	public List<String> getRoot()
	{
        List<String> as = new ArrayList<String>();
        if( configType == EnumConfig.ONE_LINE )
        {
            for( int i = 0; i < this.keys.size(); i++ )
            {
                String line = this.keys.get(i);
                as.add( line );
            }
            return as;
        }
        else
        {
            String lastNodeName = "";
            for( int i = 0; i < this.keys.size(); i++ )
            {
                String line = this.keys.get(i);
                String[] cols = line.split( "\\." );
                if( cols.length >= 2 )
                {
                    if( !lastNodeName.equals( cols[0] ) )
                    {
                        lastNodeName = cols[0].trim();
                        as.add( lastNodeName );
                    }
                }
                else
                {
                    lastNodeName = line.trim();
                    as.add( lastNodeName );
                }
            }
        }
		return as;
	}

    public List<String> getRoot( String key )
    {
        List<String> as = new ArrayList<String>();
        String lastNodeName = "";
        for( int i = 0; i < this.keys.size(); i++ )
        {
            String line = this.keys.get(i);
            if( line.substring( 0, key.length() ).equals( key ) )
            {
                if( key.contains( "." ) )
                    as.add( line.trim().replace( key, "" ) );
                else
                    as.add( line.trim().replace( key+".", "" ) );
            }
        }
        return as;
    }

    public ArrayList<String> getContainsKeys(String key) {
        ArrayList<String> as = new ArrayList<String>();
        String lastNodeName = "";
        for( int i = 0; i < this.keys.size(); i++ )
        {
            String line = this.keys.get(i);
            if( line.toLowerCase().contains( key.toLowerCase() ) )
            {
                as.add( line.trim() );
            }
        }
        return as;
    }

    public String getVersion() {
        return version;
    }

}
