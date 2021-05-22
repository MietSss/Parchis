package Iteratie14.View.EnterPlayersScreen;

import Iteratie14.Model.Kleur;
import Iteratie14.UISettings;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.MalformedURLException;
import java.nio.file.Files;


/**
 * @author Miet Smets
 * @version 1.0 06/05/2021 21:44
 */
public class EnterPlayersView extends GridPane {
    //  private Label titleParchis;
    private ImageView titleParchisIm;
    private TextField activePlayers;
    private Button start;
    private Label player1;
    private TextField namePlayer1;
    private ComboBox colorPlayer1;
    private Label player2;
    private TextField namePlayer2;
    private ComboBox colorPlayer2;
    private Label player3;
    private TextField namePlayer3;
    private ComboBox colorPlayer3;
    private Label player4;
    private TextField namePlayer4;
    private ComboBox colorPlayer4;
    private UISettings uiSettings;
    private static final double TEXTFIELD_HEIGHT = 100;
    private static final double TEXTFIELD_WIDTH = 100;
    private static final double LABEL_HEIGHT = 100;
    private static final double LABEL_WIDTH = 200;
    private Button confirm;


    public EnterPlayersView(UISettings uiSettings) {
        this.uiSettings = uiSettings;
        this.initialiseNodes();
        this.layoutNodes();
    }

    private void initialiseNodes() {
        //de breedte en lengte van het scherm worden berekent op basis van het scherm van de gebruiker
        final double WIDTH = uiSettings.getResX() * 0.75;
        final double HEIGHT = uiSettings.getResY() * 0.75;
        confirm = new Button("Confirm");
        confirm.setDisable(false);
        confirm.setPrefSize(LABEL_WIDTH, LABEL_HEIGHT);

        //titel afbeelding wordt opgehaald - exception gegooid
        if (Files.exists(uiSettings.getTitleParchis())) {
            try {
                this.titleParchisIm = new ImageView(new Image(uiSettings.getTitleParchis().toUri().toURL().toString()));
            } catch (MalformedURLException ex) {
                // do nothing, if toURL-conversion fails, program can continue
            }
        } else { // do nothing, if AboutImage is not available, program can continue
        }

        //bepaalt de hoogte en breedte van het titellogo
       // this.titleParchisIm.setFitHeight(HEIGHT * 0.3);
       // this.titleParchisIm.setFitWidth(WIDTH * 0.3);

        //   this.titleParchis = new Label("WELCOME TO PARCHIS");
        //  this.titleParchis.setPrefSize(WIDTH,HEIGHT);

        //  this.activePlayers = new TextField("Enter number of players: 2-4");
        this.activePlayers = new TextField();
        this.activePlayers.setPromptText("Enter aantal spelers: 2-4 en klik dan op Confirm");
        this.activePlayers.setPrefSize(LABEL_WIDTH, LABEL_HEIGHT);

        this.start = new Button("Start Parchis");
        this.start.setPrefSize(LABEL_WIDTH, LABEL_HEIGHT);
        this.start.setDisable(false);

        String instructieNaam = "Geef een naam in";
        String instructieKleur = "Kies een kleur";
        this.player1 = new Label("Player 1");
        this.player1.setPrefSize(LABEL_WIDTH, LABEL_HEIGHT);
        player1.setDisable(true);
        //  this.namePlayer1= new TextField("Enter name");
        this.namePlayer1 = new TextField();
        namePlayer1.setPromptText(instructieNaam);
        namePlayer1.setDisable(true);
        this.namePlayer1.setPrefSize(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT);
        this.colorPlayer1 = new ComboBox<>();
        //  ObservableList<String> availableColors1 = FXCollections.observableArrayList("GROEN", "ROOD", "GEEL","BLAUW" );
        //  this.colorPlayer1.setItems(availableColors1);
        this.colorPlayer1.setPromptText(instructieKleur);
        //this.colorPlayer1.getSelectionModel().selectFirst();
        this.colorPlayer1.setPrefSize(LABEL_WIDTH, LABEL_HEIGHT);
        colorPlayer1.setDisable(true);

        this.player2 = new Label("Player 2");
        this.player2.setPrefSize(LABEL_WIDTH, LABEL_HEIGHT);
        // this.namePlayer2 = new TextField("Enter name");
        player2.setDisable(true);
        namePlayer2 = new TextField();
        this.namePlayer2.setPromptText(instructieNaam);
        this.namePlayer2.setPrefSize(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT);
        namePlayer2.setDisable(true);
        this.colorPlayer2 = new ComboBox<>();
        //    ObservableList<String> availableColors2 = FXCollections.observableArrayList("GROEN", "ROOD", "GEEL","BLAUW" );
        //    this.colorPlayer2.setItems(availableColors2);
        colorPlayer2.setPromptText(instructieKleur);
        // this.colorPlayer2.getSelectionModel().selectFirst();
        this.colorPlayer2.setPrefSize(LABEL_WIDTH, LABEL_HEIGHT);
        colorPlayer2.setDisable(true);

        this.player3 = new Label("Player 3");
        this.player3.setPrefSize(LABEL_WIDTH, LABEL_HEIGHT);
        this.player3.setDisable(true);
        //  this.namePlayer3 = new TextField("Enter name");
        this.namePlayer3 = new TextField();
        this.namePlayer3.setPromptText(instructieNaam);
        this.namePlayer3.setPrefSize(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT);
        this.namePlayer3.setDisable(true);
        this.colorPlayer3 = new ComboBox<>();
        //    ObservableList<String> availableColors3 = FXCollections.observableArrayList("GROEN", "ROOD", "GEEL","BLAUW" );
        //    this.colorPlayer3.setItems(availableColors3);
        colorPlayer3.setPromptText(instructieKleur);
        // this.colorPlayer3.getSelectionModel().selectFirst();
        this.colorPlayer3.setPrefSize(LABEL_WIDTH, LABEL_HEIGHT);
        this.colorPlayer3.setDisable(true);

        /*checken of er een 3de speler is _ dan worden velden actief
        if(Integer.parseInt(getActivePlayers().toString())>=3){
            this.namePlayer3.setDisable(false);
            this.colorPlayer3.setDisable(false);
            this.colorPlayer3.setDisable(false);
        }
*/


        this.player4 = new Label("Player 4");
        this.player4.setPrefSize(LABEL_WIDTH, LABEL_HEIGHT);
        this.player4.setDisable(true);
        //  this.namePlayer4 = new TextField("Enter name");
        namePlayer4 = new TextField();
        namePlayer4.setPromptText(instructieNaam);
        this.namePlayer4.setPrefSize(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT);
        this.namePlayer4.setDisable(true);
        this.colorPlayer4 = new ComboBox<>();
        //    ObservableList<String> availableColor4 = FXCollections.observableArrayList("GROEN", "ROOD", "GEEL","BLAUW" );
        //   this.colorPlayer4.setItems(availableColor4);
        // this.colorPlayer4.getSelectionModel().selectFirst();
        colorPlayer4.setPromptText(instructieKleur);
        this.colorPlayer4.setPrefSize(LABEL_WIDTH, LABEL_HEIGHT);
        this.colorPlayer4.setDisable(true);

        /* checken of er een 4de speler is _ dan worden velden actief
        if(Integer.parseInt(getActivePlayers().toString())==4){

            this.namePlayer4.setDisable(false);
            this.colorPlayer4.setDisable(false);
            this.colorPlayer4.setDisable(false);
        }

         */

    }

