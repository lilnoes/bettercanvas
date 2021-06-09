/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.teacher;

import com.sun.rowset.CachedRowSetImpl;
import com.sun.rowset.internal.Row;
import config.SessionData;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import models.Course;
import models.User;
import utils.DatabaseUtils;

/**
 *
 * @author leon
 */
@ManagedBean(name = "teacherBean", eager = true)
@SessionScoped
public class TeacherBean implements Serializable {

    @ManagedProperty(value = "#{sessionData}")
    private SessionData session;
    private List<Course> courses;
    private List<Row> announcements = null;
    private List<Row> allAnnouncements = null;
    private List<Row> students = null;
    public Course currentCourse;


    public String getName() {
        return session.getUser().name;
    }

    public String getEm() {
        return session.getEm();
    }

    public Course getCurrentCourse() {
        return currentCourse;
    }

    public void setCurrentCourse(int courseID) {
        currentCourse = Course.fetchById(courseID);
    }

    /*public void setCourses() {
        courses = Course.fetchByTeacher(session.getUser().userID);
    }*/

    public void setAnnouncements(List<Row> announcements) {
        this.announcements = announcements;
    }

    public void setAllAnnouncements() {
        this.allAnnouncements =  new ArrayList<>();
        try {
            CachedRowSetImpl crs = new CachedRowSetImpl();
            PreparedStatement stmt = DatabaseUtils.getPreparedStatement("select summary from announcements where createdBy = ?");
            stmt.setInt(1, session.getUser().userID);
            crs.populate(stmt.executeQuery());
            stmt.close();
            stmt.getConnection().close();
            Collection<Row> rows = (Collection<Row>) crs.toCollection();
            this.allAnnouncements.addAll(rows);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStudents() {
        students = new ArrayList<>();
        try {
            CachedRowSetImpl crs = new CachedRowSetImpl();
            PreparedStatement stmt = DatabaseUtils.getPreparedStatement("select u.NAME, u.surname, u.country, u.picture from studentcourses as sc\n"
                    + "join users as u on u.USERID=sc.STUDENTID\n"
                    + "where sc.COURSEID=?");
            stmt.setInt(1, session.currentCourse);
            crs.populate(stmt.executeQuery());
            stmt.close();
            stmt.getConnection().close();
            Collection<Row> rows = (Collection<Row>) crs.toCollection();
            students.addAll(rows);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
    
    public List<Course> getCourses() {
        if (courses != null) {
            return courses;
        }
        //setCourses();
        return courses;
    }

    public List<Row> getAnnouncements() {
        if(announcements != null) return announcements;
        setAnnouncements();
        return announcements;
    }

    public void setAnnouncements() {
        announcements = new ArrayList<>();
        try {
            CachedRowSetImpl crs = new CachedRowSetImpl();
            PreparedStatement stmt = DatabaseUtils.getPreparedStatement("select title, summary, content, createdAt from announcements where courseID = ?");
            stmt.setInt(1, session.currentCourse);
            crs.populate(stmt.executeQuery());
            stmt.close();
            stmt.getConnection().close();
            Collection<Row> rows = (Collection<Row>) crs.toCollection();
            announcements.addAll(rows);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<Row> getStudents() {
        if(students != null) return students;
        setStudents();
        return students;
    }

    public List<Row> getAllAnnouncements() {
        if(allAnnouncements != null) return allAnnouncements;
        setAllAnnouncements();
        return allAnnouncements;
    }

    public SessionData getSession() {
        return session;
    }

    public void setSession(SessionData session) {
        this.session = session;
    }

    public String init() {
        String course = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("course");
        if(course != null){
            session.currentCourse = Integer.valueOf(course);
            setCurrentCourse(session.currentCourse);
        }
        System.out.println("starting view");
        //setCourses();
        setStudents();
        setAllAnnouncements();
        return null;
    }
    public String initCourses() {
        init();
        this.setCurrentCourse(session.currentCourse);
        return null;
    }

}
