/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import models.User;

/**
 *
 * @author leon
 */
@SessionScoped
@ManagedBean(name = "sessionData", eager = true)
public class SessionData implements Serializable{
    private User user = null;
    private String em = "em";
    
    public String getEm(){return em;}

    public User getUser() {
        if(user == null) user = User.fetchByEmail("ishimwe@ogr.iuc.edu.tr");
        return user;
    }
    
    public void setUser(User user){
        this.user = user;
    }
}
