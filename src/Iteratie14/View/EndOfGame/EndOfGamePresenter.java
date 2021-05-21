package Iteratie14.View.EndOfGame;

import Iteratie14.Model.HighScore;
import Iteratie14.Model.ParchisException;
import Iteratie14.Model.ParchisModel;
import Iteratie14.View.EnterPlayersScreen.EnterPlayersPresenter;
import Iteratie14.View.EnterPlayersScreen.EnterPlayersView;
import Iteratie14.View.HighscoresScreen.HighScorePresenter;
import Iteratie14.View.HighscoresScreen.HighScoreView;
import Iteratie14.View.MainScreen.MainScreenPresenter;
import Iteratie14.View.MainScreen.MainScreenView;
import Iteratie14.View.StartScreen.StartScreenPresenter;
import Iteratie14.View.StartScreen.StartScreenView;
import Iteratie14.View.UISettings;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


import java.net.MalformedURLException;
import java.nio.file.Files;

public class EndOfGamePresenter {
    private ParchisModel model;
    private EndOfGameView view;
    private UISettings uiSettings;

    public EndOfGamePresenter(ParchisModel model, EndOfGameView view, UISettings uiSettings) {
        this.model = model;
        this.view = view;
        this.uiSettings = uiSettings;
        this.addEventHandlers();


    }

    private void addEventHandlers() {
        view.getNieuwSpel().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                EnterPlayersView epView = new EnterPlayersView(uiSettings);
                EnterPlayersPresenter epPresenter = new EnterPlayersPresenter(model, epView, uiSettings);
                view.getScene().setRoot(epView);
                try {
                    epView.getScene().getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
                } catch (MalformedURLException ex) {
                    // // do nothing, if toURL-conversion fails, program can continue
                }
                epView.getScene().getWindow().sizeToScene();
                epView.getScene().getWindow().setX(uiSettings.getResX() / 20);
                epView.getScene().getWindow().setY(uiSettings.getResY() / 20);
                epView.getScene().getWindow().setHeight(9 * uiSettings.getResY() / 10);
                epView.getScene().getWindow().setWidth(9 * uiSettings.getResX() / 10);
                UISettings uiSettings = new UISettings();
                ParchisModel model = new ParchisModel();
                StartScreenView view = new StartScreenView(uiSettings);
                StartScreenPresenter presenter = new StartScreenPresenter(model, view, uiSettings);
                Scene scene = new Scene(view);
                if (uiSettings.styleSheetAvailable()){
                    try {
                        scene.getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
                    } catch (MalformedURLException ex) {
                        // do nothing, if toURL-conversion fails, program can continue
                    }
                }

            }
        });
    }

    }



