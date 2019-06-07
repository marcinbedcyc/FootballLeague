package pl.football.league.services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.football.league.controllers.MainScreenController;
import pl.football.league.entities.Fan;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;

public class MainService {
    protected EntityManager entityManager;
    protected MainScreenController mainScreenController;
    protected Fan currentUser;

    protected void loadCenterScene(String path, Object controller){
        Parent root = loadScene(path, controller);
        mainScreenController.getBorderPane().setCenter(root);
    }

    protected void loadLeftScene(String path, Object controller){
        Parent root = loadScene(path, controller);
        mainScreenController.getBorderPane().setLeft(root);
    }

    protected void loadNewStage(String path, Object controller, Stage stage){
        Parent root = loadScene(path, controller);
        stage.setScene(new Scene(root, 320, 600));
        stage.showAndWait();
        stage.setMinHeight(600);
        stage.setMinWidth(320);
    }

    private Parent loadScene(String path, Object controller){
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(path));
        loader.setController(controller);
        try{
            root = loader.load();
        }catch(IOException e){
            e.printStackTrace();
        }
        return root;
    }

    protected void closeService(){
        EntityManagerFactory entityManagerFactory = entityManager.getEntityManagerFactory();
        entityManager.close();
        entityManagerFactory.close();
    }

    public void setDependencies(Fan currentUser, EntityManager entityManager, MainScreenController mainScreenController){
        setCurrentUser(currentUser);
        setEntityManager(entityManager);
        setMainScreenController(mainScreenController);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public MainScreenController getMainScreenController() {
        return mainScreenController;
    }

    public void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    public Fan getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Fan currentUser) {
        this.currentUser = currentUser;
    }
}
