/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.teacher;

import com.sun.rowset.CachedRowSetImpl;
import com.sun.rowset.internal.Row;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import utils.DatabaseUtils;

/**
 *
 * @author leon
 */
@SessionScoped
@ManagedBean(name = "allowStudentsBean")
public class AllowStudentsBean {

    CachedRowSetImpl _students;
    List<Row> students;

    public void accept() {
        try {
            _students = new CachedRowSetImpl();
            PreparedStatement stmt = DatabaseUtils.getPreparedStatement("select * from studentcourses where status = 'waiting'");
            _students.populate(stmt.executeQuery());
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Row> getStudents() {
        if (students != null) {
            return students;
        }
        students = new ArrayList<>();
        accept();
        try {
            Collection<Row> rows = (Collection<Row>)_students.toCollection();
            students.addAll(rows);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }
    
    public int getID(Row row){
        int outcome = 0;
        try{
            outcome = (int)row.getColumnObject(1);
        }catch(Exception e){e.printStackTrace();}
        return outcome;
    }
}
