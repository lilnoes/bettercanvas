/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author leon
 */
public class GeneralUtils {
    public static void addMessage(FacesContext fct, String id, String message){
        fct.addMessage(id, new FacesMessage(message));
    }
}
