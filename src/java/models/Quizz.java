/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import beans.teacher.TeacherBean;
import com.sun.rowset.CachedRowSetImpl;
import com.sun.rowset.internal.Row;
import java.io.File;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.servlet.http.Part;
import javax.sql.DataSource;
import utils.DatabaseUtils;

/**
 *
 * @author Edson Casimiro
 */
@ManagedBean(name = "quizz")
@ViewScoped
public class Quizz {

    private String quizzName = "";
    private Date startDate;
    private Part file;
    private int duration = 0;
    public List<Row> quizzes;

    public List<Row> getQuizzes() {
        quizzes = new ArrayList<>();
        try {
            CachedRowSetImpl crs = new CachedRowSetImpl();
            PreparedStatement stmt = DatabaseUtils.getPreparedStatement("select * from quizz where courseID = ?");
            stmt.setInt(1, 1);
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

    
    
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    @Resource(name = "jdbc/lms")
    DataSource dataSource;

    public void setQuizzName(String quizzName) {
        this.quizzName = quizzName;
    }

    public String getQuizzName() {
        return quizzName;
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

    private String upload() {
        String name = file.getSubmittedFileName();
        try {
            String base = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("location");
            Files.copy(file.getInputStream(), Paths.get(base + name));
            return name;
        } catch (FileAlreadyExistsException e) {
            return name;
        } catch (Exception e) {
            return null;
        }

    }

    public String save() throws SQLException {
        System.out.println("saving file");
        String filename = "";
        if (file != null) {
            filename = upload();
        }
        try {
            TeacherBean teacherBean = (TeacherBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("teacherBean");

            PreparedStatement addEntry
                    = DatabaseUtils.getPreparedStatement("INSERT INTO QUIZZ (TEACHERID,QUIZZNAME,FILE, STARTDATE, COURSEID, DURATION)"
                            + "VALUES ( ?, ?, ?, ?, ?, ? )");

            addEntry.setInt(1, 1);  //teacherBean.getSession().getUser().userID
            addEntry.setString(2, quizzName);  // here is the reciver user!
            addEntry.setString(3, filename);
            addEntry.setTimestamp(4, new Timestamp(startDate.getTime()));   //getStartDate()
            addEntry.setInt(5, 1);   //teacherBean.currentCourse.id
            addEntry.setInt(6, duration);
            if (addEntry.executeUpdate() == 0) {
                throw new AbortProcessingException();
            }

            addEntry.close();
            addEntry.getConnection().close();
            System.out.println("finished saving file");

        } catch (Exception e) {
            e.printStackTrace();
        } // end finally
        return "";
    } // end method save

}
