/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.teacher;

import config.SessionData;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import models.Course;
import models.User;

/**
 *
 * @author leon
 */
@ManagedBean(name = "teacherBean", eager = true)
@SessionScoped
public class TeacherBean {
    @ManagedProperty(value = "#{sessionData}")
    private SessionData session;
    private List<Course> courses;
    
    public String getName(){return session.getUser().name;}
    public String getEm(){return session.getEm();}

    public List<Course> getCourses(){
        if(courses != null) return courses;
//        courses = Course.fetchByTeacher(session.getUser().userID);
        courses = Course.fetchByTeacher(1);
        return courses;
    }
    
    public SessionData getSession() {
        return session;
    }

    public void setSession(SessionData session) {
        this.session = session;
    }
    
    
}
