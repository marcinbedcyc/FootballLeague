package pl.football.league.entities;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "druzyna")
public class Team {
    @Id
    @GeneratedValue
    @Column(name = "kod_druzyny")
    private long teamID;

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

    @OneToOne
    @JoinColumn (name = "trener")
    private Coach coach;

    @ManyToMany
    @JoinTable(name = "kibicowanie_druzynie",
            joinColumns = {@JoinColumn(name = "kod_druzyny")},
            inverseJoinColumns = {@JoinColumn(name = "id_kibica")})
    private Set<Fan> teamFans;

    public long getTeamID() {
        return teamID;
    }

    public void setTeamID(long teamID) {
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

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public Set<Fan> getTeamFans() {
        return teamFans;
    }

    public void setTeamFans(Set<Fan> teamFans) {
        this.teamFans = teamFans;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