    private void layoutNodes() {
        //de breedte en lengte en de marges van het scherm worden berekent op basis van het scherm van de gebruiker
        final double INSETS_MARGIN = uiSettings.getInsetsMargin();
        final double WIDTH = uiSettings.getResX() * 0.75;
        final double HEIGHT = uiSettings.getResY() * 0.75;
/*

        this.titleParchis.setFont(new Font("Arial",70));
        this.titleParchis.setTextAlignment(TextAlignment.CENTER);
        this.titleParchis.setAlignment(Pos.CENTER);
        this.titleParchis.setPadding(new Insets(INSETS_MARGIN));
        this.titleParchis.setStyle("-fx-background-color: WHITE");
        this.titleParchis.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
*/


        // iedere node wordt in het midden gealligneerd, krijgt een witte achtergrond, zwarte kader, en padding

        this.activePlayers.setAlignment(Pos.CENTER);
        this.activePlayers.setPadding(new Insets(INSETS_MARGIN));
        this.activePlayers.setStyle("-fx-background-color: WHITE");
        this.activePlayers.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

        this.player1.setAlignment(Pos.CENTER);
        this.player1.setPadding(new Insets(INSETS_MARGIN));
        this.player1.setStyle("-fx-background-color: WHITE");
        this.player1.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

        this.namePlayer1.setAlignment(Pos.CENTER);
        this.namePlayer1.setPadding(new Insets(INSETS_MARGIN));
        this.namePlayer1.setStyle("-fx-background-color: WHITE");
        this.namePlayer1.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

        this.colorPlayer1.setPadding(new Insets(INSETS_MARGIN));
        this.colorPlayer1.setStyle("-fx-background-color: WHITE");
        this.colorPlayer1.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

        this.player2.setAlignment(Pos.CENTER);
        this.player2.setPadding(new Insets(INSETS_MARGIN));
        this.player2.setStyle("-fx-background-color: WHITE");
        this.player2.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

        this.namePlayer2.setAlignment(Pos.CENTER);
        this.namePlayer2.setPadding(new Insets(INSETS_MARGIN));
        this.namePlayer2.setStyle("-fx-background-color: WHITE");
        this.namePlayer2.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

        this.colorPlayer2.setPadding(new Insets(INSETS_MARGIN));
        this.colorPlayer2.setStyle("-fx-background-color: WHITE");
        this.colorPlayer2.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

        this.player3.setAlignment(Pos.CENTER);
        this.player3.setPadding(new Insets(INSETS_MARGIN));
        this.player3.setStyle("-fx-background-color: WHITE");
        this.player3.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

        this.namePlayer3.setAlignment(Pos.CENTER);
        this.namePlayer3.setPadding(new Insets(INSETS_MARGIN));
        this.namePlayer3.setStyle("-fx-background-color: WHITE");
        this.namePlayer3.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

        this.colorPlayer3.setPadding(new Insets(INSETS_MARGIN));
        this.colorPlayer3.setStyle("-fx-background-color: WHITE");
        this.colorPlayer3.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

        this.player4.setAlignment(Pos.CENTER);
        this.player4.setPadding(new Insets(INSETS_MARGIN));
        this.player4.setStyle("-fx-background-color: WHITE");
        this.player4.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

        this.namePlayer4.setAlignment(Pos.CENTER);
        this.namePlayer4.setPadding(new Insets(INSETS_MARGIN));
        this.namePlayer4.setStyle("-fx-background-color: WHITE");
        this.namePlayer4.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

        this.colorPlayer4.setPadding(new Insets(INSETS_MARGIN));
        this.colorPlayer4.setStyle("-fx-background-color: WHITE");
        this.colorPlayer4.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

        this.start.setAlignment(Pos.CENTER);
        this.start.setPadding(new Insets(INSETS_MARGIN));
        this.start.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

        // plaatsing van de nodes op de grid

        this.add(titleParchisIm, 1, 0);
        this.add(activePlayers, 1, 1);
        this.add(player1, 0, 2);
        this.add(namePlayer1, 1, 2);
        this.add(colorPlayer1, 2, 2);
        this.add(player2, 0, 3);
        this.add(namePlayer2, 1, 3);
        this.add(colorPlayer2, 2, 3);
        this.add(player3, 0, 4);
        this.add(namePlayer3, 1, 4);
        this.add(colorPlayer3, 2, 4);
        this.add(player4, 0, 5);
        this.add(namePlayer4, 1, 5);
        this.add(colorPlayer4, 2, 5);
        this.add(start, 1, 6);
        this.add(confirm, 2, 1);


        // geeft ruimte tussen de kolommen en de rijen
        this.setVgap(INSETS_MARGIN);
        this.setHgap(INSETS_MARGIN);


        //zet het gehele scherm in het midden
        this.setAlignment(Pos.CENTER);

        // voegt extra marge onderaan de Confirm knop
        GridPane.setMargin(start, new Insets(0, 0, INSETS_MARGIN * 2, 0));


        //bepalen van de kolombreedtes en alignering van de kolommen
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPrefWidth(WIDTH * 0.25);
        column1.setHalignment(HPos.RIGHT);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPrefWidth(WIDTH * 0.5);
        column2.setHalignment(HPos.CENTER);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPrefWidth(WIDTH * 0.25);
        column3.setHalignment(HPos.LEFT);
        this.getColumnConstraints().addAll(column1, column2, column3);


        //bepalen van de rijhoogtes;
        RowConstraints row1 = new RowConstraints();
        row1.setPrefHeight(HEIGHT * 0.4);
        this.getRowConstraints().addAll(row1);

        //   this.setGridLinesVisible(true);
    }

