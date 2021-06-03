/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.teacher;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author leon
 */
@ManagedBean(name = "gradesBean")
@ViewScoped
public class GradesBean implements Serializable{
    public int currentQuiz = 0;
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
