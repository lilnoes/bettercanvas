/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.teacher;

import com.sun.faces.facelets.el.TagValueExpression;
import com.sun.rowset.CachedRowSetImpl;
import com.sun.rowset.internal.Row;
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
import javax.faces.event.AjaxBehaviorEvent;
import utils.DatabaseUtils;

/**
 *
 * @author leon
 */
@SessionScoped
@ManagedBean(name = "allowStudentsBean")
public class AllowStudentsBean implements Serializable{

    CachedRowSetImpl _students;
    List<Row> students;
    private int currentUserID;

    public void setCurrentUserID(int currentUserID) {
        this.currentUserID = currentUserID;
    }

    public int getCurrentUserID() {
        return currentUserID;
    }
    
    
    
    

    public void accept(int index) throws SQLException{
        _students.absolute(index);
        _students.updateString(5, "accepted");
        _students.updateRow();
        DatabaseUtils.acceptChanges(_students);
        
        System.out.println(_students.getObject(5));
    }

    public void deny(int index) throws SQLException{
        _students.absolute(index);
        _students.updateString(5, "rejected");
        _students.updateRow();
        DatabaseUtils.acceptChanges(_students);
        
        System.out.println(_students.getObject(5));
    }

    public List<Row> getStudents() {
        if (students != null) {
            return students;
        }
        students = new ArrayList<>();
        try {
            _students = new CachedRowSetImpl();
            PreparedStatement stmt = DatabaseUtils.getPreparedStatement("select * from studentcourses");
            _students.populate(stmt.executeQuery());
            stmt.close();
            stmt.getConnection().close();
            Collection<Row> rows = (Collection<Row>) _students.toCollection();
            students.addAll(rows);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    public String getID(Row row) {
        int outcome = 0;
        try {
            outcome = (int) row.getColumnObject(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(outcome);
    }
}
