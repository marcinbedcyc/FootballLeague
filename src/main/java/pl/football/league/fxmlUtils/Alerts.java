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
}
