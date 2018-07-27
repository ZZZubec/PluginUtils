/*
 * create by ZZZubec
 * @create: 16/11/2012
 * @modifed: 16/11/2012
 */
package ru.salamandr.dev.utils;

public class DataBaseConfig
{
    public String host, name, username;
    public int port = 3306;
    public String password = "";
    public String file = "";
    
    public DataBaseConfig( String dbfile  )
    {
        this.file = dbfile;
    }

    public DataBaseConfig( String dbhost, int dbport, String dbname, String dbuser, String dbpassword  )
    {
        this.init(dbhost, port, dbname, dbuser, dbpassword, "");
    }

    public DataBaseConfig( String dbhost, int dbport, String dbname, String dbuser, String dbpassword, String dbfile  )
    {
        this.init(dbhost, dbport, dbname, dbuser, dbpassword, dbfile);
    }

    public DataBaseConfig( String dbhost, String dbname, String dbuser, String dbpassword  )
    {
        this.init(dbhost, port, dbname, dbuser, dbpassword, "");
    }

    public DataBaseConfig( String dbhost, String dbname, String dbuser, String dbpassword, String dbfile  )
    {
        this.init(dbhost, port, dbname, dbuser, dbpassword, dbfile);
    }

    public DataBaseConfig( String dbhost, String dbname, String dbuser  )
    {
        this.init(dbhost, port, dbname, dbuser, password, file);
    }

    private DataBaseConfig init( String dbhost, int dbport, String dbname, String dbuser, String dbpassword, String dbfile )
    {
        this.host = dbhost;
        this.port = dbport;
        this.name = dbname;
        this.username = dbuser;
        this.password = dbpassword;
        this.file = dbfile;
        return this;
    }
    
    public String toString()
    {
        String str = (new StringBuilder()).append( "\r\ndbhost: " ).append( this.host ).append( "\r\ndbport: " ).append( this.port ).append( "\r\ndbname: " ).append( this.username ).append( "\r\ndbpassword: " ).append( this.password ).append( "\r\ndbfile: ").append( this.file ).toString();
        return str;
    }
}
