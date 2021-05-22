package Iteratie14.View.HighscoresScreen;

import Iteratie14.Model.ParchisModel;
import Iteratie14.UISettings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.BufferedReader;
import java.io.FileReader;

public class HighScorePresenter {
    private ParchisModel model;
    private HighScoreView view;
    private UISettings uiSettings;

    public HighScorePresenter(ParchisModel model, HighScoreView view, UISettings uiSettings) {
        this.model = model;
        this.view = view;
        this.uiSettings = uiSettings;

        view.gethighscores().setText(ReadInfoFromFile());

        view.getBtnOk().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                view.getScene().getWindow().hide();
            }
        });
    }

    private String ReadInfoFromFile() {
        String highscoreTextInFile = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(uiSettings.getHighscoreTextPath().toString()));) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                highscoreTextInFile += line + "\n";
            }
        } catch (Exception ex) {
            // do nothing, if info.txt file can not be read or is incomplete, or ... a standard text will be return

        }
        return (highscoreTextInFile.compareTo("") == 0) ? "No info available" : highscoreTextInFile;
    }
}

