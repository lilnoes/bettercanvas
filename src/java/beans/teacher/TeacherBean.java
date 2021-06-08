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

    @PostConstruct
    public void init1() {
//        setCourses();
//        setAnnouncements();
        init();
        setFallBack();
        System.out.println("finished setting course");
    }

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

    public List<Course> getCourses() {
        if (courses != null) {
            return courses;
        }
//        courses = Course.fetchByTeacher(session.getUser().userID);
        courses = Course.fetchByTeacher(1);
        return courses;
    }

    public List<Row> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements() {
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
    }
    
    public List<Row> getStudents() {
        students = new ArrayList<>();
        try {
            CachedRowSetImpl crs = new CachedRowSetImpl();
            PreparedStatement stmt = DatabaseUtils.getPreparedStatement("select u.NAME, u.surname, u.country from studentcourses as sc\n"
                    + "join users as u on u.USERID=sc.STUDENTID\n"
                    + "where sc.COURSEID=?");
            stmt.setInt(1, 1);
            crs.populate(stmt.executeQuery());
            stmt.close();
            stmt.getConnection().close();
            Collection<Row> rows = (Collection<Row>) crs.toCollection();
            students.addAll(rows);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    public List<Row> getAllAnnouncements() {
        allAnnouncements = new ArrayList<>();
        try {
            CachedRowSetImpl crs = new CachedRowSetImpl();
            PreparedStatement stmt = DatabaseUtils.getPreparedStatement("select summary from announcements where createdBy = ?");
            stmt.setInt(1, 2);
            crs.populate(stmt.executeQuery());
            stmt.close();
            stmt.getConnection().close();
            Collection<Row> rows = (Collection<Row>) crs.toCollection();
            allAnnouncements.addAll(rows);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allAnnouncements;
    }

    public SessionData getSession() {
        return session;
    }

    public void setSession(SessionData session) {
        this.session = session;
    }

    public String init() {
        System.out.println("starting view");
        Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (map.isEmpty()) {
            return "index";
        }
        int courseID = Integer.valueOf(map.get("course"));
        setCurrentCourse(courseID);
        System.out.println("found course " + courseID);
        return null;
    }

    private void setFallBack() {
        if(currentCourse != null) return;
        try {
            String sql = "select id from courses\n"
                    + "where createdBY = ?"
                    + "fetch first 1 rows only";
            PreparedStatement stmt = DatabaseUtils.getPreparedStatement(sql);
            stmt.setInt(1, 1);
            ResultSet res = stmt.executeQuery();
            if (!res.next()) {
                return;
            }
            setCurrentCourse(res.getInt(1));
        } catch (Exception e) {
            currentCourse = null;
        }

    }

}
