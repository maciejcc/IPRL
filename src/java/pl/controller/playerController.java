/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import pl.domain.Player;
import pl.service.Service;
import pl.util.Pages;

/**
 *
 * @author Maciej
 */
@Named(value = "playerController")
@SessionScoped
public class playerController implements Serializable {

    @EJB
    private Service service;
    private Player currentPlayer;
    private Player selectPlayer;
    private boolean logged;
    private Player item;
    private List<Player> playersList = new ArrayList<>();
    private List<Player> wantToPlayPlayers = new ArrayList<>();
    private List<Player> notifiedPlayers = new ArrayList<>();
    private List<Player> usersToConfirm = new ArrayList<>();

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public String addPlayer() {
        if (currentPlayer.getPassword().equals(currentPlayer.getPasswordConfirm())) {
            if (service.idEmpty(currentPlayer.getLogin())) {
                if (service.AllPlayers().isEmpty()) {
                    currentPlayer.setPlayerRole(1);
                    currentPlayer.setEnabled(true);
                    service.addPlayer(currentPlayer);
                    FacesContext.getCurrentInstance().addMessage(null, 
                            new FacesMessage("Zarejestrowano użytkownika " + currentPlayer.getLogin()));
                    currentPlayer = new Player();
                    return Pages.GUEST_HOME;
                } else {
                    currentPlayer.setPlayerRole(2);
                    service.addPlayer(currentPlayer);
                    FacesContext.getCurrentInstance().addMessage(null, 
                            new FacesMessage("Zarejestrowano użytkownika " + currentPlayer.getLogin()));
                    currentPlayer = new Player();
                    return Pages.GUEST_HOME;
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage("Login " + currentPlayer.getLogin() + " jest już zajęty"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage("Hasła muszą być takie same!"));
        }
        return null;
    }

    public List<Player> getPlayersList() {
        playersList = service.AllPlayers();
        return playersList;
    }

    public void setPlayersList(List<Player> playersList) {
        this.playersList = playersList;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public playerController() {
        currentPlayer = new Player();
    }

    public Player getSelectPlayer() {
        return selectPlayer;
    }

    public void setSelectPlayer(Player selectPlayer) {
        this.selectPlayer = selectPlayer;
    }

    public Player getItem() {
        return item;
    }

    public void setItem(Player item) {
        this.item = item;
    }

    public String loggIn() {
        Player tmpPlayer = new Player();
        if (!service.idEmpty(currentPlayer.getLogin())) {
            tmpPlayer = service.findPersonByUsernameClient(currentPlayer.getLogin());
            if (currentPlayer.getLogin() != null && currentPlayer.getPasswordConfirm() != null) {
                if (tmpPlayer.getPassword().equals(currentPlayer.getPasswordConfirm())) {
                    currentPlayer = tmpPlayer;
                    if (currentPlayer.isEnabled() == true) {
                        logged = true;
                        if (currentPlayer.getPlayerRole() == 1) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pomyślnie zalogowano jako " + currentPlayer.getLogin()));
                            return Pages.GUEST_HOME;
                        } else {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pomyślnie zalogowano jako " + currentPlayer.getLogin()));
                            return Pages.GUEST_HOME;
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Logowanie nie powiodło się. Twoje konto czeka na akceptację przez administratora."));
                        return "";
                    }
                } else {
                    logged = false;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Błędny login lub hasło!!!!"));
                    return "";
                }

            } else {
                logged = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Błędny login lub hasło!"));
                return "";
            }
        } else {
            logged = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Nie ma użytkownika z takim loginem!"));
            return "";
        }
    }

    public String updatePlayerProfile() {
        if (currentPlayer.getPassword().equals(currentPlayer.getPasswordConfirm())) {
            service.updatePlayerProfile(currentPlayer);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Zaaktualizowano profil."));
            return Pages.PLAYER_PROFILE;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Podaj hasło w celu weryfikacji"));
            return Pages.PLAYER_PROFILE;
        }
    }

    public String logOut() {
        logged = false;
        currentPlayer = new Player();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Zostałeś poprawnie wylogowany."));
        return Pages.GUEST_HOME;
    }

    public List<Player> getAllPlayers() {
        playersList = service.AllPlayers();
        return playersList;
    }

    public String deletePlayer(int idPlayer) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Użytkownik usunięty"));
        service.deletePlayer(idPlayer);
        return Pages.ADMIN_USERS;
    }

    public String goToEditPlayer(Player player) {
        selectPlayer = player;
        return Pages.ADMIN_EDIT_PLAYER;
    }

    public String editPlayerProfile() {
        service.updatePlayerProfile(currentPlayer);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Zaaktualizowano profil użytkownika" + selectPlayer.getFirstName() + " " + selectPlayer.getLastName()));
        return Pages.ADMIN_USERS;
    }

    public String editPlayerProfile(Player player) {
        player = selectPlayer;
        service.updatePlayerProfile(selectPlayer);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Zaaktualizowano profil użytkownika" + selectPlayer.getFirstName() + " " + selectPlayer.getLastName()));
        return Pages.ADMIN_USERS;
    }

    public List<Player> getWantToPlayPlayers() {
        wantToPlayPlayers = service.wantToPlayPlayers();
        return wantToPlayPlayers;
    }

    public void setWantToPlayPlayers(List<Player> wantToPlayPlayers) {
        this.wantToPlayPlayers = wantToPlayPlayers;
    }

    public List<Player> getNotifiedPlayers() {
        notifiedPlayers = service.allNotifiedPlayers();
        return notifiedPlayers;
    }

    public void setNotifiedPlayers(List<Player> notifiedPlayers) {
        this.notifiedPlayers = notifiedPlayers;
    }

    public List<Player> getUsersToConfirm() {
        usersToConfirm = service.allUsersToConfirm();
        return usersToConfirm;
    }

    public void setUsersToConfirm(List<Player> usersToConfirm) {
        this.usersToConfirm = usersToConfirm;
    }

    public String confirmPlayer(Player player) {
        player.setEnabled(true);
        service.updatePlayerProfile(player);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Zaakceptowano rejestrację użytkownika" + player.getFirstName() + " " + player.getLastName()));
        return Pages.ADMIN_USERS;
    }
}
