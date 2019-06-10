package pl.football.league.services;

import pl.football.league.controllers.MainScreenController;
import pl.football.league.entities.Fan;

import javax.persistence.EntityManager;

/**
 * Serwis wyświetlający informajcę o danym elemencie z bazy
 *
 * @author Marcin Cyc
 * @see pl.football.league.services.MainService
 */
public class ItemShowService extends MainService {
    /**
     * Aktualnie wyswietlany element
     */
    protected Object currentData;

    /**
     * Ustawienie zależności, Korzysta z uswtawień zależności klasy MainService
     * @param currentUser obecnie zalogowany użytkownik
     * @param entityManager menadżer encji
     * @param mainScreenController kontroler głównego okna
     * @param currentData Aktualnie wyswietlany element
     */
    public void setDependencies(Fan currentUser, EntityManager entityManager, MainScreenController mainScreenController, Object currentData){
        super.setDependencies(currentUser, entityManager, mainScreenController);
        setCurrentData(currentData);
    }

    public Object getCurrentData() {
        return currentData;
    }

    public void setCurrentData(Object currentData) {
        this.currentData = currentData;
    }
}
