/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import pl.domain.Round;
import pl.service.Service;

/**
 *
 * @author Maciej
 */
@Named(value ="roundController")
@SessionScoped
public class roundController implements Serializable{
     @EJB
     private Service service ;
     private List <Round> roundsList = new ArrayList<> ();


    public List<Round> getRoundsList() {
        roundsList = service.allRounds();
        return roundsList;
    }

    public void setRoundsList(List<Round> roundsList) {
        this.roundsList = roundsList;
    }
      
      
}
