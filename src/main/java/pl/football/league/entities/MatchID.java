package pl.football.league.entities;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

/**
 * Klasa reprezntująca klucz główny dla encji mecz w bazie danych
 * @author Marcin Cyc
 */
@Embeddable
public class MatchID implements Serializable {
    /**
     * Gospodarz meczu
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gospodarz")
    private Team home;

    /**
     * Gość meczu
     */
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

    /**
     * Prównuje aktualny obiekt do obiektu podane w argumencie
     * @param o refernecja obiektu do porównania
     * @return true - jeśli obiekty są takie same. false - jeśli obiekty są różne
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MatchID)) return false;
        MatchID matchID = (MatchID) o;
        return Objects.equals(home, matchID.home) &&
                Objects.equals(away, matchID.away);
    }

    /**
     * Haszuje obiekt
     * @return zahaszowaną wartość obiektu
     */
    @Override
    public int hashCode() {
        return Objects.hash(home, away);
    }
}