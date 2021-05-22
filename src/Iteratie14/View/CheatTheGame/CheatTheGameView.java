package Iteratie14.View.CheatTheGame;

import Iteratie14.UISettings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class CheatTheGameView extends BorderPane {
    private UISettings uiSettings;
    private Label titleHighscores;
    


    public CheatTheGameView(UISettings uiSettings) {
        this.uiSettings = uiSettings;
        initialiseNodes();
        layoutNodes();
    }

    public void initialiseNodes() {
        this.titleHighscores = new Label("Parchis HighScores");
    }

    public void layoutNodes() {
        titleHighscores.setFont(Font.font("Verdana", 50));
        titleHighscores.setAlignment(Pos.CENTER);
        this.setTop(titleHighscores);
        BorderPane.setAlignment(titleHighscores, Pos.CENTER);
        BorderPane.setMargin(titleHighscores, new Insets(uiSettings.getInsetsMargin()));
        GridPane highscoreList = new GridPane();
        this.setCenter(highscoreList);
        highscoreList.setGridLinesVisible(true);

    }
}