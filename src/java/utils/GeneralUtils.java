/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author leon
 */
@ManagedBean(name = "generalUtils")
public class GeneralUtils {
    
    public static void main(String[] args){
        Timestamp time = Timestamp.from(Instant.now());
        System.out.println(format(time));
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    public static String format(Timestamp timeStamp){
        String pattern = "MMMM dd, yyyy 'at' HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date(timeStamp.getTime()));
        return date;
    }
    private String token;
    public static void addMessage(FacesContext fct, String id, String message){
        fct.addMessage(id, new FacesMessage(message));
    }
    
    public String loggedIn(){
        System.out.println("here here here");
        return "account/login.xhtml";
    }
}
