package pl.football.league.services;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import pl.football.league.controllers.CoachScreenController;
import pl.football.league.controllers.FanScreenController;
import pl.football.league.controllers.FootballerScreenController;
import pl.football.league.controllers.TeamScreenController;
import pl.football.league.entities.*;
import pl.football.league.fxmlUtils.TableControls;

import java.util.Date;
import java.util.List;

public class TableItemsShowService extends MainService {
    protected List currentData;

    public List<?> getCurrentData() {
        return currentData;
    }

    public void setCurrentData(List<?> currentData) {
        this.currentData = currentData;
    }

    protected void fillGridPane(GridPane gridPane, int columnSize) {
        if (currentData.isEmpty()) {
            return;
        }

        int i = 0;
        gridPane.getChildren().remove(0, gridPane.getChildren().size());

        if (currentData.get(0) instanceof Coach) {
            for (Object c : currentData) {
                Label name = TableControls.LabelVGrow(150, ((Coach) c).getName());
                gridPane.setHgrow(name, Priority.ALWAYS);
                name.setOnMouseClicked((MouseEvent event) -> {
                    CoachScreenController coachScreenController = new CoachScreenController();
                    coachScreenController.setDependencies(currentUser, entityManager, mainScreenController, c);
                    loadCenterScene("/fxml/coachScreen.fxml", coachScreenController);
                });

                Label surname = TableControls.LabelVGrow(150, ((Coach) c).getSurname());
                gridPane.setHgrow(surname, Priority.ALWAYS);
                surname.setOnMouseClicked(name.getOnMouseClicked());

                String result;
                if (((Coach) c).getAge() == null) {
                    result = "-";
                } else {
                    result = String.valueOf(((Coach) c).getAge());
                }
                Label age = TableControls.Label(150, result);
                age.setMaxWidth(1.7976931348623157E308);
                gridPane.setHgrow(age, Priority.ALWAYS);

                gridPane.addRow(i, name, surname, age);
                i++;
            }
        }
        else if (currentData.get(0) instanceof Fan) {
            for (Object f : currentData) {
                Label nameLabel = TableControls.LabelVGrow(150, ((Fan) f).getName());
                gridPane.setHgrow(nameLabel, Priority.ALWAYS);
                nameLabel.setOnMouseClicked((MouseEvent) -> {
                    FanScreenController fanScreenController = new FanScreenController();
                    fanScreenController.setDependencies(currentUser, entityManager, mainScreenController, f);
                    loadCenterScene("/fxml/fanScreen.fxml", fanScreenController);
                });

                Label surnameLabel = TableControls.LabelVGrow(150, ((Fan) f).getSurname());
                gridPane.setHgrow(surnameLabel, Priority.ALWAYS);
                surnameLabel.setOnMouseClicked(nameLabel.getOnMouseClicked());

                Label nicknameLabel = TableControls.LabelVGrow(150, ((Fan) f).getNickname());
                gridPane.setHgrow(nicknameLabel, Priority.ALWAYS);
                nicknameLabel.setOnMouseClicked(nameLabel.getOnMouseClicked());

                String result = String.valueOf(((Fan) f).getAge());
                if (result.equals("null"))
                    result = "-";
                Label ageLabel = TableControls.Label(50, result);

                gridPane.addRow(i, nameLabel, surnameLabel, nicknameLabel, ageLabel);
                i++;
            }
        }
        else if (currentData.get(0) instanceof Team) {
            for (Object t : currentData) {
                Label teamName = TableControls.LabelVGrow(200, ((Team) t).getName());
                gridPane.setHgrow(teamName, Priority.ALWAYS);
                gridPane.setVgrow(teamName, Priority.ALWAYS);
                teamName.setOnMouseClicked(event -> {
                    TeamScreenController teamScreenController = new TeamScreenController();
                    teamScreenController.setDependencies(currentUser, entityManager, mainScreenController, t);
                    loadCenterScene("/fxml/teamScreen.fxml", teamScreenController);
                });

                Label coachLabel = TableControls.LabelVGrow(200, ((Team) t).getCoach().getName() + " " + ((Team) t).getCoach().getSurname());
                gridPane.setHgrow(coachLabel, Priority.ALWAYS);
                gridPane.setVgrow(coachLabel, Priority.ALWAYS);
                coachLabel.setOnMouseClicked(event -> {
                    CoachScreenController coachScreenController = new CoachScreenController();
                    coachScreenController.setDependencies(currentUser, entityManager, mainScreenController, ((Team) t).getCoach());
                    loadCenterScene("/fxml/coachScreen.fxml", coachScreenController);
                });

                if (columnSize == 7) {
                    Label number = TableControls.Label(60, String.valueOf(currentData.indexOf(t) + 1));
                    Label points = TableControls.Label(30, String.valueOf(((Team) t).getPoints()));
                    Label wins = TableControls.Label(20, String.valueOf(((Team) t).getWins()));
                    Label draws = TableControls.Label(20, String.valueOf(((Team) t).getDraws()));
                    Label loses = TableControls.Label(20, String.valueOf(((Team) t).getLoses()));

                    gridPane.addRow(i, number, teamName, points, wins, draws, loses, coachLabel);
                }

                if (columnSize == 3) {
                    Date data = ((Team) t).getCreationDate();
                    String result;
                    if (data == null)
                        result = "-";
                    else
                        result = data.toString();

                    Label dateLabel = TableControls.Label(200, result);
                    dateLabel.setMaxWidth(1.7976931348623157E308);
                    dateLabel.setMaxHeight(1.7976931348623157E308);
                    gridPane.setHgrow(dateLabel, Priority.ALWAYS);
                    gridPane.setVgrow(dateLabel, Priority.ALWAYS);

                    gridPane.addRow(i, teamName, coachLabel, dateLabel);
                }

                i++;
            }
        }
        else if(currentData.get(0) instanceof Footballer){
            for (Object f : currentData) {
                Label name = TableControls.LabelVGrow(150, ((Footballer)f).getName());
                gridPane.setHgrow(name, Priority.ALWAYS);
                name.setOnMouseClicked(event->{
                    FootballerScreenController footballerScreenController = new FootballerScreenController();
                    footballerScreenController.setDependencies(currentUser, entityManager, mainScreenController, f);
                    loadCenterScene("/fxml/footballerScreen.fxml", footballerScreenController);
                });

                Label surname = TableControls.LabelVGrow(150, ((Footballer)f).getSurname());
                gridPane.setHgrow(surname, Priority.ALWAYS);
                surname.setOnMouseClicked(name.getOnMouseClicked());

                Label teamLabel;
                if(((Footballer)f).getTeam()!= null) {
                    teamLabel = TableControls.LabelVGrow(150, ((Footballer)f).getTeam().getName());
                    gridPane.setHgrow(teamLabel, Priority.ALWAYS);
                    teamLabel.setOnMouseClicked(event -> {
                        TeamScreenController teamScreenController = new TeamScreenController();
                        teamScreenController.setDependencies( currentUser, entityManager, mainScreenController, ((Footballer)f).getTeam());
                        loadCenterScene("/fxml/teamScreen.fxml", teamScreenController);
                    });
                }
                else{
                    teamLabel = TableControls.Label(150, "-");
                    teamLabel.setMaxWidth(1.7976931348623157E308);
                    gridPane.setHgrow(teamLabel, Priority.ALWAYS);
                }

                Label number;
                if(((Footballer)f).getNumber() != null) {
                    number = TableControls.Label(50, String.valueOf(((Footballer)f).getNumber()));
                }
                else {
                    number = TableControls.Label(50, "-");
                }

                Label position = TableControls.Label(60, ((Footballer)f).getPosition());
                gridPane.addRow(i, name, surname, teamLabel, number, position );
                i++;
            }
        }
        else if(currentData.get(0) instanceof Match){
            for(Object m : currentData){
                Label homeLabel = TableControls.LabelVGrow(150, ((Match)m).getMatchID().getHome().getName());
                gridPane.setHgrow(homeLabel, Priority.ALWAYS);
                homeLabel.setOnMouseClicked(event->{
                    TeamScreenController teamScreenController = new TeamScreenController();
                    teamScreenController.setDependencies(currentUser, entityManager, mainScreenController, ((Match)m).getMatchID().getHome());
                    loadCenterScene("/fxml/teamScreen.fxml", teamScreenController);
                });

                Label awayLabel = TableControls.LabelVGrow(150, ((Match)m).getMatchID().getAway().getName());
                gridPane.setHgrow(awayLabel, Priority.ALWAYS);
                awayLabel.setOnMouseClicked(event->{
                    TeamScreenController teamScreenController = new TeamScreenController();
                    teamScreenController.setDependencies(currentUser, entityManager, mainScreenController, ((Match)m).getMatchID().getAway());
                    loadCenterScene("/fxml/teamScreen.fxml", teamScreenController);
                });

                Label resultLabel = TableControls.Label(150, ((Match)m).getResultHome() + " : " + ((Match)m).getResultAway());
                resultLabel.setMaxWidth(1.7976931348623157E308);
                resultLabel.setMaxHeight(1.7976931348623157E308);
                gridPane.setHgrow(resultLabel, Priority.ALWAYS);

                Label dateLabel = TableControls.Label(150, ((Match)m).getMatchDate().toString());
                dateLabel.setMaxWidth(1.7976931348623157E308);
                dateLabel.setMaxHeight(1.7976931348623157E308);
                gridPane.setHgrow(dateLabel, Priority.ALWAYS);

                gridPane.addRow(i, homeLabel, resultLabel, awayLabel, dateLabel);
                i++;
            }
        }
        else
            System.out.println("Złe dane!");
    }
}
