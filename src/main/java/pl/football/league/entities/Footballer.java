package pl.football.league.entities;

import javax.persistence.*;
import java.util.Set;

/**
 * Klasa mapująca encję piłkarz z bazy danych
 * @author Marcin Cyc
 */
@Entity
@Table(name="pilkarz")
public class Footballer {
    /**
     * Identyfiaktor piłkarza
     */
    @Id
    @GeneratedValue
    @Column(name = "id_pilkarza")
    private long footballerID;

    /**
     * Imię piłkarza
     */
    @Column(name = "imie")
    private String name;

    /**
     * Nazwisko piłkarza
     */
    @Column(name = "nazwisko")
    private String surname;

    /**
     * Pozycja, na której gra piłkarz
     */
    @Column(name = "pozycja")
    private String position;

    /**
     * Numer na koszulce piłakrza
     */
    @Column(name ="numer")
    private Integer number;

    /**
     * Drużyna, w której gra piłkarz
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "druzyna")
    private Team team;

    public Footballer(){

    }

    /**
     * Zbiór kibiców, którzy wspierają piłkarza reprezentowanego przez obiekt tej klasy
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "kibicowanie_pilkarzowi",
            joinColumns = {@JoinColumn(name = "id_pilkarza")},
            inverseJoinColumns = {@JoinColumn(name = "id_kibica")})
    private Set<Fan> fans;

    /**
     * Tworzy  nowy obiekt piłkarza
     * @param id identyfikator piłkarza
     * @param name imię piłkarza
     * @param surname nazwisko piłkarza
     * @param position pozycja piłkarza
     * @param number numer na koszulce piłkarza
     * @param team drużyna, w której gra piłkarz
     */
    public Footballer(long id, String name, String surname, String position, Integer number, Team team ){
        this.footballerID = id;
        this.name = name;
        this.surname = surname;
        this.position = position;
        this.number = number;
        this.team = team;
    }


    public long getFootballerID() {
        return footballerID;
    }

    public void setFootballerID(long footballerID) {
        this.footballerID = footballerID;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Set<Fan> getFans() {
        return fans;
    }

    public void setFans(Set<Fan> fans) {
        this.fans = fans;
    }

    /**
     * Przekształca obiekt piłkarza na napis
     * @return imię + nazwisko piłkarza jako jeden napis
     */
    @Override
    public String toString() {
        return this.name + " " + surname;
    }
}
