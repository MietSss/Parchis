package Iteratie14.View.InfoScreen;

import Iteratie14.UISettings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

public class InfoScreenView extends BorderPane{

    private UISettings uiSettings;
    private TextArea spelregels;
    private Button okButton;

    public InfoScreenView(UISettings uiSettings) {
        this.uiSettings = uiSettings;
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        spelregels = new TextArea("Spelregels");
        okButton = new Button("OK");
        okButton.setPrefWidth(60);
    }

    private void layoutNodes() {
        setCenter(spelregels);
        spelregels.setPrefWidth(Double.MAX_VALUE);
        spelregels.setPrefHeight(Double.MAX_VALUE);
        spelregels.setWrapText(true);
        spelregels.setFont(Font.font("Arial", 12));
        spelregels.setEditable(false);
        setPadding(new Insets(uiSettings.getInsetsMargin()));
        BorderPane.setAlignment(okButton, Pos.CENTER_RIGHT);
        BorderPane.setMargin(okButton, new Insets(uiSettings.getInsetsMargin(), 0, 0, 0));
        setBottom(okButton);
        setPrefWidth(uiSettings.getLowestRes() / 4);
        setPrefHeight(uiSettings.getLowestRes() / 4);
    }

    TextArea getspelregels () {return spelregels;}

    Button getBtnOk() {
        return okButton;
    }
}
