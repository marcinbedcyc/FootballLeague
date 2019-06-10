import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import pl.football.league.controllers.MainScreenController;

/**
 * Główna klasa programu, która uruchamia aplikację
 * @author Marcin Cyc
 * @see javafx.application.Application
 */
public class Main extends Application {
    /**
     * Tworzy połączenie z bazą danych oraz uruchamia pierwsze okno aplikacji
     * @param primaryStage główne okno programu
     * @throws Exception Nieprzechwycony wątek, który może wystąpić podczas operacji w tle
     */
    public void start(Stage primaryStage) throws Exception {
        EntityManagerFactory entityManagerFactory  = Persistence.createEntityManagerFactory("footballDataBase");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainScreen.fxml"));
        MainScreenController mainController = new MainScreenController();
        mainController.setEntityManager(entityManager);
        mainController.setStage(primaryStage);
        loader.setController(mainController);
        Parent root = loader.load();

        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
    }

    /**
     * Uruchamia program
     * @param args argumnety wywoałania w konsoli (nieistotne)
     */
    public static void main(String[] args) {
        launch(args);
    }
}
