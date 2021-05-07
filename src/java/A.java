
import javax.faces.bean.ManagedBean;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author leon
 */
@ManagedBean(name = "a")
public class A {
    String message = "leon";
    public String getMessage(){return this.message; }
}
