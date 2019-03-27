package pl.football.league.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "kibic")
public class Fan {
    @Id
    @Column(name = "id_kibica")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long fanID;

    @Column(name = "imie")
    private String name;

    @Column(name = "nazwisko")
    private String surname;

    @Column(name = "wiek")
    private Integer age;

    @Column(name = "pseudonim")
    private String nickname;

    @Column(name = "haslo")
    private  String password;

    @ManyToMany(mappedBy = "fans")
    private Set<Footballer> supportedFootballers;

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

    @Override
    public String toString() {
        return name + " " + surname + " aka " + nickname;
    }
}
