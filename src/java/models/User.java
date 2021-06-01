/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import utils.DatabaseUtils;
import utils.UserUtils;

/**
 *
 * @author leon
 */
public class User {
    public int userID;
    public String name = "EM";
    public String surName = "";
    public String email = "";
    private String password = "";
    public String title = "";
    public String type = "";
    public String faculty = "";
    public String sinif = "";
    public String picture = "";
    public Timestamp birthDate;
    public String country = "";

    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getSurName() {
        return surName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getSinif() {
        return sinif;
    }

    public String getPicture() {
        return picture;
    }

    public Timestamp getBirthDate() {
        return birthDate;
    }

    public String getCountry() {
        return country;
    }
    
    public String getFullname(){return this.name + " " + this.surName;}
    
    
    
    public Boolean verifyPassword(String password){
        if(UserUtils.hashPassword(password).equals(this.password)) return true;
        return false;
    }
    
    public static User getUser(ResultSet res) throws SQLException{
        User user = new User();
        user.userID = res.getInt(1);
        user.name = res.getString(2);
        user.surName = res.getString(3);
        user.email = res.getString(4);
        user.password = res.getString(5);
        user.type = res.getString(6);
        user.title = res.getString(7);
        user.sinif = res.getString(8);
        user.faculty = res.getString(9);
        user.picture = res.getString(10);
        user.birthDate = res.getTimestamp(11);
        user.country = res.getString(12);
        return user;
    }
    
    public static User fetchByEmail(String email){
        User user = null;
        String sql = "select * from users\n"
                + "where email = ?"
                + "fetch first 1 rows only";
        try {
            PreparedStatement statement = DatabaseUtils.getPreparedStatement(sql);
            statement.setString(1, email);
            ResultSet res = statement.executeQuery();
            if (!res.next()) return user;
            user = getUser(res);
            statement.close();
            statement.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
