package studmine.library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * StudConnection
 * Connect to the database and manage the requests
 * @author Caligone
 * @version 0.1
 */
public class StudConnection
{
          private Connection connection = null;
          private static StudConnection studConnection = null;

          /**
           * StudConnection constructor
           * Private to keep the uncity of the database connection
           * @param String url contains the url to the database (format : jdbc:mysql://address:port/database)
           * @param String user contains the name of the login
           * @param String password contains the password
           */
          private StudConnection(String url, String user, String password)
          {
              try 
              {
                  Class.forName("com.mysql.jdbc.Driver");
                  this.connection = DriverManager.getConnection(url, user, password);
              }
              catch (Exception e)
              {
                  e.printStackTrace();
              }
          }

          /**
           * getStudConnection method permit to get a StudConnection object
           * @param String url : Contains the url to the database (format : jdbc:mysql://address:port/database)
           * @param String user : Contains the name of the login
           * @param String password : Contains the password
           * @return StudConnection object
           */
          public static StudConnection getStudConnection(String url, String user, String password)
          {
              if(StudConnection.studConnection != null)
                  return StudConnection.studConnection;
              StudConnection.studConnection = new StudConnection(url, user, password);
              return StudConnection.studConnection;
          }
          
          
          /**
           * sendData method permit to update, insert or delete data to the database
           * @param String sql contains the SQL Request
           * @return the row count or -1 if error appends
           */
          public int sendData(String sql)
          {
              try
              {
                  return this.connection.prepareStatement(sql).executeUpdate(sql);
              }
              catch (SQLException e)
              {
                  e.printStackTrace();
                  return -1;
              }
          }
          
          /**
           * getData method permit get data from the database
           * @param String sql contains the SQL Request
           * @return ResultSet contains the data requested
           */
          public ResultSet getData(String sql)
          {
              try
              {
                  return this.connection.prepareStatement(sql).executeQuery(sql);
              }
              catch (SQLException e)
              {
                  e.printStackTrace();
                  return null;
              }
          }
          
}