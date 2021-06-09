/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.teacher;

import com.sun.faces.context.SessionMap;
import com.sun.rowset.CachedRowSetImpl;
import com.sun.rowset.internal.Row;
import config.SessionData;
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
@ManagedBean(name = "announcementBean")
@ViewScoped
public class AnnouncementBean implements Serializable {

    private List<Row> announcements;

    private String title = "";
    private String content = "";

    public List<Row> getAnnouncements() {
        SessionData sessionData = (SessionData) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionData");
        announcements = new ArrayList<>();
        try {
            CachedRowSetImpl crs = new CachedRowSetImpl();
            PreparedStatement stmt = DatabaseUtils.getPreparedStatement("select title, summary, content, createdAt from announcements where courseID = ?");
            stmt.setInt(1, sessionData.currentCourse);
            crs.populate(stmt.executeQuery());
            stmt.close();
            stmt.getConnection().close();
            Collection<Row> rows = (Collection<Row>) crs.toCollection();
            announcements.addAll(rows);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return announcements;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String create() {
        SessionData sessionData = (SessionData) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionData");
        System.out.println(content + " content title " + title);
        try {
            TeacherBean teacherBean = (TeacherBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("teacherBean");
            System.out.println("creating announcement");
            String sql = "insert into announcements\n"
                    + "(courseID, createdBY, title, summary, content, createdAt, type)\n"
                    + "values\n"
                    + "(?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = DatabaseUtils.getPreparedStatement(sql);
            stmt.setInt(1, sessionData.currentCourse); //current course ID
            stmt.setInt(2, sessionData.getUser().userID);
            stmt.setString(3, title);
            stmt.setString(4, content.substring(0, content.length() > 40 ? 40 : content.length()));
            stmt.setString(5, content);
            stmt.setTimestamp(6, Timestamp.from(Instant.now()));
            stmt.setString(7, "announcement");
            stmt.executeUpdate();
            teacherBean.setAnnouncements();
            stmt.close();
            stmt.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new AbortProcessingException();
        }
        
        
        return null;
    }

}
