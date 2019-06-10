package pl.football.league.entities;

import javax.persistence.*;

/**
 * Klasa mapująca encję trener z bazy danych
 * @author Marcin Cyc
 */
@Entity
@Table(name ="trener")
public class Coach {
    /**
     * Identyfikatora trenera
     */
    @Id
    @GeneratedValue
    @Column(name = "id_trenera")
    private long coachID;

    /**
     * Imię trenera
     */
    @Column(name = "imie")
    private String name;

    /**
     * Nazwisko trenera
     */
    @Column(name = "nazwisko")
    private String surname;

    /**
     * Wiek trenera
     */
    @Column (name = "wiek", nullable = true)
    private Integer age;

    /**
     * Tworzy nowy obiekt trenera i nie inicjalizuje żadnych wartości
     */
    public Coach(){
    }

    /**
     * Tworzy nowy obiekt trenera
     * @param id identyfikator trenera
     * @param name imię trenera
     * @param surname nazwisko trenera
     * @param age wiek trenera
     */
    public Coach(long id, String name, String surname, Integer age){
        coachID = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public long getCoachID() {
        return coachID;
    }

    public void setCoachID(long coachID) {
        this.coachID = coachID;
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

    /**
     * Przekształca obiekt trener na napis
     * @return imię + nazwisko trenera jako jeden napis
     */
    @Override
    public String toString() {
        return name + " " + surname;
    }
}
