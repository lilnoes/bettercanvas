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
import java.time.Instant;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import javax.sql.DataSource;

/**
 *
 * @author Edson Casimiro
 */
@Named(value = "message")
@Dependent
public class message {
    
    public int messageId = 0;
    public int userTo = 0;
    public String conetnt = "";
    
    
    /**
     * Creates a new instance of Grades
     */
    @Resource(name = "jdbc/lms")
    DataSource dataSource;
    
    public void setContent(String conetnt) {
        this.conetnt = conetnt;
    }
    public String getContent() {
        return conetnt;
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
                    = connection.prepareStatement("INSERT INTO messages "
                            + "(USERFROM,USERTO,CONTENT, DATE)"
                            + "VALUES (?, ?, ?, ? )");

//            addEntry.setInt(1, messageId());
            addEntry.setInt(2, teacherBean.getSession().getUser().userID);
//            addEntry.setDouble(3, getUserTo());
            addEntry.setString(4, getContent());
            addEntry.setTimestamp(5, Timestamp.from(Instant.now()));
            addEntry.executeUpdate();

        } finally {
            connection.close();
        } // end finally
        return  "messages";
    } // end method save

}
