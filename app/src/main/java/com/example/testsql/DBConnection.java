package com.example.testsql;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection{
    private static String url = "jdbc:mysql://mysql-11308-0.cloudclusters.net:11308/Onetdb";
    private static String user = "onetuser";
    private static String pass = "1234";
    private static Connection connection;

    public static Connection getConnection(){
        if(connection== null)
        {
            try
            {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                connection = DriverManager.getConnection(url,user,pass);
            }catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        return connection;
    }

}
