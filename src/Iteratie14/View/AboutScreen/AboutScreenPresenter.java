package Iteratie14.View.AboutScreen;

import Iteratie14.Model.*;
import Iteratie14.View.UISettings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class AboutScreenPresenter {

    private ParchisModel model;
    private AboutScreenView view;
    private UISettings uiSettings;

    public AboutScreenPresenter(ParchisModel model, AboutScreenView view, UISettings uiSettings) {
        this.model = model;
        this.view = view;
        this.uiSettings = uiSettings;
        view.getBtnOk().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                view.getScene().getWindow().hide();
            }
        });
    }
}
