/*
 * create by ZZZubec
 * @create: 16/11/2012
 * @modifed: 21/12/2012
 */
package ru.salamandr.dev.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLSystem
{
    public Statement statement;

    private DataBaseConfig db;


    public EnumSQL sqlType = EnumSQL.SQLLite;

    /*
    public int getInstertID() {
        ResultSet rs = query("SELECT LAST_INSERT_ID();");
        try {
            while (rs != null && rs.next()) {
                return rs.getInt(0);
            }
        } catch (SQLException ex) {
            LogSystem.getInstance().errorMessage(this.getClass().getSimpleName(), "connectSQL->SQLException", ex.getMessage());
        }
        return -1;
    }
    */

    public enum EnumSQL
	{
		SQLLite, MySQL;
	}

    public SQLSystem( DataBaseConfig db, EnumSQL type )
    {
        this.db = db;
        this.sqlType = type;
    }

    public boolean connectSQL()
    {
        Connection conn = null;
        try
		{
            LogSystem.getInstance().showMessage( this.getClass().getSimpleName(), "connectSQL", "initialize" );
            if( sqlType == EnumSQL.SQLLite )
            {
            	conn = DriverManager.getConnection("jdbc:sqlite:" + this.db.file );
                statement = conn.createStatement();
                if( statement != null )
                    return true;
            }
            else
            {
                conn = DriverManager.getConnection("jdbc:mysql://" + this.db.host + ":" + this.db.port + "/" + this.db.name, this.db.username, this.db.password );
                statement = conn.createStatement();
                if( statement != null )
                    return true;
            }
		} catch (SQLException e) {
            LogSystem.getInstance().errorMessage( this.getClass().getSimpleName(), "connectSQL->SQLException", e.getMessage() );
			e.printStackTrace();
		}
        return false;
    }

    public ResultSet query( String query )
    {
        ResultSet rs = null;

        if( statement == null )
        {
            if( !this.connectSQL() ) return rs;
        }

        try
        {
            LogSystem.getInstance().showMessage( this.getClass().getSimpleName(), "SQLSystem->query", query );
            return statement.executeQuery( query );
        } catch (SQLException e) {
            LogSystem.getInstance().errorMessage( this.getClass().getSimpleName(), "SQLSystem->query", e.getMessage() );
			e.printStackTrace();
        }
        return rs;
    }

    public boolean update( String query )
    {
        if( statement == null )
        {
            if( !this.connectSQL() ) return false;
        }

        try
        {
            LogSystem.getInstance().showMessage( this.getClass().getSimpleName(), "SQLSystem->update", query );
            statement.executeUpdate( query );
            return true;
        } catch (SQLException e) {
            LogSystem.getInstance().errorMessage( this.getClass().getSimpleName(), "SQLSystem->query", e.getMessage() );
			e.printStackTrace();
        }
        return false;
    }

	public void close() {
		// TODO Auto-generated method stub
		try {
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
