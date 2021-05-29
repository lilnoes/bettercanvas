/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import models.User;

/**
 *
 * @author leon
 */
@SessionScoped
@ManagedBean(name = "sessionData")
public class SessionData {
    private User user = null;

    public User getUser() {
        return user;
    }
    
    public void setUser(User user){
        this.user = user;
    }
}
