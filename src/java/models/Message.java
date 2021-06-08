/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import beans.teacher.TeacherBean;
import com.sun.rowset.CachedRowSetImpl;
import com.sun.rowset.internal.Row;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.sql.DataSource;
import utils.DatabaseUtils;

/**
 *
 * @author Edson Casimiro
 */
@ManagedBean(name = "message")
@ViewScoped
public class Message {

    public int toID = 0;
    public String type = "1";
    public String message = "";
    public List<Row> courses;
    public List<Row> courses1; //for students

    public List<Row> senders;
    public List<Row> messages;
    public List<Row> messages1;

    public int getToID() {
        return toID;
    }

    public void setToID(int toID) {
        this.toID = toID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Row> getCourses() {
        courses = new ArrayList<>();
        try {
            CachedRowSetImpl crs = new CachedRowSetImpl();
            PreparedStatement stmt = DatabaseUtils.getPreparedStatement("select u.USERID, c.id, u.NAME, u.surname, u.title, c.NAME, c.STUDENTS from courses as c\n"
                    + "inner join users u\n"
                    + "on u.USERID=c.CREATEDBY\n"
                    + "where c.CREATEDBY=?\n"
                    + "");
            stmt.setInt(1, 1);
            crs.populate(stmt.executeQuery());
            stmt.close();
            stmt.getConnection().close();
            Collection<Row> rows = (Collection<Row>) crs.toCollection();
            courses.addAll(rows);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }

    public List<Row> getCourses1() {
        courses1 = new ArrayList<>();
        try {
            CachedRowSetImpl crs = new CachedRowSetImpl();
            PreparedStatement stmt = DatabaseUtils.getPreparedStatement("select u.USERID, u.NAME  || ' '  || u.surname, u.title, c.NAME, c.ID from studentcourses as sc\n"
                    + "inner join courses as c on c.ID=sc.COURSEID\n"
                    + "inner join users as u\n"
                    + "on u.USERID=c.CREATEDBY\n"
                    + "where sc.STATUS='accepted' and sc.STUDENTID=?");
            stmt.setInt(1, 2);
            crs.populate(stmt.executeQuery());
            stmt.close();
            stmt.getConnection().close();
            Collection<Row> rows = (Collection<Row>) crs.toCollection();
            courses1.addAll(rows);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses1;
    }

    public List<Row> getSenders() {
        senders = new ArrayList<>();
        try {
            CachedRowSetImpl crs = new CachedRowSetImpl();
            PreparedStatement stmt = DatabaseUtils.getPreparedStatement("select u.USERID, u.NAME, u.surname, u.FACULTY, u.SINIF from messages as m\n"
                    + "inner join users u\n"
                    + "on u.USERID=m.FROMUSER\n"
                    + "where m.TOUSER = ?");
            stmt.setInt(1, 1);
            crs.populate(stmt.executeQuery());
            stmt.close();
            stmt.getConnection().close();
            Collection<Row> rows = (Collection<Row>) crs.toCollection();
            senders.addAll(rows);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return senders;
    }

    public List<Row> getMessages() {
        messages = new ArrayList<>();
        try {
            CachedRowSetImpl crs = new CachedRowSetImpl();
            PreparedStatement stmt;
            if (type.equals("course")) {
                stmt = DatabaseUtils.getPreparedStatement("select * from messages where courseID = ?");
                stmt.setInt(1, toID);
            } else {
                stmt = DatabaseUtils.getPreparedStatement("select * from messages where touser = ? and fromuser = ?");
                stmt.setInt(1, 1);
                stmt.setInt(2, toID);
            }
            stmt.setInt(2, toID);
            crs.populate(stmt.executeQuery());
            stmt.close();
            stmt.getConnection().close();
            Collection<Row> rows = (Collection<Row>) crs.toCollection();
            messages.addAll(rows);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messages;
    }

    public List<Row> getMessages1() {
        messages1 = new ArrayList<>();
        try {
            CachedRowSetImpl crs = new CachedRowSetImpl();
            PreparedStatement stmt = DatabaseUtils.getPreparedStatement("select * from messages\n"
                    + "where (fromuser = ? and courseID = ?) or courseID = ?");
            stmt.setInt(1, 2);
            stmt.setInt(2, Integer.valueOf(type));
            stmt.setInt(3, Integer.valueOf(type));
            crs.populate(stmt.executeQuery());
            stmt.close();
            stmt.getConnection().close();
            Collection<Row> rows = (Collection<Row>) crs.toCollection();
            messages1.addAll(rows);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messages1;
    }

    public void test() {
        System.out.println("came here");
    }

    public void send() throws SQLException {
        System.out.println("ajax recieved");
        if (message.isEmpty()) {
            return;
        }
        TeacherBean teacherBean = (TeacherBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("teacherBean");

        PreparedStatement addEntry;
        if (type.equals("course")) {

            addEntry = DatabaseUtils.getPreparedStatement("INSERT INTO messages "
                    + "(courseID, fromUSER, message)"
                    + "VALUES (?, ?, ?)");
            addEntry.setInt(1, toID);
            addEntry.setInt(2, 1);
            addEntry.setString(3, message);
        } else {
            addEntry = DatabaseUtils.getPreparedStatement("INSERT INTO messages "
                    + "(toUSER, fromID, message)"
                    + "VALUES (?, ?, ?)");
            addEntry.setInt(1, toID);
            addEntry.setInt(2, 1);
            addEntry.setString(3, message);
        }

        if (addEntry.executeUpdate() == 0) {
            throw new AbortProcessingException();
        }
        addEntry.close();
        addEntry.getConnection().close();
        System.out.println("finished saving");

    } // end method save
    
    public void send1() throws SQLException {
        System.out.println("ajax recieved");
        if (message.isEmpty()) {
            return;
        }
        TeacherBean teacherBean = (TeacherBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("teacherBean");

        PreparedStatement addEntry;
        addEntry = DatabaseUtils.getPreparedStatement("INSERT INTO messages "
                + "(toUSER, fromUSER, courseID, message)"
                + "VALUES (?, ?, ?, ?)");
        addEntry.setInt(1, toID);
        addEntry.setInt(2, 2);
        addEntry.setInt(3, Integer.valueOf(type));
        addEntry.setString(4, message);

        if (addEntry.executeUpdate() == 0) {
            throw new AbortProcessingException();
        }
        addEntry.close();
        addEntry.getConnection().close();
        System.out.println("finished saving");

    } // end method save


}
