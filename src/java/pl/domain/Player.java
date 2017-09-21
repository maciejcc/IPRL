/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;


@Entity
public class Player implements Serializable {
    
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Pattern(regexp = "[a-zA-Z0-9_.\\-@]*", message = "Dopuszczalne znaki [A-Z][a-z][0-9][._-@], Niedopuszczalne są polskie znaki w loginie")
    @NotEmpty(message = "Pole Login nie może być puste!")
    private String login;
    @Size(min = 5, message = "Hasło musi być dłuższe niż 5 znaków!")
    private String password;
    
    @Transient
    private String passwordConfirm;
    
    @NotEmpty(message = "Wprowadź imię.")
    private String firstName;
    @NotEmpty(message = "Wprowadź nazwisko.")
    private String lastName;
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Niepoprawny adres e-mail!")
    private String email;
    
    private int contactNumber;
    private int playerRole;
    private boolean enabled;
    private boolean notify;
    private boolean wantToPlay;
    private int wonSmallPoints;
    private int wonSets;
    private int lostSmallPoints;
    private int lostSets;
    private int wonMatches;
    private int lostMatches;
    private int balanceMatches;
    private int balanceSets;
    private int balanceSmallPoints;

    public boolean isWantToPlay() {
        return wantToPlay;
    }

    public void setWantToPlay(boolean wantToPlay) {
        this.wantToPlay = wantToPlay;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(int contactNumber) {
        this.contactNumber = contactNumber;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isNotify() {
        return notify;
    }

    public void setNotify(boolean notify) {
        this.notify = notify;
    }

    public int getWonSmallPoints() {
        return wonSmallPoints;
    }

    public void setWonSmallPoints(int wonSmallPoints1) {
        this.wonSmallPoints = wonSmallPoints + wonSmallPoints1;
    }

    public int getWonSets() {
        return wonSets;
    }

    public void setWonSets(int wonSets1) {
        this.wonSets = wonSets + wonSets1 ;
    }

    public int getLostSmallPoints() {
        return lostSmallPoints;
    }

    public void setLostSmallPoints(int lostSmallPoints1) {
        this.lostSmallPoints = lostSmallPoints + lostSmallPoints1;
    }

    public int getLostSets() {
        return lostSets;
    }

    public void setLostSets(int lostSets1) {
        this.lostSets = lostSets + lostSets1 ;
    }

    public int getWonMatches() {
        return wonMatches;
    }

    public void setWonMatches(int wonMatches1) {
        this.wonMatches = wonMatches + wonMatches1;
    }

    public int getLostMatches() {
        return lostMatches;
    }

    public void setLostMatches(int lostMatches1) {
        this.lostMatches = lostMatches + lostMatches1;
    }

    public int getBalanceMatches() {
        balanceMatches = wonMatches - lostMatches;
        return balanceMatches;
    }

    public void setBalanceMatches(int balanceMatches) {
        this.balanceMatches = balanceMatches;
    }

    public int getBalanceSets() {
        balanceSets = wonSets - lostSets;
        return balanceSets;
    }

    public void setBalanceSets(int balanceSets) {
        this.balanceSets = balanceSets;
    }

    public int getBalanceSmallPoints() {
        balanceSmallPoints = wonSmallPoints - lostSmallPoints;
        return balanceSmallPoints;
    }

    public void setBalanceSmallPoints(int balanceSmallPoints) {
        this.balanceSmallPoints = balanceSmallPoints;
    }

    public int getPlayerRole() {
        return playerRole;
    }

    public void setPlayerRole(int playerRole) {
        this.playerRole = playerRole;
    }
    
    public void resetPlayerStats (int a){
        wonMatches = a;
        lostMatches = a;
        wonSets = a;
        lostSets = a;
        wonSmallPoints = a;
        lostSmallPoints = a;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Player)) {
            return false;
        }
        Player other = (Player) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.domain.User[ id=" + id + " ]";
    }
    
}
