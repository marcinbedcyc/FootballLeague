package pl.football.league.fxmlUtils;

import javafx.scene.control.Alert;

public class Alerts {
    public static Alert wrongLoginAlert(){
        Alert  userAlreadyExistAlert = new Alert(Alert.AlertType.ERROR);
        userAlreadyExistAlert.setTitle("Zajety login");
        userAlreadyExistAlert.setHeaderText("Uyztkownik o podanym loginie juz istnieje!");
        userAlreadyExistAlert.setContentText("Prosze wybrac inny login.");
        return  userAlreadyExistAlert;
    }

    public static Alert diffrentPasswords(){
        Alert diffrentPasswordsAlert = new Alert(Alert.AlertType.ERROR);
        diffrentPasswordsAlert.setTitle("Rozne hasla");
        diffrentPasswordsAlert.setHeaderText("Podano 2 rozne hasla!");
        diffrentPasswordsAlert.setContentText("Prosze podac poprawne dane.");
        return  diffrentPasswordsAlert;
    }

    public static Alert wrongPassword(){
        Alert wrongPassword = new Alert(Alert.AlertType.ERROR);
        wrongPassword.setTitle("Zle haslo");
        wrongPassword.setHeaderText("Podane haslo jest nieprawidlowe");
        wrongPassword.setContentText("Prosze podac poprawne haslo.");

        return  wrongPassword;
    }

    public static  Alert emptyPasswordField(){
        Alert emptyPasswdFiled = new Alert(Alert.AlertType.ERROR);
        emptyPasswdFiled.setTitle("Puste pole z haslem");
        emptyPasswdFiled.setHeaderText("Nie podano nowego hasla");
        emptyPasswdFiled.setContentText("Prosze podac nowe haslo.");
        return emptyPasswdFiled;
    }

    public static Alert emptyField(){
        Alert emptyField = new Alert(Alert.AlertType.WARNING);
        emptyField.setTitle("Puste pole");
        emptyField.setHeaderText("Jedno z obowiazkowych pol jest puste!");
        emptyField.setContentText("Prosze je uzupelnic.");
        return emptyField;
    }

    public static Alert noCoach(){
        Alert noCoach = new Alert(Alert.AlertType.WARNING);
        noCoach.setTitle("Brak Trenera");
        noCoach.setHeaderText("Nie wybrano trenera drużyny");
        noCoach.setContentText("Prosze wybrać trenera z listy");
        return noCoach;
    }

    public static Alert busyCoach(){
        Alert busyCoach = new Alert(Alert.AlertType.ERROR);
        busyCoach.setTitle("Nie można usunąć");
        busyCoach.setHeaderText("Nie można usunąć, trener ma przypisaną drużynę.");
        busyCoach.setContentText("Usun druzyne lub zmien trenera drużynie aby usunąć tego trenera.");
        return busyCoach;
    }

    public static Alert sameTeams(){
        Alert sameTeams = new Alert(Alert.AlertType.WARNING);
        sameTeams.setTitle("Takie same drużyny");
        sameTeams.setHeaderText("Wybrano taką samą drużynę jako gospodarza i gościa meczu!");
        sameTeams.setContentText("Prosze zmienić jedną z drużyn.");
        return sameTeams;
    }

    public static Alert noHomeTeam(){
        Alert noHomeTeam = new Alert(Alert.AlertType.WARNING);
        noHomeTeam.setTitle("Brak gospodarza");
        noHomeTeam.setHeaderText("Nie wybrano gospodarza meczu.");
        noHomeTeam.setContentText("Proszę wybrać gopodarza meczu!");
        return  noHomeTeam;
    }

    public static Alert noAwayTeam(){
        Alert noAwayTeam = new Alert(Alert.AlertType.WARNING);
        noAwayTeam.setTitle("Brak gościa");
        noAwayTeam.setHeaderText("Nie wybrano gościa meczu.");
        noAwayTeam.setContentText("Proszę wybrać gościa meczu!");
        return  noAwayTeam;
    }

    public static Alert noPosition(){
        Alert noPostion = new Alert(Alert.AlertType.WARNING);
        noPostion.setTitle("Brak pozycji");
        noPostion.setHeaderText("Nie określono pozycji dla zawodnika!");
        noPostion.setContentText("Prosze wybrać.");
        return noPostion;
    }

    public static Alert transactionFail(){
        Alert transactionFail = new Alert(Alert.AlertType.ERROR);
        transactionFail.setTitle("Blad");
        transactionFail.setHeaderText("Blad przy dodowania pilkarza");
        transactionFail.setContentText("Prosze sprobowac jeszcze raz.");
        return transactionFail;
    }

    public static Alert wrongNumber(){
        Alert wrongNumberAlert = new Alert(Alert.AlertType.ERROR);
        wrongNumberAlert.setTitle("Zla liczba");
        wrongNumberAlert.setHeaderText("Podano lanuch znakow zamiast liczby!");
        wrongNumberAlert.setContentText("Prosze podac poprawne dane.");
        return wrongNumberAlert;
    }

    public static Alert loginFail(){
        Alert loginFailedAlert = new Alert(Alert.AlertType.INFORMATION);
        loginFailedAlert.setTitle("Blad logowania");
        loginFailedAlert.setHeaderText("Podane dane logowania sa niepoprawne!");
        loginFailedAlert.setContentText("Sprobuj ponowanie. ");
        return loginFailedAlert;
    }
}
