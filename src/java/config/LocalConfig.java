/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import models.User;

/**
 *
 * @author leon
 */
@ManagedBean(name = "localConfig")
@SessionScoped
public class LocalConfig {
    private String minDate;
    public User user;
    private String confirmationCode;

    public String getMinDate() {
        if(minDate != null) return minDate;
        LocalDate min = LocalDate.now().minusYears(16);
        minDate = min.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return minDate;
    }
    
    public String getConfirmationCode(){
        Random rnd = new Random();
        confirmationCode = rnd.ints(6).toString();
        return confirmationCode;
    }
    
    public void validateConfirmationCode(FacesContext fc, UIComponent uic, String confCode){
        
    }
    
}
