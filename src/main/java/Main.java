import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import pl.football.league.entities.*;

public class Main extends Application {

    public void start(Stage primaryStage) throws Exception {
        Parent root  = FXMLLoader.load(getClass().getResource("fxml/loginScreen.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("footballDataBase");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Footballer pogba;
        Team manchester, barca;
        Match mecz;
        Fan kibic;
        Coach trener;

        //entityManager.getTransaction().begin();
        pogba = entityManager.find(Footballer.class, 33);
        manchester = entityManager.find(Team.class, 1);
        barca = entityManager.find(Team.class, 3);
        mecz = entityManager.find(Match.class, new MatchID(manchester,barca));
        kibic = entityManager.find(Fan.class, 1);
        trener = entityManager.find(Coach.class, 1);
        //entityManager.getTransaction().commit();
        System.out.println(pogba);
        System.out.println(manchester);
        System.out.println(trener);
        System.out.println(mecz);
        System.out.println(kibic);
        System.out.println("Kibicuje: ");
        System.out.println(kibic.getSupportedTeams());
        System.out.println("and");
        System.out.println(kibic.getSupportedFootballers());

        entityManager.close();
        entityManagerFactory.close();
        launch(args);
    }
}
