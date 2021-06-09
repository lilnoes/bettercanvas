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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import org.apache.derby.client.am.SqlException;
import utils.DatabaseUtils;

/**
 *
 * @author leon
 */
@ManagedBean(name = "gradesBean")
@ViewScoped
public class GradesBean implements Serializable {

    public int currentQuiz = 0;
    public List<Row> grades;
    public List<Row> quizzes;
    public List<String> quizNames;
    public String action = "view";
    public int studentId = 1;
    public double points = 100;
    public String model = "points";
    public String currentCourse = "";

    public GradesBean() {
        quizNames = new ArrayList<>();
        quizNames.add("quiz 1");
        quizNames.add("quiz 2");
    }

//    public String getLink() throws UnsupportedEncodingException {
//        String base = FacesContext.getCurrentInstance().getExternalContext().getRequestServletPath();
//        Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
//        String action = (String) map.get("action");
//        currentCourse = map.get("course");
//        if (currentCourse == null || currentCourse.isEmpty()) {
//            currentCourse = "1";
//        }
//        return String.format("grades.xhtml?faces-redirect=true&quiz=%s&action=%s&course=%s", currentQuiz, action, currentCourse);
//    }

    @PostConstruct
    public void init() {
        Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        setQuizzes();
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCurrentQuiz() {
        return currentQuiz;
    }

    public void setCurrentQuiz(int currentQuiz) {
        this.currentQuiz = currentQuiz;
    }

    public List<Row> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes() {
        SessionData sessionData = (SessionData) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionData");
        quizzes = new ArrayList<>();
        try {
            CachedRowSetImpl crs = new CachedRowSetImpl();
            PreparedStatement stmt = DatabaseUtils.getPreparedStatement("select id, quizzname from quizz where courseID = ?");
            stmt.setInt(1, sessionData.currentCourse); //courseID
            crs.populate(stmt.executeQuery());
            stmt.close();
            stmt.getConnection().close();
            Collection<Row> rows = (Collection<Row>) crs.toCollection();
            quizzes.addAll(rows);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getQuizNames() {
        return quizNames;
    }

    public List<Row> getGrades() {
        SessionData sessionData = (SessionData) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionData");
        grades = new ArrayList<>();
        try {
            CachedRowSetImpl crs = new CachedRowSetImpl();
            PreparedStatement stmt = DatabaseUtils.getPreparedStatement("select u.userid, u.SURNAME || u.name, q.QUIZZNAME, q.STARTDATE, g.GRADES, g.RANGE from grades as g\n"
                    + "join quizz as q on g.QUIZID=q.ID\n"
                    + "join users as u on u.USERID=g.userID\n"
                    + "where g.QUIZID=? and g.COURSEID=?");
            stmt.setInt(1, currentQuiz);
            stmt.setInt(2, sessionData.currentCourse); //courseID
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

    public void updatePoints(AjaxBehaviorEvent evt) throws SQLException {
        String sql = "update grades set grades = ? where userid = ? and quizid = ?";
        PreparedStatement stmt = DatabaseUtils.getPreparedStatement(sql);
        stmt.setDouble(1, points);
        stmt.setInt(2, studentId);
        stmt.setInt(3, currentQuiz);
        stmt.executeUpdate();
        stmt.close();
        stmt.getConnection().close();
//        getQuizzes().add("quiz ajax "+ point);
        System.out.println("ajax event point");
        System.out.println("ajax event point");
    }

}
