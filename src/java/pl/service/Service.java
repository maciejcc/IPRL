/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.service;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import pl.domain.Match;
import pl.domain.News;
import pl.domain.Player;
import pl.domain.Round;

@Stateless
public class Service {

    @PersistenceContext
    private EntityManager manager;

    public void addPlayer(Player player) {
        manager.persist(player);
    }

    public void addNews(News news) {
        manager.persist(news);
    }

    public boolean idEmpty(String login) {
        List result = manager.createQuery("select p from Player p where p.login = :login")
                .setParameter("login", login).getResultList();
        if (result.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public Player findPersonByUsernameClient(String login) {

        return (Player) manager.createQuery("select p from Player p where p.login = :login").setParameter("login", login).getSingleResult();
    }

    public List<Player> AllPlayers() {
        return manager.createQuery("Select p from Player p").getResultList();
    }

    public void updatePlayerProfile(Player player) {
        manager.merge(player);
    }

    public void deletePlayer(int id) {
        manager.createQuery("Delete FROM Player p WHERE p.id = :id").setParameter("id", id).executeUpdate();
    }

    public List<Player> wantToPlayPlayers() {
        return manager.createQuery("Select p from Player p where p.wantToPlay = true").getResultList();
    }

    public List<News> AllNews() {
        return manager.createQuery("Select n from News n ORDER by n.id DESC").getResultList();
    }

    public void addMatch(Match match) {
        manager.persist(match);
    }

    public List<Match> allMatches() {
        return manager.createQuery("Select m from spotkanie m").getResultList();
    }

    public List<Match> matchesOnRound(int nrRound) {
        return manager.createQuery("Select s from spotkanie s where s.nrRound = :nrRound").setParameter("nrRound", nrRound).getResultList();
    }

    public void addRound(Round round) {
        manager.persist(round);
    }

    public List<Round> allRounds() {
        return manager.createQuery("Select r from Round r").getResultList();
    }

     public List<Match> getFinishedMatchesOfPlayer(Player player){
          return manager.createQuery("Select s from spotkanie s WHERE (s.player1 = :player OR s.player2 = :player) AND s.finished = 1").setParameter("player", player).getResultList();
     }
     
     public List<Match> getMatchesWithOutScore(Player player){
          return manager.createQuery("Select s from spotkanie s WHERE (s.player1 = :player OR s.player2 = :player) AND (s.acceptedPlayer1 = false OR s.acceptedPlayer2 = false) AND (s.acceptedPlacePlayer1 = true AND s.acceptedPlacePlayer2 = true) ").setParameter("player", player).getResultList();
     }
     
      public List<Match> getMatchesWithOutPlace(Player player){
          return manager.createQuery("Select s from spotkanie s WHERE (s.player1 = :player OR s.player2 = :player) AND (s.acceptedPlacePlayer1 = false OR s.acceptedPlacePlayer2 = false)").setParameter("player", player).getResultList();
     }
     
     public void updateMatch(Match match) {
        manager.merge(match);
    }
     
     public List<Player> allNotifiedPlayers() {
        return manager.createQuery("Select p from Player p where p.notify = true ORDER BY p.wonMatches DESC, p.balanceMatches DESC, p.balanceSets DESC, p.wonSmallPoints DESC").getResultList();
    }
      public List<Player> allUsersToConfirm() {
        return manager.createQuery("Select p from Player p where p.enabled = false").getResultList();
    }
      
    public void deleteScheadule (){
         manager.createNativeQuery("Delete FROM maciek.round_spotkanie").executeUpdate();
         manager.createNativeQuery("Delete FROM spotkanie").executeUpdate();
         manager.createNativeQuery("Delete FROM round").executeUpdate();
    }
     
}
