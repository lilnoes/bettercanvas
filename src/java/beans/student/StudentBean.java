/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.student;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import models.User;

/**
 *
 * @author leon
 */
@ManagedBean(name = "studentBean")
@SessionScoped
public class StudentBean {
    private User user;
}
