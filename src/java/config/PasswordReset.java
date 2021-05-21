/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
import java.util.stream.IntStream;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import utils.DatabaseUtils;
import utils.GeneralUtils;
import utils.UserUtils;

/**
 *
 * @author leon
 */
@ManagedBean(name = "passwordReset")
@ViewScoped
public class PasswordReset {
    private String email = "";
    private String newpassword = "";
    private String confpassword = "";
    private String confirmationCode;
    private String code = "";
    private Boolean errorsVar = true;
    
    @PostConstruct
    public void init(){
        System.out.println("view started");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        System.out.println("setting email "+email);
        this.email = email;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    public String getConfpassword() {
        return confpassword;
    }

    public void setConfpassword(String confpassword) {
        this.confpassword = confpassword;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    
    public void seed(ActionEvent evt){
        FacesContext fct = FacesContext.getCurrentInstance();
        UIInput component =  (UIInput)fct.getViewRoot().findComponent("resetform:email");
        if(component == null || email.isEmpty() || !component.isValid()){
            GeneralUtils.addMessage(fct, "resetform:codebutton", "email should be valid");
            return;
        }
        Random rnd = new Random();
         confirmationCode = String.valueOf(rnd.nextInt(900000) + 100000);
    }
    
    public String getConfirmationCode(){
        return confirmationCode;
    }
    
    public void validateConfirmationCode(FacesContext fc, UIComponent uic, String confCode){
        
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }
    
    public String changePassword() {
        String outcome = null;
        String sql = "select * from users\n"
                + "where email=? and password=?\n"
                + "fetch first 1 rows only";
        System.out.println("changing password " + email + newpassword);
//        try {
//            PreparedStatement statement = DatabaseUtils.getPreparedStatement(sql);
//            statement.setString(1, email);
//            statement.setString(2, UserUtils.hashPassword(newPassword));
//            ResultSet res = statement.executeQuery();
//            if (!res.next()) {
//                outcome = null;
//                FacesContext.getCurrentInstance().addMessage("loginform:password", new FacesMessage("Email or Password is invalid"));
//            } else {
//                outcome = "dashboard";
//            }
//            statement.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return outcome;

    }
    
}
