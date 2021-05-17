/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.derby.client.am.DateTime;

/**
 *
 * @author leon
 */
@ManagedBean(name = "localConfig")
@RequestScoped
public class LocalConfig {
    private String minDate;

    public String getMinDate() {
        if(minDate != null) return minDate;
        LocalDate min = LocalDate.now().minusYears(16);
        minDate = min.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return minDate;
    }
    
}
