/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.controller;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import pl.domain.News;
import pl.service.Service;
import pl.util.Pages;

/**
 *
 * @author Maciej
 */
@Named(value = "newsController")
@SessionScoped
public class newsController implements Serializable {

    @EJB
    private Service service;
    private News currentNews;
    private News selectNews;
    private List <News> newsList = new ArrayList<> ();

    public List<News> getNewsList() {
         newsList = service.AllNews();
        return newsList;
    }

    public News getSelectNews(int i) {
        newsList = service.AllNews();
        selectNews = newsList.get(i);
        return selectNews;
    }
    
    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }
    
    public Service getService() {
        return service;
    }

    public newsController() {
        currentNews = new News();
    }

    public void setService(Service service) {
        this.service = service;
    }

    public News getCurrentNews() {
        return currentNews;
    }

    public void setCurrentNews(News currentNews) {
        this.currentNews = currentNews;
    }

    public String addNews() {
        currentNews.setDateNews(new Date()); // new Date() - domyślnie ustawia aktualną datę
        service.addNews(currentNews);
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Dodano ogłoszenie"));
         currentNews = new News();
        return Pages.ADMIN_MENAGE;
    }

}
