package pl.football.league.entities;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class MatchID implements Serializable {
    @ManyToOne
    @JoinColumn(name = "gospodarz")
    private Team home;

    @ManyToOne
    @JoinColumn(name = "gosc")
    private Team away;

    public MatchID(){

    }

    public MatchID(Team home, Team away){
        this.home = home;
        this.away = away;
    }

    public Team getHome() {
        return home;
    }

    public void setHome(Team home) {
        this.home = home;
    }

    public Team getAway() {
        return away;
    }

    public void setAway(Team away) {
        this.away = away;
    }
}