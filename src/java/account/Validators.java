/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author leon
 */
@ManagedBean(name = "validators")
public class Validators {
    public void validateEmail(FacesContext context, UIComponent component, Object o){
        System.out.println("object now"+ o.toString());
//        component
        throw new ValidatorException(new FacesMessage("email is invalid"));
    }
}
