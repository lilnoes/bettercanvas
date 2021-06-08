/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.student;

import com.sun.faces.context.SessionMap;
import com.sun.rowset.CachedRowSetImpl;
import com.sun.rowset.internal.Row;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import utils.DatabaseUtils;

/**
 *
 * @author leon
 */
@ManagedBean(name = "availableCoursesBean")
@ViewScoped
public class AvailableCoursesBean implements Serializable {

    private List<Row> courses;

    public List<Row> getCourses() {
        courses = new ArrayList<>();
        try {
            CachedRowSetImpl crs = new CachedRowSetImpl();
            PreparedStatement stmt = DatabaseUtils.getPreparedStatement("select u.name, u.title, cc.* from (select c.CREATEDBY, c.ID, c.NAME, s.status, c.SINIF from courses as c\n"
                    + "left outer join studentcourses as s\n"
                    + "on c.ID=s.COURSEID and s.STUDENTID=?) as cc\n"
                    + "join users as u\n"
                    + "on cc.createdBY=u.USERID\n"
                    + "where cc.sinif=u.SINIF");
            stmt.setInt(1, 2);
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

    public void join(String id) {
        try {
            System.out.println("joining class " + id);
            String sql = "insert into studentcourses\n"
                    + "(courseID, studentID, status)\n"
                    + "values\n"
                    + "(?, ?, ?)";
            PreparedStatement stmt = DatabaseUtils.getPreparedStatement(sql);
            stmt.setInt(1, Integer.valueOf(id));
            stmt.setInt(2, 2); //current studentID
            stmt.setString(3, "waiting");
            stmt.executeUpdate();
            stmt.close();
            stmt.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new AbortProcessingException();
        }
    }

}
