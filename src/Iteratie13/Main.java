package Iteratie13;

import iteratie12.model.Parchis;
import iteratie12.view.ParchisPresenter;
import iteratie12.view.ParchisView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Parchis model =
                new Parchis();
        ParchisView view =
                new ParchisView();
        new ParchisPresenter(model, view);
        primaryStage.setScene(new Scene(view));
        primaryStage.show();
    }
    public static void main(String[] args) {

        Application.launch(args);
    }
}