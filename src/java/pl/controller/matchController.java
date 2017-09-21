/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import pl.domain.Match;
import pl.domain.Player;
import pl.domain.Round;
import pl.util.Pages;
import pl.service.Service;

/**
 *
 * @author Maciej //
 */
@Named(value = "matchController")
@SessionScoped
public class matchController implements Serializable {

    @EJB
    private Service service;
    private Match match = new Match();
    private List<Player> wantToPlayPlayers = new ArrayList<>();
    private List<Match> matchesList = new ArrayList<>();
    private List<Match> finishedMatchesOfPlayer = new ArrayList<>();
    private List<Match> matchesWithOutScore = new ArrayList<>();
    private List<Match> matchesWithOutPlace = new ArrayList<>();
    private List<Match> matchesListOnRound = new ArrayList<>();
    private Player currentPlayer;
    private Match selectMatch;

    public String createSchedule() {
        wantToPlayPlayers.addAll(service.wantToPlayPlayers());
        for (Player x : wantToPlayPlayers) {
            x.resetPlayerStats(0);
            x.setNotify(true);
            service.updatePlayerProfile(x);
        }
        int totalRounds = (wantToPlayPlayers.size() - 1);
        int matchesPerRound = (wantToPlayPlayers.size() / 2);

        for (int nrRound = 0; nrRound < totalRounds; nrRound++) {
            Round round = new Round();
            round.setNrRound(nrRound + 1);
            matchesListOnRound = new ArrayList<>();
            for (int nrMatch = 0; nrMatch < matchesPerRound; nrMatch++) {
                match = new Match();
                match.setNrRound(nrRound + 1);
                match.setPlayer1(wantToPlayPlayers.get((nrRound + nrMatch) % (wantToPlayPlayers.size() - 1)));
                match.setPlayer2(wantToPlayPlayers.get((wantToPlayPlayers.size() - 1 - nrMatch + nrRound) % (wantToPlayPlayers.size() - 1)));
                if (nrMatch == 0) {
                    match.setPlayer2(wantToPlayPlayers.get(wantToPlayPlayers.size() - 1));
                }
                matchesListOnRound.add(match);
                service.addMatch(match);
            }
            round.setMatchesOnRound(matchesListOnRound);
            service.addRound(round);
        }
        matchesListOnRound.clear();
        wantToPlayPlayers.clear();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Wygenerowano terminarz!"));
        sendMailToAll(wantToPlayPlayers, "Liga ruszyła!", "Drodzy zawodnicy! \n\n Właśnie wygenerowano terminarz meczów w ping-pongowej lidze! Możecie już ustalać terminy spotkań a kolejno zapisywać rezultaty! "
                + "\n\n Zapraszamy na www.iprl.pl do zakładki MOJE MECZE.");
        return Pages.ADMIN_MATCHES;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public List<Player> getWantToPlayPlayers() {
        return wantToPlayPlayers;
    }

    public void setWantToPlayPlayers(List<Player> wantToPlayPlayers) {
        this.wantToPlayPlayers = wantToPlayPlayers;
    }

    public List<Match> getMatchesList() {
        matchesList = service.allMatches();
        return matchesList;
    }

    public void setMatchList(List<Match> matchList) {
        this.matchesList = matchList;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public List<Match> getMatchesListOnRound(int nrRound) {
        matchesListOnRound = service.matchesOnRound(nrRound);
        return matchesListOnRound;
    }

    public void setMatchesListOnRound(List<Match> matchesListOnRound) {
        this.matchesListOnRound = matchesListOnRound;
    }

    public List<Match> getFinishedMatchesOfPlayer() {
        finishedMatchesOfPlayer = service.getFinishedMatchesOfPlayer(currentPlayer);
        return finishedMatchesOfPlayer;
    }

    public Match getSelectMatch() {
        return selectMatch;
    }

    public void setSelectMatch(Match selectMatch) {
        this.selectMatch = selectMatch;
    }

    public String goToEditMatchScore(Match match) {
        selectMatch = match;
        return Pages.PLAYER_EDIT_MATCH_SCORE;
    }

    public String goToEditMatchPlace(Match match) {
        selectMatch = match;
        return Pages.PLAYER_EDIT_MATCH_PLACE;
    }

    public List<Match> getMatchesWithOutScore() {
        matchesWithOutScore = service.getMatchesWithOutScore(currentPlayer);
        return matchesWithOutScore;
    }

    public void setMatchesWithOutScore(List<Match> matchesWithOutScore) {
        this.matchesWithOutScore = matchesWithOutScore;
    }

    public List<Match> getMatchesWithOutPlace() {
        matchesWithOutPlace = service.getMatchesWithOutPlace(currentPlayer);
        return matchesWithOutPlace;
    }

    public void setMatchesWithOutPlace(List<Match> matchesWithOutPlace) {
        this.matchesWithOutPlace = matchesWithOutPlace;
    }

    public String editMatchScore(Match selectMatch) {

        if (currentPlayer.equals(selectMatch.getPlayer1())) {
            selectMatch.setAcceptedPlayer1(true);
            selectMatch.setAcceptedPlayer2(false);
        }
        if (currentPlayer.equals(selectMatch.getPlayer2())) {
            selectMatch.setAcceptedPlayer2(true);
            selectMatch.setAcceptedPlayer1(false);

        }
        service.updateMatch(selectMatch);
        if (selectMatch.isAcceptedPlayer1() && selectMatch.isAcceptedPlayer2()) {
            selectMatch.setFinished(true);
            if (selectMatch.getScorePlayer1() > selectMatch.getScorePlayer2()) {
                selectMatch.getPlayer1().setWonMatches(1);
                selectMatch.getPlayer2().setLostMatches(1);
            }
            selectMatch.getPlayer1().setWonSets(selectMatch.getScorePlayer1());
            selectMatch.getPlayer2().setWonSets(selectMatch.getScorePlayer2());
            selectMatch.getPlayer1().setLostSets(selectMatch.getScorePlayer2());
            selectMatch.getPlayer2().setLostSets(selectMatch.getScorePlayer1());
            selectMatch.getPlayer1().setWonSmallPoints(selectMatch.getSet1Player1() + selectMatch.getSet2Player1() + selectMatch.getSet3Player1() + selectMatch.getSet4Player1() + selectMatch.getSet5Player1() + selectMatch.getSet6Player1() + selectMatch.getSet7Player1());
            selectMatch.getPlayer2().setWonSmallPoints(selectMatch.getSet1Player2() + selectMatch.getSet2Player2() + selectMatch.getSet3Player2() + selectMatch.getSet4Player2() + selectMatch.getSet5Player2() + selectMatch.getSet6Player2() + selectMatch.getSet7Player2());
            selectMatch.getPlayer2().setLostSmallPoints(selectMatch.getSet1Player1() + selectMatch.getSet2Player1() + selectMatch.getSet3Player1() + selectMatch.getSet4Player1() + selectMatch.getSet5Player1() + selectMatch.getSet6Player1() + selectMatch.getSet7Player1());
            selectMatch.getPlayer1().setLostSmallPoints(selectMatch.getSet1Player2() + selectMatch.getSet2Player2() + selectMatch.getSet3Player2() + selectMatch.getSet4Player2() + selectMatch.getSet5Player2() + selectMatch.getSet6Player2() + selectMatch.getSet7Player2());
        }
        service.updatePlayerProfile(selectMatch.getPlayer1());
        service.updatePlayerProfile(selectMatch.getPlayer2());
        service.updateMatch(selectMatch);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pomyślnie edytowano wynik spotkania"));
        return Pages.PLAYER_MATCHES;
    }

    public String editMatchPlace(Match selectMatch) {

        if (currentPlayer.equals(selectMatch.getPlayer1())) {
            selectMatch.setAcceptedPlacePlayer1(true);
            selectMatch.setAcceptedPlacePlayer2(false);
        }
        if (currentPlayer.equals(selectMatch.getPlayer2())) {
            selectMatch.setAcceptedPlacePlayer2(true);
            selectMatch.setAcceptedPlacePlayer1(false);

        }

        service.updateMatch(selectMatch);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pomyślnie edytowano datę i miejsce spotkania"));
        return Pages.PLAYER_MATCHES;
    }

    public String setAcceptPlace(Match selectMatch) {
        if (currentPlayer.equals(selectMatch.getPlayer1())) {
            selectMatch.setAcceptedPlacePlayer1(true);
        }
        if (currentPlayer.equals(selectMatch.getPlayer2())) {
            selectMatch.setAcceptedPlacePlayer2(true);

        }
        service.updateMatch(selectMatch);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Zaakceptowano datę i miejsce spotkania"));
        return Pages.PLAYER_MATCHES;
    }

    public String setAcceptScore(Match selectMatch) {
        if (currentPlayer.equals(selectMatch.getPlayer1())) {
            selectMatch.setAcceptedPlayer1(true);
        }
        if (currentPlayer.equals(selectMatch.getPlayer2())) {
            selectMatch.setAcceptedPlayer2(true);

        }
        if (selectMatch.isAcceptedPlayer1() && selectMatch.isAcceptedPlayer2()) {
            selectMatch.setFinished(true);
            if (selectMatch.getScorePlayer1() > selectMatch.getScorePlayer2()) {
                selectMatch.getPlayer1().setWonMatches(1);
                selectMatch.getPlayer2().setLostMatches(1);
            } else {
                selectMatch.getPlayer2().setWonMatches(1);
                selectMatch.getPlayer1().setLostMatches(1);
            }
            selectMatch.getPlayer1().setWonSets(selectMatch.getScorePlayer1());
            selectMatch.getPlayer2().setWonSets(selectMatch.getScorePlayer2());
            selectMatch.getPlayer1().setLostSets(selectMatch.getScorePlayer2());
            selectMatch.getPlayer2().setLostSets(selectMatch.getScorePlayer1());
            selectMatch.getPlayer1().setWonSmallPoints(selectMatch.getSet1Player1() + selectMatch.getSet2Player1() + selectMatch.getSet3Player1() + selectMatch.getSet4Player1() + selectMatch.getSet5Player1() + selectMatch.getSet6Player1() + selectMatch.getSet7Player1());
            selectMatch.getPlayer2().setWonSmallPoints(selectMatch.getSet1Player2() + selectMatch.getSet2Player2() + selectMatch.getSet3Player2() + selectMatch.getSet4Player2() + selectMatch.getSet5Player2() + selectMatch.getSet6Player2() + selectMatch.getSet7Player2());
            selectMatch.getPlayer2().setLostSmallPoints(selectMatch.getSet1Player1() + selectMatch.getSet2Player1() + selectMatch.getSet3Player1() + selectMatch.getSet4Player1() + selectMatch.getSet5Player1() + selectMatch.getSet6Player1() + selectMatch.getSet7Player1());
            selectMatch.getPlayer1().setLostSmallPoints(selectMatch.getSet1Player2() + selectMatch.getSet2Player2() + selectMatch.getSet3Player2() + selectMatch.getSet4Player2() + selectMatch.getSet5Player2() + selectMatch.getSet6Player2() + selectMatch.getSet7Player2());
        }
        service.updateMatch(selectMatch);
        service.updatePlayerProfile(selectMatch.getPlayer1());
        service.updatePlayerProfile(selectMatch.getPlayer2());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Zaakceptowano wynik spotkania"));
        return Pages.PLAYER_MATCHES;
    }

    public String getWalkover1(Match selectMatch) {
        selectMatch.setScorePlayer1(4);
        selectMatch.setScorePlayer2(0);
        selectMatch.setSet1Player1(11);
        selectMatch.setSet1Player2(0);
        selectMatch.setSet2Player1(11);
        selectMatch.setSet2Player2(0);
        selectMatch.setSet3Player1(11);
        selectMatch.setSet3Player2(0);
        selectMatch.setSet4Player1(11);
        selectMatch.setSet4Player2(0);
        selectMatch.getPlayer2().setLostMatches(1);
        selectMatch.getPlayer1().setWonMatches(1);
        selectMatch.setAcceptedPlacePlayer1(true);
        selectMatch.setAcceptedPlacePlayer2(true);
        selectMatch.setAcceptedPlayer1(true);
        selectMatch.setAcceptedPlayer2(true);
        selectMatch.setFinished(true);
        selectMatch.setMatchPlace("Mecz się nie odbył");
        service.updateMatch(selectMatch);

        selectMatch.getPlayer1().setWonSets(selectMatch.getScorePlayer1());
        selectMatch.getPlayer2().setWonSets(selectMatch.getScorePlayer2());
        selectMatch.getPlayer1().setLostSets(selectMatch.getScorePlayer2());
        selectMatch.getPlayer2().setLostSets(selectMatch.getScorePlayer1());
        selectMatch.getPlayer1().setWonSmallPoints(selectMatch.getSet1Player1() + selectMatch.getSet2Player1() + selectMatch.getSet3Player1() + selectMatch.getSet4Player1() + selectMatch.getSet5Player1() + selectMatch.getSet6Player1() + selectMatch.getSet7Player1());
        selectMatch.getPlayer2().setWonSmallPoints(selectMatch.getSet1Player2() + selectMatch.getSet2Player2() + selectMatch.getSet3Player2() + selectMatch.getSet4Player2() + selectMatch.getSet5Player2() + selectMatch.getSet6Player2() + selectMatch.getSet7Player2());
        selectMatch.getPlayer2().setLostSmallPoints(selectMatch.getSet1Player1() + selectMatch.getSet2Player1() + selectMatch.getSet3Player1() + selectMatch.getSet4Player1() + selectMatch.getSet5Player1() + selectMatch.getSet6Player1() + selectMatch.getSet7Player1());
        selectMatch.getPlayer1().setLostSmallPoints(selectMatch.getSet1Player2() + selectMatch.getSet2Player2() + selectMatch.getSet3Player2() + selectMatch.getSet4Player2() + selectMatch.getSet5Player2() + selectMatch.getSet6Player2() + selectMatch.getSet7Player2());

        service.updatePlayerProfile(selectMatch.getPlayer1());
        service.updatePlayerProfile(selectMatch.getPlayer2());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Przyznano walkower"));
        return Pages.ADMIN_MATCHES;
    }

    public String getWalkover2(Match selectMatch) {

        selectMatch.setScorePlayer1(0);
        selectMatch.setScorePlayer2(4);
        selectMatch.setSet1Player1(0);
        selectMatch.setSet1Player2(11);
        selectMatch.setSet2Player1(0);
        selectMatch.setSet2Player2(11);
        selectMatch.setSet3Player1(0);
        selectMatch.setSet3Player2(11);
        selectMatch.setSet4Player1(0);
        selectMatch.setSet4Player2(11);
        selectMatch.setAcceptedPlacePlayer1(true);
        selectMatch.setAcceptedPlacePlayer2(true);
        selectMatch.setAcceptedPlayer1(true);
        selectMatch.setAcceptedPlayer2(true);
        selectMatch.setFinished(true);
        selectMatch.setMatchPlace("Mecz się nie odbył");
        service.updateMatch(selectMatch);
        selectMatch.getPlayer1().setLostMatches(1);
        selectMatch.getPlayer2().setWonMatches(1);
        selectMatch.getPlayer1().setWonSets(selectMatch.getScorePlayer1());
        selectMatch.getPlayer2().setWonSets(selectMatch.getScorePlayer2());
        selectMatch.getPlayer1().setLostSets(selectMatch.getScorePlayer2());
        selectMatch.getPlayer2().setLostSets(selectMatch.getScorePlayer1());
        selectMatch.getPlayer1().setWonSmallPoints(selectMatch.getSet1Player1() + selectMatch.getSet2Player1() + selectMatch.getSet3Player1() + selectMatch.getSet4Player1() + selectMatch.getSet5Player1() + selectMatch.getSet6Player1() + selectMatch.getSet7Player1());
        selectMatch.getPlayer2().setWonSmallPoints(selectMatch.getSet1Player2() + selectMatch.getSet2Player2() + selectMatch.getSet3Player2() + selectMatch.getSet4Player2() + selectMatch.getSet5Player2() + selectMatch.getSet6Player2() + selectMatch.getSet7Player2());
        selectMatch.getPlayer2().setLostSmallPoints(selectMatch.getSet1Player1() + selectMatch.getSet2Player1() + selectMatch.getSet3Player1() + selectMatch.getSet4Player1() + selectMatch.getSet5Player1() + selectMatch.getSet6Player1() + selectMatch.getSet7Player1());
        selectMatch.getPlayer1().setLostSmallPoints(selectMatch.getSet1Player2() + selectMatch.getSet2Player2() + selectMatch.getSet3Player2() + selectMatch.getSet4Player2() + selectMatch.getSet5Player2() + selectMatch.getSet6Player2() + selectMatch.getSet7Player2());

        service.updatePlayerProfile(selectMatch.getPlayer1());
        service.updatePlayerProfile(selectMatch.getPlayer2());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Przyznano walkower"));
        return Pages.ADMIN_MATCHES;
    }

    public String adminEditMatch(Match selectMatch) {

        selectMatch.setAcceptedPlacePlayer1(true);
        selectMatch.setAcceptedPlacePlayer2(true);
        selectMatch.setAcceptedPlayer1(true);
        selectMatch.setAcceptedPlayer2(true);
        selectMatch.setFinished(true);
        if (selectMatch.getScorePlayer1() > selectMatch.getScorePlayer2()) {
            selectMatch.getPlayer1().setWonMatches(1);
            selectMatch.getPlayer2().setLostMatches(1);
        } else {
            selectMatch.getPlayer2().setWonMatches(1);
            selectMatch.getPlayer1().setLostMatches(1);
        }
        selectMatch.getPlayer1().setWonSets(selectMatch.getScorePlayer1());
        selectMatch.getPlayer2().setWonSets(selectMatch.getScorePlayer2());
        selectMatch.getPlayer1().setLostSets(selectMatch.getScorePlayer2());
        selectMatch.getPlayer2().setLostSets(selectMatch.getScorePlayer1());
        selectMatch.getPlayer1().setWonSmallPoints(selectMatch.getSet1Player1() + selectMatch.getSet2Player1() + selectMatch.getSet3Player1() + selectMatch.getSet4Player1() + selectMatch.getSet5Player1() + selectMatch.getSet6Player1() + selectMatch.getSet7Player1());
        selectMatch.getPlayer2().setWonSmallPoints(selectMatch.getSet1Player2() + selectMatch.getSet2Player2() + selectMatch.getSet3Player2() + selectMatch.getSet4Player2() + selectMatch.getSet5Player2() + selectMatch.getSet6Player2() + selectMatch.getSet7Player2());
        selectMatch.getPlayer2().setLostSmallPoints(selectMatch.getSet1Player1() + selectMatch.getSet2Player1() + selectMatch.getSet3Player1() + selectMatch.getSet4Player1() + selectMatch.getSet5Player1() + selectMatch.getSet6Player1() + selectMatch.getSet7Player1());
        selectMatch.getPlayer1().setLostSmallPoints(selectMatch.getSet1Player2() + selectMatch.getSet2Player2() + selectMatch.getSet3Player2() + selectMatch.getSet4Player2() + selectMatch.getSet5Player2() + selectMatch.getSet6Player2() + selectMatch.getSet7Player2());

        service.updatePlayerProfile(selectMatch.getPlayer1());
        service.updatePlayerProfile(selectMatch.getPlayer2());
        service.updateMatch(selectMatch);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pomyślnie edytowano wynik spotkania"));
        return Pages.ADMIN_MATCHES;

    }

    public String goToAdminEditMatch(Match match) {
        selectMatch = match;
        return Pages.ADMIN_EDIT_MATCH;
    }

    public void sendMail(String emailTo, String emailFrom, String subject, String text, String senderFirstName, String senderLastName) {
        final String username = "iprl.mail.service@gmail.com";
        final String password = "Maciek1995";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailFrom));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(emailTo));
            message.setSubject("Internetowa platmorma rozgrywek ligowych: " + subject);
            message.setText("Witaj! \n\n Użytkownik " + senderFirstName + " " + senderLastName + " " + text);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public String sendMailToAll(List<Player> addresseeList, String subject, String text) {
        final String username = "iprl.mail.service@gmail.com";
        final String password = "Maciek1995";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        for (Player player : addresseeList) {
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("iprl.mail.service@gmail.com"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(player.getEmail()));
                message.setSubject("Internetowa platmorma rozgrywek ligowych: " + subject);
                message.setText(text);
                Transport.send(message);
                System.out.println("Done");
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Wiadomość została wysłana do wszystkich zarejestrowanych użytkowników"));
        return Pages.ADMIN_MENAGE;
    }

    public String deleteAllMatches() {
        service.deleteScheadule();
        wantToPlayPlayers.addAll(service.wantToPlayPlayers());
        for (Player x : wantToPlayPlayers) {
            x.resetPlayerStats(0);
            x.setNotify(true);
            service.updatePlayerProfile(x);
        }
        wantToPlayPlayers.clear();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Terminarz został poprawnie usunięty."));
        return Pages.ADMIN_MATCHES;
    }
    
    public String resetPlayersStats() {
        wantToPlayPlayers.addAll(service.wantToPlayPlayers());
        for (Player x : wantToPlayPlayers) {
            x.resetPlayerStats(0);
            x.setNotify(true);
            service.updatePlayerProfile(x);
        }
        wantToPlayPlayers.clear();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Statystyki wszystkich użytkowników zostały zresetowane"));
        return Pages.ADMIN_MATCHES;
    }
    
}
