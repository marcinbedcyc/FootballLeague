package pl.football.league.entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MatchID implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gospodarz")
    private Team home;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gosc")
    private Team away;

    public MatchID(){}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MatchID)) return false;
        MatchID matchID = (MatchID) o;
        return Objects.equals(home, matchID.home) &&
                Objects.equals(away, matchID.away);
    }

    @Override
    public int hashCode() {
        return Objects.hash(home, away);
    }
}