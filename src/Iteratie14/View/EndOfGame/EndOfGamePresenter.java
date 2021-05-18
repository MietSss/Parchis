package Iteratie14.View.EndOfGame;

import Iteratie14.Model.HighScore;
import Iteratie14.Model.ParchisException;
import Iteratie14.Model.ParchisModel;
import Iteratie14.View.HighscoresScreen.HighScorePresenter;
import Iteratie14.View.HighscoresScreen.HighScoreView;
import Iteratie14.View.UISettings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.net.MalformedURLException;

public class EndOfGamePresenter {
    private ParchisModel model;
    private EndOfGameView view;
    private UISettings uiSettings;

    public EndOfGamePresenter(ParchisModel model, EndOfGameView view, UISettings uiSettings) {
        this.model = model;
        this.view = view;
        this.uiSettings = uiSettings;

    /*    view.getBtnOk().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                view.getScene().getWindow().hide();
            }
        });

     */

        view.getVoegToe().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                HighScore highscore = model.getHighScore();
                try {
                    model.getSpelerAanZet().setAantalBeurten(20);
                    highscore.voegToe(model.getSpelerAanZet(), view.getNaam().getText());

                } catch (ParchisException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Geen geldige naam");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
                try {
                    HighScoreView highScoreView = new HighScoreView(uiSettings);
                    HighScorePresenter highScorePresenter = new HighScorePresenter(model, highScoreView, uiSettings);
                    Stage highscoreStage = new Stage();
                    highscoreStage.setTitle("Highscores");
                    highscoreStage.initOwner(view.getScene().getWindow());
                    highscoreStage.initModality(Modality.APPLICATION_MODAL);
                    Scene scene2 = new Scene(highScoreView);
                    highscoreStage.setScene(scene2);
                    highscoreStage.setTitle(uiSettings.getApplicationName() + " - Settings");
                    highscoreStage.setX(view.getScene().getWindow().getX() + uiSettings.getResX() / 10);
                    highscoreStage.setY(view.getScene().getWindow().getY() + uiSettings.getResY() / 10);
                    highScoreView.getScene().getWindow().setHeight(7 * uiSettings.getResY() / 10);
                    highScoreView.getScene().getWindow().setWidth(7 * uiSettings.getResX() / 10);
                    if (uiSettings.styleSheetAvailable()) {
                        highscoreStage.getScene().getStylesheets().removeAll();
                        try {
                            highscoreStage.getScene().getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
                        } catch (MalformedURLException ex) {
                            // do nothing, if toURL-conversion fails, program can continue
                        }
                    }
                    highscoreStage.showAndWait();
                }catch(Exception e){
                  // nog op te zoeken waarom hij exceptions geeft
                }

            }


        });

    }


}
