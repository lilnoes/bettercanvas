/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.derby.client.am.DateTime;

/**
 *
 * @author leon
 */
@ManagedBean(name = "globalConfig")
@ApplicationScoped
public class GlobalConfig {

    private String countries[] = {"", "Afghanistan", "Angola", "Rwanda", "Turkey"};
    private int years[] = {1, 2, 3, 4};
    private String faculties[] = {"", "Computer Engineering", "Electrical Engineering",
        "Control Engineering", "Mechanical Engineering"};

    public String[] getCountries() {
        return countries;
    }

    public int[] getYears() {
        return years;
    }

    public String[] getFaculties() {
        return faculties;
    }
    
    

}
