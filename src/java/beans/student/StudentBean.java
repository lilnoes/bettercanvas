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
@ManagedBean(name = "studentBean", eager = true)
@SessionScoped
public class StudentBean implements Serializable {

    @ManagedProperty(value = "#{sessionData}")
    private SessionData session;

    private List<Row> courses;
    private List<Row> grades;
    private List<Row> quizzes;
    private List<Row> announcements = null;
    public Course currentCourse;

    @PostConstruct
    public void init1() {
        setCourses();
        setAnnouncements();
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

    private void setFallBack() {
        init();
        if(currentCourse != null) return;
        try {
            String sql = "select id from studentcourses\n"
                    + "where studentid = ? and status = 'accepted'\n"
                    + "fetch first 1 rows only";
            PreparedStatement stmt = DatabaseUtils.getPreparedStatement(sql);
            stmt.setInt(1, 2);
            ResultSet res = stmt.executeQuery();
            if(!res.next()) return;
            setCurrentCourse(res.getInt(1));
        } catch (Exception e) {
            currentCourse = null;
        }

    }

    public void setCourses() {
        courses = new ArrayList<>();
        try {
            CachedRowSetImpl crs = new CachedRowSetImpl();
            PreparedStatement stmt = DatabaseUtils.getPreparedStatement("select c.NAME, c.SHORTNAME, c.ID from studentcourses as sc\n"
                    + "join courses as c on c.ID=sc.COURSEID\n"
                    + "where sc.STATUS='accepted' and sc.STUDENTID=?");
            stmt.setInt(1, 2);
            crs.populate(stmt.executeQuery());
            stmt.close();
            stmt.getConnection().close();
            Collection<Row> rows = (Collection<Row>) crs.toCollection();
            courses.addAll(rows);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Row> getCourses() {
        return courses;
    }

    public List<Row> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements() {
        announcements = new ArrayList<>();
        try {
            CachedRowSetImpl crs = new CachedRowSetImpl();
            PreparedStatement stmt = DatabaseUtils.getPreparedStatement("select a.TITLE, a.SUMMARY, a.CREATEDAT, c.NAME from studentcourses as sc\n"
                    + "join announcements as a on a.COURSEID=sc.COURSEID\n"
                    + "join courses as c on c.ID=sc.COURSEID\n"
                    + "where sc.STUDENTID=? and sc.STATUS='accepted'");
            stmt.setInt(1, 2);
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
                    + "where g.USERID= ? and g.courseID = ?");
            stmt.setInt(1, 2);//studentID
            stmt.setInt(2, 1);//courseID
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
            return null;
        }
        int courseID = Integer.valueOf(map.get("course"));
        setCurrentCourse(courseID);
        System.out.println("found course " + courseID);
        return null;
    }

}
