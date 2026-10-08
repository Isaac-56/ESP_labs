package com.site;
import java.sql.*;
public class App
{
    private static final String URL = "jdbc:mysql://localhost:3306/studentDB";
    private static final String USER = "root";
    private static final String PASSWORD = "abebebesobela";

    public static void main( String[] args )
    {
        System.out.println("connecting DataBase");
        try(Connection connection = DriverManager.getConnection(URL,USER, PASSWORD)){
            System.out.println("Connection successful");
        }
        catch(SQLException e){
            System.out.println( "Connection failed" );
        }

    }
}
