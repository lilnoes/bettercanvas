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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import utils.DatabaseUtils;

/**
 *
 * @author leon
 */
@SessionScoped
@ManagedBean(name = "allowStudentsBean")
public class AllowStudentsBean implements Serializable{

    List<Row> students;
    
    public void accept(int userID, int courseID) throws SQLException{
        PreparedStatement stmt = DatabaseUtils.getPreparedStatement("update studentcourses set status='accepted'\n"
                + "where studentid=? and courseID=?");
        stmt.setInt(1, userID);
        stmt.setInt(2, courseID);
        stmt.executeUpdate();
        DatabaseUtils.execute(String.format("update courses as c\n"
                + "set c.STUDENTS=(select count(*) from studentcourses as sc where sc.COURSEID=%d)\n"
                + "where c.ID=%d", courseID, courseID));
        stmt.close();
        stmt.getConnection().close();
    }

    public void deny(int userID, int courseID) throws SQLException {
        PreparedStatement stmt = DatabaseUtils.getPreparedStatement("update studentcourses set status='rejected'\n"
                + "where studentid=? and courseID=?");
        stmt.setInt(1, userID);
        stmt.setInt(2, courseID);
        stmt.executeUpdate();
        stmt.close();
        stmt.getConnection().close();
    }

    public List<Row> getStudents() {
        SessionData sessionData = (SessionData) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionData");
        students = new ArrayList<>();
        try {
            CachedRowSetImpl crs = new CachedRowSetImpl();
            PreparedStatement stmt = DatabaseUtils.getPreparedStatement("select u.name || ' ' || u.surname, u.SINIF, u.faculty, sc.STUDENTID, sc.COURSEID, u.picture from studentcourses as sc\n"
                    + "join courses as c on c.ID=sc.COURSEID\n"
                    + "join users as u\n"
                    + "on u.USERID=sc.STUDENTID\n"
                    + "where sc.STATUS='waiting' and c.CREATEDBY=?");
            stmt.setInt(1, sessionData.getUser().userID);
            crs.populate(stmt.executeQuery());
            stmt.close();
            stmt.getConnection().close();
            Collection rows = (Collection<Row>) crs.toCollection();
            students.addAll(rows);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }
}
