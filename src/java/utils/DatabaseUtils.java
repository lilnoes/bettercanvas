/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.sun.rowset.CachedRowSetImpl;
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

    public static Boolean acceptChanges(CachedRowSetImpl res) {
        try {
            res.acceptChanges(getConnection());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Boolean execute(String sql) {
        try {
            Connection conn = getConnection();
            Statement statement = conn.createStatement();
            statement.execute(sql);
            conn.close();
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
//create table users
//(
//userID INT NOT NULL GENERATED ALWAYS AS IDENTITY,
//name varchar (20),
//surname varchar (20),
//email varchar (50),
//password varchar (32),
//type varchar (10) not null,
//title varchar (50),
//sinif smallint,
//faculty varchar(50),
//picture varchar(50),
//birthDate timestamp,
//country varchar(15)
//)

        String sql = "create table users\n"
                + "(\n"
                + "userID INT NOT NULL GENERATED ALWAYS AS IDENTITY,\n"
                + "name varchar (20),\n"
                + "surname varchar (20),\n"
                + "email varchar (50),\n"
                + "password varchar (32),\n"
                + "type varchar (10) not null,\n"
                + "title varchar (50),\n"
                + "sinif smallint,\n"
                + "faculty varchar(50),\n"
                + "picture varchar(50),\n"
                + "birthDate timestamp,\n"
                + "country varchar(15)\n"
                + ")";
        execute(sql);
    }

    public static void main(String args[]) {
        createUsersTable();
    }

    public static void createCoursesTable() {
//        create table courses
//(
//ID INT NOT NULL GENERATED ALWAYS AS IDENTITY,
//name varchar(50),
//shortName varchar(50),
//sinif smallint,
//faculty varchar(50),
//description varchar(100),
//requirements varchar(100),
//creationDate timestamp,
//locked boolean,
//createdBy int,
//students int
//)
        String sql = "create table courses\n"
                + "(\n"
                + "ID INT NOT NULL GENERATED ALWAYS AS IDENTITY,\n"
                + "name varchar(50),\n"
                + "shortName varchar(50),\n"
                + "sinif smallint,\n"
                + "faculty varchar(50),\n"
                + "description varchar(100),\n"
                + "requirements varchar(100),\n"
                + "creationDate timestamp,\n"
                + "locked boolean,\n"
                + "createdBy int,\n"
                + "students int\n"
                + ")";
        execute(sql);
    }

    public static void createStudentCoursesTable() {
//create table studentCourses
//(
//id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
//courseID int,
//studentID int,
//pass Boolean with default false,
//status varchar(10) with default 'waiting'
//)

        String sql = "create table studentCourses\n"
                + "(\n"
                + "id INT NOT NULL GENERATED ALWAYS AS IDENTITY,\n"
                + "courseID int,\n"
                + "studentID int,\n"
                + "pass Boolean with default false,\n"
                + "status varchar(10) with default 'waiting'\n"
                + ")";
        execute(sql);
    }
}
