package Iteratie14.View.EndOfGame;

import Iteratie14.Model.ParchisModel;
import Iteratie14.View.EnterPlayersScreen.EnterPlayersPresenter;
import Iteratie14.View.EnterPlayersScreen.EnterPlayersView;
import Iteratie14.UISettings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


import java.net.MalformedURLException;

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

            }
        });
    }

    }



