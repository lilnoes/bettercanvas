/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account;

import java.io.IOException;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import models.User;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

/**
 *
 * @author leon
 */
@ManagedBean(name = "validators")
public class Validators {
    public void validateEmail(FacesContext context, UIComponent component, Object email){
        String type = "";
        Boolean isStudent = Pattern.matches(".*@ogr[.]iuc[.]edu[.]tr", email.toString().trim());
        Boolean isTeacher = Pattern.matches(".*@iuc[.]edu[.]tr", email.toString().trim());
        if (!isStudent && !isTeacher)
            throw new ValidatorException(new FacesMessage("email should end in iuc.edu.tr"));
        if (User.emailVar(email.toString()))
            throw new ValidatorException(new FacesMessage("email already exists"));
        UserData data = (UserData)context.getExternalContext().getSessionMap().get("userData");
        if(isStudent) data.setType("student");
        else data.setType("teacher");
    }
    
    public void validatePasswordConfirmation(FacesContext context, UIComponent component, Object confpassword) {
        UserData data = (UserData)context.getExternalContext().getSessionMap().get("userData");
        if(data == null) return;
        if(data.getNewPassword().equals(confpassword)) return;
        throw new ValidatorException(new FacesMessage("Passwords should match"));
    }
}
