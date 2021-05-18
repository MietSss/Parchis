package Iteratie14.View.EndOfGame;

import Iteratie14.View.UISettings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.net.MalformedURLException;
import java.nio.file.Files;

public class EndOfGameView extends BorderPane {
    private UISettings uiSettings;
    private Button voegToe;
  //  private Button btnOK;
    private ImageView confettiIV;
    private Label proficiat;
    private TextArea instructie;
    private Image confettiGIF;
    private TextField naam;
   // private final double WIDTH = imag.getWidth();
   // private final double HEIGHT = imag.getHeight();
    private Background backgroundConfetti;

    public EndOfGameView(UISettings uiSettings) {
        this.uiSettings = uiSettings;
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        proficiat = new Label("Proficiat! U hebt gewonnen!");
        instructie = new TextArea("Je hebt het spel in zo weinig beurten uitgespeeld, dat je behoort tot de top 5 spelers.\n" +
                "Vul je naam in en daarna op de knop \"voeg toe aan highscore\"");
     //   btnOK = new Button("OK");
        voegToe = new Button("voeg toe aan highscore");
        naam = new TextField();
        BackgroundSize bsize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true);

         if (Files.exists(uiSettings.getConfettiPath())) {
            try {
                confettiGIF = new Image(uiSettings.getConfettiPath().toUri().toURL().toString());
                backgroundConfetti = new Background(new
                        BackgroundImage(confettiGIF, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER, bsize));
            } catch (MalformedURLException ex) {
                // do nothing, if toURL-conversion fails, program can continue
            }
        }



      /*
        if (Files.exists(uiSettings.getConfettiPath())) {
            try {
                this.confettiIV = new ImageView(new Image(uiSettings.getConfettiPath().toUri().toURL().toString()));
            } catch (MalformedURLException ex) {
                // do nothing, if toURL-conversion fails, program can continue
            }
        }

       */
    }



    private void layoutNodes() {
    //    proficiat.setMaxSize(WIDTH * 4, HEIGHT / 2);
        proficiat.setFont(Font.font("Verdana", 30));
        proficiat.setAlignment(Pos.CENTER);
        instructie.setFont(Font.font("Verdana", 18));
       // instructie.setAlignment(Pos.CENTER);
        instructie.setMaxHeight(Double.MAX_VALUE);
        instructie.setMaxWidth(Double.MAX_VALUE);
        instructie.setWrapText(true);
        instructie.setEditable(false);
        naam.setPromptText("Geef een naam op");
        voegToe.setMaxSize(200,200);
       // voegToe.setPrefWidth(WIDTH/2);
      //  btnOK.setMaxSize(60, 60);
        this.setTop(proficiat);
        //VBox vBox1 = new VBox();
        //vBox1.getChildren().add(confettiIV);
        //vBox1.setAlignment(Pos.CENTER);
        VBox vBox2 = new VBox();
        VBox.setVgrow(instructie, Priority.ALWAYS);
        VBox.setVgrow(naam, Priority.ALWAYS);
        VBox.setVgrow(voegToe, Priority.ALWAYS);

        this.setBackground(backgroundConfetti);

        //VBox.setVgrow(confettiIV,Priority.ALWAYS);
        vBox2.getChildren().addAll(instructie, naam, voegToe);
        vBox2.setAlignment(Pos.CENTER);
       // vBox1.setSpacing(uiSettings.getInsetsMargin());
         vBox2.setSpacing(uiSettings.getInsetsMargin());
        //  hBox.getChildren().addAll(confettiIV, voegToe);
        //  hBox.setAlignment(Pos.CENTER);
        //  hBox.setSpacing(uiSettings.getInsetsMargin());
        //  this.setCenter(hBox);
     //   this.setLeft(confettiIV);
        this.setCenter(vBox2);

    //    this.setBottom(btnOK);
        BorderPane.setAlignment(proficiat, Pos.CENTER);
    //    BorderPane.setAlignment(confettiIV, Pos.CENTER);
        BorderPane.setAlignment(vBox2, Pos.CENTER);
    //    BorderPane.setAlignment(btnOK, Pos.BOTTOM_RIGHT);
        BorderPane.setMargin(proficiat, new Insets(uiSettings.getInsetsMargin()));
//        BorderPane.setMargin(confettiIV, new Insets(uiSettings.getInsetsMargin()));
        BorderPane.setMargin(instructie, new Insets(uiSettings.getInsetsMargin()));
        BorderPane.setMargin(naam, new Insets(uiSettings.getInsetsMargin()));
        BorderPane.setMargin(voegToe, new Insets(uiSettings.getInsetsMargin()));
    //    BorderPane.setMargin(btnOK, new Insets(uiSettings.getInsetsMargin()));


    }


 /*   Button getBtnOk() {
        return btnOK;
    }

  */

    public Button getVoegToe() {
        return voegToe;
    }

    public TextField getNaam() {
        return naam;
    }
}