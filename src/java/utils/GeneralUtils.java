/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author leon
 */
@ManagedBean(name = "generalUtils")
public class GeneralUtils {

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
