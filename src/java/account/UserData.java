/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
<<<<<<< HEAD
=======
import java.sql.Timestamp;
>>>>>>> 92b72685839bc9521d953c93c9a5fa0bfda778fc
import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.sql.rowset.CachedRowSet;
import org.apache.derby.client.am.DateTime;
import utils.DatabaseUtils;
import utils.UserUtils;

/**
 *
 * @author leon
 */
@SessionScoped
@ManagedBean(name = "userData")
public class UserData {

    private String name = "";
    private String surName = "";
    private String email = "";
<<<<<<< HEAD
=======
    private String type = "";
    private String title = "";
    private int year = 1;
>>>>>>> 92b72685839bc9521d953c93c9a5fa0bfda778fc
    private Date birthDate;
    private String country = "Turkey";
    private String newPassword = "";
    private String confPassword = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

<<<<<<< HEAD
=======
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    
    

>>>>>>> 92b72685839bc9521d953c93c9a5fa0bfda778fc
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfPassword() {
        return confPassword;
    }

    public void setConfPassword(String confPassword) {
        this.confPassword = confPassword;
    }

<<<<<<< HEAD
    public String registerStudent() {
        String outcome = "#";
        String sql = "insert into users\n"
                + "(name, surname, email, password, type, birthDate, country)\n"
                + "values\n"
                + "(?, ?, ?, ?, 'student', ?, ?)";
=======
    public String register() {
        String outcome = "#";
        String sql = "insert into users\n"
                + "(name, surname, email, password, type, title, sinif, birthDate, country)\n"
                + "values\n"
                + "(?, ?, ?, ?, ?, ?, ?, ?, ?)";
>>>>>>> 92b72685839bc9521d953c93c9a5fa0bfda778fc
        try {
            PreparedStatement statement = DatabaseUtils.getPreparedStatement(sql);
            statement.setString(1, name);
            statement.setString(2, surName);
            statement.setString(3, email);
            statement.setString(4, UserUtils.hashPassword(newPassword));
<<<<<<< HEAD
            statement.setDate(5, new java.sql.Date(birthDate.getTime()));
            statement.setString(6, country.toLowerCase());
=======
            statement.setString(5, type);
            statement.setString(6, title);
            statement.setInt(7, year);
            statement.setTimestamp(8, new Timestamp(birthDate.getTime()));
            statement.setString(9, country.toLowerCase());
>>>>>>> 92b72685839bc9521d953c93c9a5fa0bfda778fc
            int res = statement.executeUpdate();
            if (res == 0) {
                FacesContext.getCurrentInstance().addMessage("registerform:confpassword", new FacesMessage("Try registering again, error occured"));
            }
            statement.close();
            return "login";
        } catch (Exception e) {
<<<<<<< HEAD
            return "#";
=======
            return null;
>>>>>>> 92b72685839bc9521d953c93c9a5fa0bfda778fc
        }

    }

    public String login() {
        String outcome = null;
        String sql = "select * from users\n"
                + "where email=? and password=?\n"
                + "fetch first 1 rows only";
        try {
            PreparedStatement statement = DatabaseUtils.getPreparedStatement(sql);
            statement.setString(1, email);
            statement.setString(2, UserUtils.hashPassword(newPassword));
            ResultSet res = statement.executeQuery();
            if (!res.next()) {
                outcome = null;
                FacesContext.getCurrentInstance().addMessage("loginform:password", new FacesMessage("Email or Password is invalid"));
            } else {
                outcome = "dashboard";
            }
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outcome;

    }

}
