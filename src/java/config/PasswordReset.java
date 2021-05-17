/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.util.Random;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import utils.GeneralUtils;

/**
 *
 * @author leon
 */
@ManagedBean(name = "passwordReset")
@RequestScoped
public class PasswordReset {
    private String email = "";
    private String newpassword = "";
    private String confpassword = "";
    private String confirmationCode;
    private String code = "";

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
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

    
    public void seed(AjaxBehaviorEvent evt){
        FacesContext fct = FacesContext.getCurrentInstance();
        UIInput component =  (UIInput)fct.getViewRoot().findComponent("resetform:email");
        if(component == null || !component.isValid()){
            GeneralUtils.addMessage(fct, "resetform:codebutton", "email problems");
            return;
        }
        Random rnd = new Random();
        confirmationCode = rnd.ints(6).toString();
    }
    
    public String getConfirmationCode(){
        return confirmationCode;
    }
    
    public void validateConfirmationCode(FacesContext fc, UIComponent uic, String confCode){
        
    }
    
}
