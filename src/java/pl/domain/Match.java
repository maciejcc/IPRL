/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Maciej
 */
@Entity(name = "spotkanie")
public class Match implements Serializable {

   
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date matchDate;
    private String matchPlace;
    private boolean finished;
    private int nrRound;
    
    
    //Properties
    //Player 1
    @OneToOne
    private Player player1;
    private int scorePlayer1;   
    private int set1Player1;
    private int set2Player1;
    private int set3Player1;
    private int set4Player1;
    private int set5Player1;
    private int set6Player1;
    private int set7Player1;
    private boolean acceptedPlayer1;
    private boolean acceptedPlacePlayer1;
    //Properties
    //Player 2
    @OneToOne
    private Player player2;
    private int scorePlayer2;
    private int set1Player2;
    private int set2Player2;
    private int set3Player2; 
    private int set4Player2;
    private int set5Player2;
    private int set6Player2; 
    private int set7Player2;
    private boolean acceptedPlayer2;
    private boolean acceptedPlacePlayer2;

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }


    public Date getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }

    public String getMatchPlace() {
        return matchPlace;
    }

    public void setMatchPlace(String matchPlace) {
        this.matchPlace = matchPlace;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getScorePlayer1() {
        return scorePlayer1;
    }

    public void setScorePlayer1(int scorePlayer1) {
        this.scorePlayer1 = scorePlayer1;
    }

    public int getSet1Player1() {
        return set1Player1;
    }

    public void setSet1Player1(int set1Player1) {
        this.set1Player1 = set1Player1;
    }

    public int getSet2Player1() {
        return set2Player1;
    }

    public void setSet2Player1(int set2Player1) {
        this.set2Player1 = set2Player1;
    }

    public int getSet3Player1() {
        return set3Player1;
    }

    public void setSet3Player1(int set3Player1) {
        this.set3Player1 = set3Player1;
    }

    public int getSet4Player1() {
        return set4Player1;
    }

    public void setSet4Player1(int set4Player1) {
        this.set4Player1 = set4Player1;
    }

    public int getSet5Player1() {
        return set5Player1;
    }

    public void setSet5Player1(int set5Player1) {
        this.set5Player1 = set5Player1;
    }

    public int getSet6Player1() {
        return set6Player1;
    }

    public void setSet6Player1(int set6Player1) {
        this.set6Player1 = set6Player1;
    }

    public int getSet7Player1() {
        return set7Player1;
    }

    public void setSet7Player1(int set7Player1) {
        this.set7Player1 = set7Player1;
    }

    public boolean isAcceptedPlayer1() {
        return acceptedPlayer1;
    }

    public void setAcceptedPlayer1(boolean acceptedPlayer1) {
        this.acceptedPlayer1 = acceptedPlayer1;
    }

    public int getScorePlayer2() {
        return scorePlayer2;
    }

    public void setScorePlayer2(int scorePlayer2) {
        this.scorePlayer2 = scorePlayer2;
    }

    public int getSet1Player2() {
        return set1Player2;
    }

    public void setSet1Player2(int set1Player2) {
        this.set1Player2 = set1Player2;
    }

    public int getSet2Player2() {
        return set2Player2;
    }

    public void setSet2Player2(int set2Player2) {
        this.set2Player2 = set2Player2;
    }

    public int getSet3Player2() {
        return set3Player2;
    }

    public void setSet3Player2(int set3Player2) {
        this.set3Player2 = set3Player2;
    }

    public int getSet4Player2() {
        return set4Player2;
    }

    public void setSet4Player2(int set4Player2) {
        this.set4Player2 = set4Player2;
    }

    public int getSet5Player2() {
        return set5Player2;
    }

    public void setSet5Player2(int set5Player2) {
        this.set5Player2 = set5Player2;
    }

    public int getSet6Player2() {
        return set6Player2;
    }

    public void setSet6Player2(int set6Player2) {
        this.set6Player2 = set6Player2;
    }

    public int getSet7Player2() {
        return set7Player2;
    }

    public void setSet7Player2(int set7Player2) {
        this.set7Player2 = set7Player2;
    }

    public boolean isAcceptedPlayer2() {
        return acceptedPlayer2;
    }

    public void setAcceptedPlayer2(boolean acceptedPlayer2) {
        this.acceptedPlayer2 = acceptedPlayer2;
    }
       
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNrRound() {
        return nrRound;
    }

    public void setNrRound(int nrRound) {
        this.nrRound = nrRound;
    }

    public boolean isAcceptedPlacePlayer1() {
        return acceptedPlacePlayer1;
    }

    public void setAcceptedPlacePlayer1(boolean acceptedPlacePlayer1) {
        this.acceptedPlacePlayer1 = acceptedPlacePlayer1;
    }

    public boolean isAcceptedPlacePlayer2() {
        return acceptedPlacePlayer2;
    }

    public void setAcceptedPlacePlayer2(boolean acceptedPlacePlayer2) {
        this.acceptedPlacePlayer2 = acceptedPlacePlayer2;
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
        if (!(object instanceof Match)) {
            return false;
        }
        Match other = (Match) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.domain.Match[ id=" + id + " ]";
    }
    
}
