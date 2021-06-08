/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account;

import beans.teacher.TeacherBean;
import config.SessionData;
import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import javax.sql.rowset.CachedRowSet;
import models.User;
import utils.DatabaseUtils;
import utils.UserUtils;

/**
 *
 * @author leon
 */
@SessionScoped
@ManagedBean(name = "userData")
public class UserData implements Serializable {

    private String name = "";
    private String surName = "";
    private String email = "";
    private String newEmail = "";
    private String newEmailConf = "";
    private String type = "";
    private String title = "";
    private int year = 1;
    private Date birthDate;
    private String country = "Turkey";
    private String faculty = "";
    private String newPassword = "";
    private String confPassword = "";
    private static String UPLOADS = "/mnt/leon/leon/projects/BetterCanvas/web/resources/images";

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

    
    
    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }
    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmailConf(String newEmailConf) {
        this.newEmailConf = newEmailConf;
    }
    public String getNewEmailConf() {
        return newEmailConf;
    }
    
    

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
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
    
     public String errorSMS(){
        return"The entered information does not match!";
    }

//    public void upload(AjaxBehaviorEvent evt){
//        System.out.println("event ajax upload");
//        String filename = Paths.get(picture.getSubmittedFileName()).toString();
//        System.out.println(filename);
//        try{
//        File file = new File(UPLOADS, filename);
//        Files.copy(picture.getInputStream(), file.toPath());
//        }catch(Exception e){e.printStackTrace();}
//    }
//    
    public String register() {
        String outcome = "#";
        String sql = "insert into users\n"
                + "(name, surname, email, password, type, title, sinif, faculty, birthDate, country)\n"
                + "values\n"
                + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = DatabaseUtils.getPreparedStatement(sql);
            statement.setString(1, name);
            statement.setString(2, surName);
            statement.setString(3, email);
            statement.setString(4, UserUtils.hashPassword(newPassword));
            statement.setString(5, type);
            statement.setString(6, title);
            statement.setInt(7, year);
            statement.setString(8, faculty);
            statement.setTimestamp(9, new Timestamp(birthDate.getTime()));
            statement.setString(10, country.toLowerCase());
            int res = statement.executeUpdate();
            if (res == 0) {
                FacesContext.getCurrentInstance().addMessage("registerform:confpassword", new FacesMessage("Try registering again, error occured"));
            }
            statement.close();
            statement.getConnection().close();
            return "login";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public String login() {
        User user = User.fetchByEmail(email);
        if (user == null || !user.verifyPassword(newPassword)) {
            FacesContext.getCurrentInstance().addMessage("loginform:password", new FacesMessage("Email or Password is invalid"));
            return null;
        }

        SessionData session = new SessionData();
        session.setUser(user);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("session", session);
        if (user.type.equals("teacher")) {
            return "/teacher/index";
        } else {
            return "/student/index";
        }
    }

    public String logout() {
        SessionData session = (SessionData) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session");
        if (session == null) {
            return null;
        }
        session.setUser(null);
        return null;
    }
    
    
    public String updateEmail() throws SQLException{
        TeacherBean teacherBean = (TeacherBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("teacherBean");

        try {
            if ((teacherBean.getSession().getUser().email).equals(getEmail())) {
                System.out.println("good");
                if ((getNewEmail()).equals(getNewEmailConf())) {
                    String sql = "UPADATE  USERS SET EMAIL=? WHERE EMAIL=?";

                    PreparedStatement addEntry = DatabaseUtils.getPreparedStatement(sql);
                    addEntry.setString(1, getNewEmail());  //teacherBean.getSession().getUser().userID
                    addEntry.setString(2, teacherBean.getSession().getUser().email);  // here is the reciver user!
                } else {
                    System.out.println("Not the same, unfortunatelly");
                    return errorSMS();
                }
            } else {
                System.out.println(" the same but not the user unfortunatelly");
                return "account";
            }

  
                
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }
    
}
