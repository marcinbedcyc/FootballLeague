package pl.football.league.services;

import pl.football.league.controllers.MainScreenController;
import pl.football.league.entities.Fan;

import javax.persistence.EntityManager;

public class ItemShowService extends MainService {
    protected Object currentData;

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
