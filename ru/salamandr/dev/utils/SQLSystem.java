/*
 * create by ZZZubec
 * @create: 16/11/2012
 * @modifed: 21/12/2012
 */
package ru.salamandr.dev.utils;

import java.sql.*;

import ru.salamandr.dev.utils.LogSystem;

public class SQLSystem
{
    private LogSystem log;
    private Statement sql;

    private DataBaseConfig db;

    private Connection conn = null;


    public EnumSQL sqlType = EnumSQL.SQLLite;

	public enum EnumSQL
	{
		SQLLite, MySQL;
	}

    public SQLSystem( LogSystem log, DataBaseConfig db, EnumSQL type )
    {
        this.log = log;
        this.db = db;
        this.sqlType = type;
    }

    public boolean connectSQL()
    {
        try
		{
		    log.showMessage( this.getClass().getSimpleName(), "connectSQL", "initialize" );
            if( sqlType == EnumSQL.SQLLite )
            {
            	Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection("jdbc:sqlite:" + this.db.file );
                sql = conn.createStatement();
                if( sql != null )
                    return true;
            }
            else
            {
            	Class.forName("org.gjt.mm.mysql.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://" + this.db.host + ":" + this.db.port + "/" + this.db.name, this.db.username, this.db.password );
                sql = conn.createStatement();
                if( sql != null )
                    return true;
            }
		} catch (SQLException e) {
		    log.errorMessage( this.getClass().getSimpleName(), "connectSQL->SQLException", e.getMessage() );
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
		    log.errorMessage( this.getClass().getSimpleName(), "connectSQL->Exception", e.getMessage() );
			e.printStackTrace();
		}
        return false;
    }

    public ResultSet query( String query )
    {
        ResultSet rs = null;

        if( sql == null )
        {
            if( !this.connectSQL() ) return rs;
        }

        try
        {
            log.showMessage( this.getClass().getSimpleName(), "SQLSystem->query", query );
            return sql.executeQuery( query );
        } catch (SQLException e) {
            log.errorMessage( this.getClass().getSimpleName(), "SQLSystem->query", e.getMessage() );
			e.printStackTrace();
        }
        return rs;
    }

    public boolean update( String query )
    {
        if( sql == null )
        {
            if( !this.connectSQL() ) return false;
        }

        try
        {
            log.showMessage( this.getClass().getSimpleName(), "SQLSystem->update", query );
            sql.executeUpdate( query );
            return true;
        } catch (SQLException e) {
            log.errorMessage( this.getClass().getSimpleName(), "SQLSystem->query", e.getMessage() );
			e.printStackTrace();
        }
        return false;
    }

	public void close() {
		// TODO Auto-generated method stub
		try {
			sql.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
        return conn;
    }

    public ResultSet getGeneratedKeys() throws SQLException {
        ResultSet getGeneratedKeys = sql.getGeneratedKeys();
        if (getGeneratedKeys == null)
            getGeneratedKeys = conn.prepareStatement( "select last_insert_rowid();").executeQuery();
        return getGeneratedKeys;
    }
}
