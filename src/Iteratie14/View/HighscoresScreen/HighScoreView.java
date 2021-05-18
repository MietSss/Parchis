package Iteratie14.View.HighscoresScreen;

import Iteratie14.View.UISettings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

public class HighScoreView extends BorderPane {


    private UISettings uiSettings;

    private Label titleHighscores;
    private TextArea highscores;
    private Button okButton;

    public HighScoreView(UISettings uiSettings) {
        this.uiSettings = uiSettings;
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        highscores = new TextArea("Highscores");
        okButton = new Button("OK");
        okButton.setPrefWidth(60);
        this.titleHighscores = new Label("Parchis HighScores");
    }

    private void layoutNodes() {
        titleHighscores.setFont(Font.font("Verdana", 50));
        titleHighscores.setAlignment(Pos.CENTER);
        highscores.setPrefWidth(Double.MAX_VALUE);
        highscores.setPrefHeight(Double.MAX_VALUE);
        highscores.setWrapText(true);
        highscores.setFont(Font.font("Arial", 20));
        highscores.setEditable(false);
        setPadding(new Insets(uiSettings.getInsetsMargin()));
        BorderPane.setAlignment(okButton, Pos.CENTER_RIGHT);
        BorderPane.setMargin(okButton, new Insets(uiSettings.getInsetsMargin(), 0, 0, 0));
        this.setBottom(okButton);
        this.setPrefWidth(uiSettings.getLowestRes() / 4);
        this.setPrefHeight(uiSettings.getLowestRes() / 4);
        this.setTop(titleHighscores);
        this.setCenter(highscores);
    }

    TextArea gethighscores() {
        return highscores;
    }

    Button getBtnOk() {
        return okButton;
    }
}
