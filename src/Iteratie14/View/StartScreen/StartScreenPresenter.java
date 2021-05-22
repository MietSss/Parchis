package Iteratie14.View.StartScreen;

import Iteratie14.Model.*;
import Iteratie14.UISettings;
import Iteratie14.View.EnterPlayersScreen.EnterPlayersPresenter;
import Iteratie14.View.EnterPlayersScreen.EnterPlayersView;
import javafx.event.*;
import javafx.scene.control.Alert;
import javafx.stage.WindowEvent;
import java.net.MalformedURLException;

public class StartScreenPresenter {

    private ParchisModel model;
    private StartScreenView view;
    private UISettings uiSettings;

    public StartScreenPresenter(ParchisModel model, StartScreenView view, UISettings uiSettings) {
        this.model = model;
        this.view = view;
        this.uiSettings = uiSettings;
        this.updateView();
        this.addEventHandlers();
    }

    private void updateView() {
    }

    private void addEventHandlers() {
        view.getTransition().setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                EnterPlayersView epView= new EnterPlayersView(uiSettings);
                EnterPlayersPresenter epPresenter=new EnterPlayersPresenter(model, epView, uiSettings);
                view.getScene().setRoot(epView);
                try {
                    epView.getScene().getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
                } catch (MalformedURLException ex) {
                    // // do nothing, if toURL-conversion fails, program can continue
                }
                epView.getScene().getWindow().sizeToScene();
                epView.getScene().getWindow().setX(uiSettings.getResX()/20);
                epView.getScene().getWindow().setY(uiSettings.getResY()/20);
                epView.getScene().getWindow().setHeight(9 * uiSettings.getResY()/10);
                epView.getScene().getWindow().setWidth(9 * uiSettings.getResX()/10);
                epPresenter.windowsHandler();
            }
        });
    }

    public void windowsHandler() {
        view.getScene().getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
             @Override
             public void handle(WindowEvent event) {
                 final Alert stopWindow = new Alert(Alert.AlertType.ERROR);
                 stopWindow.setHeaderText("You can not yet close the application.");
                 stopWindow.setContentText("Try again after the program has started");
                 stopWindow.showAndWait();
                 event.consume(); } });
    }
}
