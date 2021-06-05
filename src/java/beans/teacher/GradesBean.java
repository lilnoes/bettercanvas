/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.teacher;

import com.sun.rowset.CachedRowSetImpl;
import com.sun.rowset.internal.Row;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
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
import utils.DatabaseUtils;

/**
 *
 * @author leon
 */
@ManagedBean(name = "gradesBean")
@ViewScoped
public class GradesBean implements Serializable{
    public int currentQuiz = 1;
    public List<Row> grades;
    public List<String> quizzes;
    public List<String> quizNames;
    public String action = "edit";
    public String point = "100";
    public String currentCourse = "";

    public GradesBean() {
        quizNames = new ArrayList<>();
        quizNames.add("quiz 1");
        quizNames.add("quiz 2");
    }
    
    public String getLink() throws UnsupportedEncodingException{
        String base = FacesContext.getCurrentInstance().getExternalContext().getRequestServletPath();
        Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String action = (String)map.get("action");
        currentCourse = map.get("course");
        if(currentCourse == null || currentCourse.isEmpty()) currentCourse = "1";
        return String.format("grades.xhtml?faces-redirect=true&quiz=%s&action=%s&course=%s", currentQuiz, action, currentCourse);
    }
    

    @PostConstruct
    public void init(){
        Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if(map.isEmpty()) return;
        String action = (String)map.get("quiz");
        currentCourse = map.get("course");
        quizzes = new ArrayList<>();
        quizzes.add("quiz 1");
        quizzes.add("quiz 2");
        System.out.println("hitting here from post construct " + action);
    }
    
    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }
    
    

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
    
    

    public int getCurrentQuiz() {
        return currentQuiz;
    }

    public void setCurrentQuiz(int currentQuiz) {
        this.currentQuiz = currentQuiz;
    }

    public List<String> getQuizzes() {
        return quizzes;
    }

    public List<String> getQuizNames() {
        return quizNames;
    }
    
    public List<Row> getGrades() {
        grades = new ArrayList<>();
        try {
            CachedRowSetImpl crs = new CachedRowSetImpl();
            PreparedStatement stmt = DatabaseUtils.getPreparedStatement("select u.NAME, q.QUIZZNAME, q.STARTDATE, g.GRADES, g.RANGE from studentcourses as sc\n"
                    + "left join grades as g\n"
                    + "on sc.STUDENTID=g.USERID\n"
                    + "join quizz as q\n"
                    + "on q.ID=g.QUIZID\n"
                    + "join users as u on u.USERID=sc.STUDENTID\n"
                    + "where g.QUIZID=? and sc.COURSEID=?");
            stmt.setInt(1, currentQuiz);
            stmt.setInt(2, 1);
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
    
    public void setQuiz(AjaxBehaviorEvent evt){
        quizzes = new ArrayList<>();
        quizzes.add("quiz 1");
        quizzes.add("quiz 2");
        String id = evt.getComponent().getId();
        if(id.equals("valuebutton")) action = "view";
        else action = "edit";
        System.out.println("came " + action + " " + point);
    }
    
    public void updatePoints(AjaxBehaviorEvent evt){
        getQuizzes().add("quiz ajax "+ point);
        System.out.println("ajax event" + point);
    }
    
    
}
