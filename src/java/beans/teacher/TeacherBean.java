/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.teacher;

import com.sun.rowset.CachedRowSetImpl;
import com.sun.rowset.internal.Row;
import config.SessionData;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import models.Course;
import models.User;
import utils.DatabaseUtils;

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
    private List<Row> announcements;
    
    public String getName(){return session.getUser().name;}
    public String getEm(){return session.getEm();}

    public List<Course> getCourses(){
        if(courses != null) return courses;
//        courses = Course.fetchByTeacher(session.getUser().userID);
        courses = Course.fetchByTeacher(1);
        return courses;
    }
    
    public List<Row> getAnnouncements() {
        if (announcements != null) {
            return announcements;
        }
        announcements = new ArrayList<>();
        try {
            CachedRowSetImpl crs = new CachedRowSetImpl();
            PreparedStatement stmt = DatabaseUtils.getPreparedStatement("select title, summary, content, createdAt from announcements where courseID = ?");
            stmt.setInt(1, 1);
            crs.populate(stmt.executeQuery());
            stmt.close();
            stmt.getConnection().close();
            Collection<Row> rows = (Collection<Row>) crs.toCollection();
            announcements.addAll(rows);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return announcements;
    }
    
    public SessionData getSession() {
        return session;
    }

    public void setSession(SessionData session) {
        this.session = session;
    }
    
    
}
