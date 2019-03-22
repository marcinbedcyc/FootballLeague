package pl.football.league.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "druzyna")
public class Team {
    @Id
    @Column(name = "kod_druzyny")
    private int teamID;

    @Column(name = "nazwa")
    private String name;

    @Column(name = "data_zalozenia")
    private java.sql.Date creationDate;

    @Column(name = "ilosc_punktow")
    private int points;

    @Column(name = "zwyciestwa")
    private int wins;

    @Column(name = "remisy")
    private int draws;

    @Column(name = "porazki")
    private int loses;

    @Column(name = "trener_id_trenera")
    private int coach;

    public int getTeamID() {
        return teamID;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getLoses() {
        return loses;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }

    public int getCoach() {
        return coach;
    }

    public void setCoach(int coach) {
        this.coach = coach;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