    public void enablePlayers(int aantal) {
        switch (aantal) {
            case 4:
                player4.setDisable(false);
                namePlayer4.setDisable(false);
                colorPlayer4.setDisable(false);
            case 3:
                player3.setDisable(false);
                namePlayer3.setDisable(false);
                colorPlayer3.setDisable(false);
            case 2:
                player1.setDisable(false);
                namePlayer1.setDisable(false);
                colorPlayer1.setDisable(false);
                player2.setDisable(false);
                namePlayer2.setDisable(false);
                colorPlayer2.setDisable(false);

        }

    }


    public TextField getActivePlayers() {
        return activePlayers;
    }

    public TextField getNamePlayer1() {
        return namePlayer1;
    }

    public ComboBox getColorPlayer1() {
        return colorPlayer1;
    }

    public TextField getNamePlayer2() {
        return namePlayer2;
    }

    public ComboBox getColorPlayer2() {
        return colorPlayer2;
    }

    public TextField getNamePlayer3() {
        return namePlayer3;
    }

    public ComboBox getColorPlayer3() {
        return colorPlayer3;
    }

    public TextField getNamePlayer4() {
        return namePlayer4;
    }

    public ComboBox getColorPlayer4() {
        return colorPlayer4;
    }

    public Button getStart() {
        return start;
    }

    public Button getConfirm() {
        return confirm;
    }

    public Label getPlayer1() {
        return player1;
    }

    public Label getPlayer2() {
        return player2;
    }

    public Label getPlayer3() {
        return player3;
    }

    public Label getPlayer4() {
        return player4;
    }
}
