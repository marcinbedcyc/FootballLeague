import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main extends Application {

    public void start(Stage primaryStage) throws Exception {
        Parent root  = FXMLLoader.load(getClass().getResource("fxml/loginScreen.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        System.out.println(new String("Hello world"));

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("footballDataBase");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Footballer pogba;
        Team manchester;

        //entityManager.getTransaction().begin();
        pogba = entityManager.find(Footballer.class, 33);
        manchester = entityManager.find(Team.class, 1);
        //entityManager.getTransaction().commit();
        System.out.println(pogba);
        System.out.println(manchester);

        entityManager.close();
        entityManagerFactory.close();
        launch(args);
    }
}
