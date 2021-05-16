/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.github.javafaker.Faker;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 *
 * @author leon
 */
public class DatabaseUtils {

    private static DataSource dataSource = null;

    private static Connection getConnection() {
        try {
            if (dataSource != null) {
                return dataSource.getConnection();
            }
            Context ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("jdbc/lms");
            return dataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Boolean execute(String sql) {
        try {
            Connection conn = getConnection();
            Statement statement = conn.createStatement();
            statement.execute(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static PreparedStatement getPreparedStatement(String sql) {
        try {
            Connection conn = getConnection();
            return conn.prepareStatement(sql);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void createUsersTable() {
//        create table users(
//                userID INT NOT NULL GENERATED ALWAYS AS IDENTITY
//        ,
//name varchar
//        (20),
//surname varchar
//        (20),
//email varchar
//        (50),
//password varchar
//        (32),
//type varchar(10) not null,
//title varchar
//        (50),
//birthDate date,
//                country varchar(15)
//    )
        String sql = "create table users\n"
                + "(\n"
                + "userID INT NOT NULL GENERATED ALWAYS AS IDENTITY,\n"
                + "name varchar(20),\n"
                + "surname varchar(20),\n"
                + "email varchar(50),\n"
                + "password varchar(32),\n"
                + "type varchar(10) not null,\n"
                + "title varchar(50),\n"
                + "birthDate date,\n"
                + "country varchar(15)\n"
                + ")";
        execute(sql);
    }

    public static void main(String args[]) {
        createUsersTable();
    }

//    public static Boolean createUsersTable(){
//        
//    }
}
