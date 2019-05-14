package pl.football.league.entities;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="pilkarz")
public class Footballer {
    @Id
    @GeneratedValue
    @Column(name = "id_pilkarza")
    private long footballerID;

    @Column(name = "imie")
    private String name;

    @Column(name = "nazwisko")
    private String surname;

    @Column(name = "pozycja")
    private String position;

    @Column(name ="numer")
    private Integer number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "druzyna")
    private Team team;

    public Footballer(){

    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "kibicowanie_pilkarzowi",
            joinColumns = {@JoinColumn(name = "id_pilkarza")},
            inverseJoinColumns = {@JoinColumn(name = "id_kibica")})
    private Set<Fan> fans;

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

    @Override
    public String toString() {
        return this.name + " " + surname;
    }
}
