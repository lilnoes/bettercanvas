/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.teacher;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.AjaxBehaviorEvent;
import utils.DatabaseUtils;
import utils.UserUtils;

/**
 *
 * @author leon
 */
@ManagedBean(name = "courseBean")
@ViewScoped
public class CourseBean implements Serializable{

    private String name = "";
    private String shortName = "";
    private int sinif = 1;
    private String faculty = "";
    private String description = "";
    private String requirements = "";

    public void register(AjaxBehaviorEvent evt) {
        System.out.println("registering");
        String sql = "insert into courses\n"
                + "(name, shortName, sinif, faculty, description, requirements, creationDate, locked, createdBy, students)\n"
                + "values\n"
                + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = DatabaseUtils.getPreparedStatement(sql);
            statement.setString(1, name);
            statement.setString(2, shortName);
            statement.setInt(3, sinif);
            statement.setString(4, faculty);
            statement.setString(5, description);
            statement.setString(6, requirements);
            statement.setTimestamp(7, Timestamp.from(Instant.now()));
            statement.setBoolean(8, false);
            statement.setInt(9, 1);
            statement.setInt(10, 0);
            int res = statement.executeUpdate();
            if (res == 0) {
                throw new AbortProcessingException();
            }
            FacesContext.getCurrentInstance().getViewRoot().getViewMap().clear();
            statement.close();
            statement.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public int getSinif() {
        return sinif;
    }

    public void setSinif(int sinif) {
        this.sinif = sinif;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }
}
