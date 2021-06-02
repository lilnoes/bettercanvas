/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import beans.teacher.TeacherBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import javax.sql.DataSource;
import utils.DatabaseUtils;

/**
 *
 * @author Edson Casimiro
 */
@Named(value = "quizz")
@Dependent
public class Quizz {
    
    private String quizzName = "";
    //private String file = "";
    private String startDate = "";
    //private String endDate = "";
    //private int courseId = 0;
    private int duration = 0;
    
    
    
    @Resource(name = "jdbc/lms")
    DataSource dataSource;
    
    public void setQuizzName(String quizzName) {
        this.quizzName = quizzName;
    }
    public String getQuizzName() {
        return quizzName;
    }
    
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getStartDate() {
        return startDate;
    }
    
   /* public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public String getEndDate() {
        return endDate;
    }*/
    
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public int getDuration() {
        return duration;
    }
    
    
    
    
    
    public String save() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("Unable to obtain DataSource");
        }

        Connection connection = dataSource.getConnection();
        if (connection == null) {
            throw new SQLException("Unable to connect to DataSource");
        }
        try {
            TeacherBean teacherBean = (TeacherBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("teacherBean");
            
            java.sql.Timestamp time = java.sql.Timestamp.valueOf("2021-03-12 12:32:10");
            
            PreparedStatement addEntry
                    = connection.prepareStatement("INSERT INTO QUIZZ (TEACHERID,QUIZZNAME,FILE, STARTDATE, COURSEID, DURATION)"
                            + "VALUES ( ?, ?, ?, ?, ?, ? )");
            
            addEntry.setInt(1, 234);  //teacherBean.getSession().getUser().userID
            addEntry.setString(2, getQuizzName());  // here is the reciver user!
            addEntry.setString(3, "edsoncasimirotest");
            addEntry.setTimestamp(4, time);   //getStartDate()
            //addEntry.setTimestamp(4, Timestamp.from(Instant.now()));
            addEntry.setInt(4, 3216);   //teacherBean.currentCourse.id
            addEntry.setInt(5, getDuration());
            addEntry.executeUpdate();

        } finally {
            connection.close();
        } // end finally
        return  "";
    } // end method save


}
