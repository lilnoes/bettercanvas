/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account;

import com.sun.rowset.CachedRowSetImpl;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.derby.client.am.DateTime;
import utils.DatabaseUtils;
import utils.UserUtils;

/**
 *
 * @author leon
 */
@SessionScoped
@ManagedBean(name = "registrationData")
public class RegistrationData {

    private String name = "";
    private String surName = "";
    private String email = "";
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

    public String registerStudent() {
        String outcome = "#";
        String sql = "insert into users\n"
                + "(name, surname, email, password, type, birthDate, country)\n"
                + "values\n"
                + "(?, ?, ?, ?, 'student', ?, ?)";
        try {
            PreparedStatement statement = DatabaseUtils.getPreparedStatement(sql);
            statement.setString(1, name);
            statement.setString(2, surName);
            statement.setString(3, email);
            statement.setString(4, UserUtils.hashPassword(newPassword));
            statement.setDate(5, new java.sql.Date(birthDate.getTime()));
            statement.setString(6, country.toLowerCase());
            int res = statement.executeUpdate();
            if (res == 0) {
                FacesContext.getCurrentInstance().addMessage("registerform:confpassword", new FacesMessage("Try registering again, error occured"));
            }
            statement.close();
            return "login";
        } catch (Exception e) {
            return "#";
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
