/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.student;

import com.sun.rowset.CachedRowSetImpl;
import com.sun.rowset.internal.Row;
import config.SessionData;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
@ManagedBean(name = "studentBean", eager = true)
@SessionScoped
public class StudentBean implements Serializable {

    @ManagedProperty(value = "#{sessionData}")
    private SessionData session;
    private List<Course> courses;
    private List<Row> grades;
    private List<Row> quizzes;
    private List<Row> announcements = null;
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
    
    public List<Row> getGrades() {
        grades = new ArrayList<>();
        try {
            CachedRowSetImpl crs = new CachedRowSetImpl();
            PreparedStatement stmt = DatabaseUtils.getPreparedStatement("select q.QUIZZNAME, q.STARTDATE, g.GRADES, g.RANGE from grades as g\n"
                    + "inner join quizz as q\n"
                    + "on q.id=g.QUIZID\n"
                    + "where g.USERID= ?");
            stmt.setInt(1, 2);
//            stmt.setInt(2, 2);
            crs.populate(stmt.executeQuery());
            stmt.close();
            stmt.getConnection().close();
            Collection<Row> rows = (Collection<Row>) crs.toCollection();
            grades.addAll(rows);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return grades;
    }
    
    public List<Row> getQuizzes() {
        quizzes = new ArrayList<>();
        try {
            CachedRowSetImpl crs = new CachedRowSetImpl();
            PreparedStatement stmt = DatabaseUtils.getPreparedStatement("select * from quizz\n"
                    + "where courseID = ?");
            stmt.setInt(1, 1);
//            stmt.setInt(2, 2);
            crs.populate(stmt.executeQuery());
            stmt.close();
            stmt.getConnection().close();
            Collection<Row> rows = (Collection<Row>) crs.toCollection();
            quizzes.addAll(rows);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quizzes;
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

}
