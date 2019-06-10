package pl.football.league.entities;

import javax.persistence.*;
import java.util.Set;

/**
 * Klasa mapująca encję kibic z bazy danych
 * @author Marcin Cyc
 */
@Entity
@Table(name = "kibic")
public class Fan {
    /**
     * Identyfikator kibica
     */
    @Id
    @Column(name = "id_kibica")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long fanID;

    /**
     * Imię kibica
     */
    @Column(name = "imie")
    private String name;

    /**
     * Nazwisko kibica
     */
    @Column(name = "nazwisko")
    private String surname;

    /**
     * Wiek kibica
     */
    @Column(name = "wiek")
    private Integer age;

    /**
     * Pseudonim kibica
     */
    @Column(name = "pseudonim")
    private String nickname;

    /**
     * Hasło kibica
     */
    @Column(name = "haslo")
    private  String password;

    /**
     * Zbiór wspieranych piłkarzy przez kibica
     */
    @ManyToMany(mappedBy = "fans")
    private Set<Footballer> supportedFootballers;

    /**
     * Zbiór wspieranych drużyn przez kibica
     */
    @ManyToMany(mappedBy = "teamFans")
    private Set<Team> supportedTeams;

    public long getFanID() {
        return fanID;
    }

    public void setFanID(long fanID) {
        this.fanID = fanID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Footballer> getSupportedFootballers() {
        return supportedFootballers;
    }

    public void setSupportedFootballers(Set<Footballer> supportedFootballers) {
        this.supportedFootballers = supportedFootballers;
    }

    public Set<Team> getSupportedTeams() {
        return supportedTeams;
    }

    public void setSupportedTeams(Set<Team> supportedTeams) {
        this.supportedTeams = supportedTeams;
    }

    /**
     * Przekształca obiekt kibic na napis
     * @return imię + nazwisko + " a'ka " + pseudonim jako jeden napis
     */
    @Override
    public String toString() {
        return name + " " + surname + " a'ka " + nickname;
    }
}
