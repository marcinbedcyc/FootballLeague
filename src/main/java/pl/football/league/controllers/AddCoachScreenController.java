package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.football.league.entities.Coach;
import pl.football.league.fxmlUtils.Alerts;
import pl.football.league.services.ItemAddService;

public class AddCoachScreenController extends ItemAddService {
    @FXML
    private Label titleLabel;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private TextField ageTextField;

    @FXML
    private Button acceptButton;

    @FXML
    private Button cancelButton;


    @FXML
    void addCoachAndBack() {
        String name, surname;
        int age;
        currentData = new Coach();

        name = nameTextField.getText();
        surname = surnameTextField.getText();

        if(name.equals("") || surname.equals("")){
            Alert emptyField = Alerts.emptyField();
            emptyField.showAndWait();
            return;
        }

        try {
            age = Integer.parseUnsignedInt(ageTextField.getText());
            ((Coach)currentData).setAge(age);
        }
        catch(NumberFormatException e){
            if(!ageTextField.getText().equals("")) {
                Alert wrongNumberAlert = Alerts.wrongNumber();
                wrongNumberAlert.showAndWait();
                return;
            }
        }

        ((Coach)currentData).setName(name);
        ((Coach)currentData).setSurname(surname);

        addItem(currentData);
        back();
    }

    @FXML
    void back() {
        if(isCanceled)
            buffer.put();
        stage.close();
    }

    @FXML
    void initialize() {
    }
}