package pl.football.league.entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "mecz")
public class Match implements java.io.Serializable{
    @EmbeddedId
    private MatchID matchID;

    @Column(name = "data")
    private java.sql.Date matchDate;

    @Column(name = "wynik_gospodarz")
    private  int resultHome;

    @Column(name =  "wynik_gosc")
    private int resultAway;

    public Date getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }

    public int getResultHome() {
        return resultHome;
    }

    public void setResultHome(int resultHome) {
        this.resultHome = resultHome;
    }

    public int getResultAway() {
        return resultAway;
    }

    public void setResultAway(int resultAway) {
        this.resultAway = resultAway;
    }

    public MatchID getMatchID() {
        return matchID;
    }

    public void setMatchID(MatchID matchID) {
        this.matchID = matchID;
    }

    @Override
    public String toString() {
        return matchID.getHome() + " " +resultHome + " : " + resultAway + " " + getMatchID().getAway();
    }
}
