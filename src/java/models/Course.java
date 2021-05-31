/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import utils.DatabaseUtils;

/**
 *
 * @author leon
 */
public class Course {

    public int id = 1;
    public String name = "";
    public String shortName = "";
    public int sinif = 1;
    public String faculty = "";
    public String description = "";
    public String requirements = "";
    public Timestamp creationDate;
    public Boolean locked = false;
    public int createdBy = 1;
    public int students = 0;

    public static Course getCourse(ResultSet res) throws SQLException {
        Course course = new Course();
        course.id = res.getInt(1);
        course.name = res.getString(2);
        course.shortName = res.getString(3);
        course.sinif = res.getInt(4);
        course.faculty = res.getString(5);
        course.description = res.getString(6);
        course.requirements = res.getString(7);
        course.creationDate = res.getTimestamp(8);
        course.locked = res.getBoolean(9);
        course.createdBy = res.getInt(10);
        course.students = res.getInt(11);
        return course;
    }

    public static Course fetchById(int id) {
        Course course = null;
        String sql = "select * from courses\n"
                + "where id = ?"
                + "fetch first 1 rows only";
        try {
            PreparedStatement statement = DatabaseUtils.getPreparedStatement(sql);
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            if (!res.next()) {
                return course;
            }
            course = getCourse(res);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return course;
    }
    
    public static List<Course> fetchByTeacher(int teacherID) {
        List<Course> courses = new ArrayList<>();
        String sql = "select * from courses\n"
                + "where createdBy = ?";
        try {
            PreparedStatement statement = DatabaseUtils.getPreparedStatement(sql);
            statement.setInt(1, teacherID);
            ResultSet res = statement.executeQuery();
            while(res.next()) courses.add(getCourse(res));
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public int getSinif() {
        return sinif;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getDescription() {
        return description;
    }

    public String getRequirements() {
        return requirements;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public Boolean getLocked() {
        return locked;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public int getStudents() {
        return students;
    }
    
    
}
