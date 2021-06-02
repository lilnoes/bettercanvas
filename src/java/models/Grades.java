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
import javax.annotation.Resource;
import java.sql.Timestamp;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author Edson Casimiro
 */
@Named(value = "grades")
@Dependent
public class Grades {

    public int userId = 0;
    public int courseId = 0;
    public double grades = 0;
    public int range = 0;

    /**
     * Creates a new instance of Grades
     */
    @Resource(name = "jdbc/lms")
    DataSource dataSource;
    
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getUserId() {
        return userId;
    }
    
    
    public void setGrades(int grades) {
        this.grades = grades;
    }

    public double getGrades() {
        return grades;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getRange() {
        return range;
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
            
            PreparedStatement addEntry
                    = connection.prepareStatement("INSERT INTO GRADES "
                            + "(USERID,COURSEID,GRADES,RANGE)"
                            + "VALUES ( ?, ?, ?, ?, ?, ? )");
            
            addEntry.setInt(1, getUserId());  //must be the student id, we dont have it. let get it through input.(okul numarasi)
            addEntry.setInt(2, teacherBean.currentCourse.id);
            addEntry.setDouble(3, getGrades());
            addEntry.setInt(4, getRange());

            addEntry.executeUpdate(); // insert the entry

        } finally {
            connection.close();
        } // end finally
        return  "grades";
    } // end method save

}
