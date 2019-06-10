package pl.football.league.entities;

import javax.persistence.*;
import java.sql.Date;

/**
 * Klasa mapująca encję mecz z bazy danych
 * @author Marcin Cyc
 */
@Entity
@Table(name = "mecz")
public class Match implements java.io.Serializable{
    /**
     * Identyfiaktor meczu
     */
    @EmbeddedId
    private MatchID matchID;

    /**
     * Data rozegrania meczu
     */
    @Column(name = "data")
    private java.sql.Date matchDate;

    /**
     * Ilość bramek strzelona przez gospodarza meczu
     */
    @Column(name = "wynik_gospodarz")
    private  int resultHome;

    /**
     * Ilość bramek strzelona przez gościa meczu
     */
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

    /**
     * Przekształca obiekt meczu na napis
     * @return gospodarz wynik::wynik gość jako jeden napis
     */
    @Override
    public String toString() {
        return matchID.getHome() + " " +resultHome + " : " + resultAway + " " + getMatchID().getAway();
    }
}
