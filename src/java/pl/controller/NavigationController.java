/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.controller;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import pl.util.Pages;


@ManagedBean
@SessionScoped
public class NavigationController implements Serializable {

    public String goToHome() {
        return Pages.GUEST_HOME;
    }

    public String goToLogin() {
        return Pages.GUEST_LOGIN;
    }

    public String goToRegister() {
        return Pages.GUEST_REGISTER;
    }

    public String goToRegulations() {
        return Pages.GUEST_REGULATIONS;
    }

    public String goToTable() {
        return Pages.GUEST_TABLE;
    }

    public String goToTimeTable() {
        return Pages.GUEST_TIMETABLE;
    }
    public String goToPlayerProfile() {
        return Pages.PLAYER_PROFILE;
    }

    public String goToPlayerContact() {
        return Pages.PLAYER_CONTACT;
    }

    public String goToPlayerMatches() {
        return Pages.PLAYER_MATCHES;
    }

    public String goToAdminHome() {
        return Pages.ADMIN_HOME;
    }

    public String goToAdminMenage() {
        return Pages.ADMIN_MENAGE;
    }

    public String goToAdminUsers() {
        return Pages.ADMIN_USERS;
    }

    public String goToAdminMatches() {
        return Pages.ADMIN_MATCHES;
    }

    public String goToAdminProfile() {
        return Pages.ADMIN_PROFILE;
    }

    public String goToAdminCoctact() {
        return Pages.ADMIN_CONTACT;
    }

    public String goToAdminEditPlayer(){
        return Pages.ADMIN_EDIT_PLAYER;
    }
    
    public String goToAdminUsersReadyToPlay(){
        return Pages.ADMIN_USER_READY_TO_PLAY;
    }
    
    public String goToAdminUsersToConfirm(){
        return Pages.ADMIN_USERS_TO_CONFIRM;
    }
}
