package Iteratie14.View.MainScreen;

import Iteratie14.Model.*;
import Iteratie14.View.AboutScreen.*;
import Iteratie14.View.EndOfGame.EndOfGamePresenter;
import Iteratie14.View.EndOfGame.EndOfGameView;
import Iteratie14.View.HighscoresScreen.HighScorePresenter;
import Iteratie14.View.HighscoresScreen.HighScoreView;
import Iteratie14.View.InfoScreen.*;
import Iteratie14.View.SettingsScreen.*;
import Iteratie14.View.UISettings;

import javafx.event.*;
import javafx.geometry.HPos;

import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;

import javafx.scene.layout.GridPane;

import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Formatter;
import java.util.List;

public class MainScreenPresenter {

    private ParchisModel model;
    private MainScreenView view;
    private UISettings uiSettings;
    private GameBoard gameBoard;
    private AnimatieDobbelsteen animatie;


    public MainScreenPresenter(ParchisModel model, MainScreenView view, UISettings uiSettings) {
        this.model = model;
        this.view = view;
        this.uiSettings = uiSettings;
        gameBoard = view.getGameBoardView();
        animatie = new AnimatieDobbelsteen(model, view);
        updateInstructies();
        EventHandlers();
        animatie.animateSpelerAanZet();
    }

    private void updateInstructies() {
        //instructies updaten
        view.getTextInstructies().setText(model.getInstruction());


    }


    private void verplaatsPion(Button pionButton, Pion pion) {
        //verwijder node op oude plaats
        view.getGameBoardView().getChildren().remove(pionButton);
        //voeg node toe aan nieuwe plaats obv x, y
        int x = pion.getVak().getX();
        int y = pion.getVak().getY();
        view.getGameBoardView().add(pionButton, x, y);
        //staan er buiten je pion nog pionnen op vak?
        if (model.getSpelers().getPionnenOpVak(pion.getVakNummer()).size() == 2) {
            GridPane.setHalignment(pionButton, HPos.LEFT);
            pionButton.setMaxSize(view.getGameBoardView().getVakBreedte() / 2, view.getGameBoardView().getVakHoogte());
            //herschaal ook andere button
            for (Button button : view.getGameBoardView().getPionnenOpVak(x, y)) {
                if (!button.equals(pionButton)) {
                    GridPane.setHalignment(button, HPos.RIGHT);
                    button.setMaxSize(view.getGameBoardView().getVakBreedte() / 2, view.getGameBoardView().getVakHoogte());
                }
            }
        }
        if (model.getSpelers().getPionnenOpVak(pion.getVakNummer()).size() == 3) {
            GridPane.setHalignment(pionButton, HPos.RIGHT);
        }
        //nadat je pion verplaatst hebt, moeten de buttons terug gedisabled worden.
        List<Button> pionnen = view.getGameBoardView().getPionnen(model.getSpelerAanZet().getKleur());
        for (Button button : pionnen) {
            button.setDisable(true);
        }
        if (model.isEindeSpel()) {

        } else {
            model.kiesVolgendeSpeler();
            //zet werp button terug aan.
            view.getWerp().setDisable(false);
            updateInstructies();
            animatie.animateSpelerAanZet();
        }
    }


    private void updateDobbelsteen() {
        view.getWerp().setDisable(true);
        animatie.start();
    }


    private void EventHandlers() {
        //als je op dobbelsteen klikt het is voor de eerste beurt te bepalen
        //instructie staat eerste keer reeds op scherm
        //dobbelsteen klik moet waarde teruggeven van worp eerste speler + instructie voor 2de speler
        //daarna worp 2de speler + instructie voor 3de speler...
        view.getWerp().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (model.isGooiVoorEersteBeurt()) {
                    //we krijgen een worp en instructie:
                    model.speelVoorEersteBeurt(model.getSpelerAanZet());
                    //update scherm, door dobbelsteen te laten rollen, daarna
                    //instructie updaten
                    //speler aan zet op scherm zichtbaar maken
                    //dobbelsteen speler updaten
                    updateDobbelsteen();

                } else {
                    //gooi en krijg opties te zien van pionnen die je kan verzetten
                    try {
                        model.beurt(model.getSpelerAanZet());
                    }catch(ParchisException e){
                        //we moeten hier nog niets mee doen.
                    }
                    updateDobbelsteen();
                    //indien geen normaal spel: voer verplichte zet uit:
                    if(!model.isNormaalSpel()){
                        //toon wat er moet gebeuren
                        updateInstructies();
                        Pion teVerzettenPion=model.getUitzonderteVerzettenPion();
                        Kleur kleurPionTeVerzetten=teVerzettenPion.getKleur();
                        Button pionButtonTeVerzetten=view.getGameBoardView().getPionButton(kleurPionTeVerzetten,teVerzettenPion.getPionNummer());
                        model.verzetPion(teVerzettenPion);
                        //verplaats pion
                        verplaatsPion(pionButtonTeVerzetten,teVerzettenPion);
                        //iets op te eten?
                        if (model.ietsOpTeEten(teVerzettenPion.getVakNummer())){
                            for (Pion pion1 : model.getSpelers().getPionnenOpVak(teVerzettenPion.getVakNummer())) {
                                if (pion1.getKleur() != teVerzettenPion.getKleur()) {
                                    model.eetPion(pion1, teVerzettenPion);
                                    Button buttonPionOpTeEten=view.getGameBoardView().getPionButton(pion1.getKleur(),pion1.getPionNummer());
                                    verplaatsPion(buttonPionOpTeEten,pion1);
                                    break;
                                }
                            }
                        }

                    }

                }

            }
        });
        //pionnenGeel
        for (int i = 0; i < view.getGameBoardView().getPionnenGeel().size(); i++) {

            int finalI = i;
            Button pionGeelButton = view.getGameBoardView().getPionnenGeel().get(i);
            pionGeelButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    int pionNummer = finalI + 1;
                    Pion pionGeel = model.getSpelers().getSpeler(Kleur.GEEL).getPion(pionNummer);
                    //voert zet uit in model logica
                    try {
                        model.verzetPion(pionGeel);
                        updateInstructies();
                        //update scherm obv model logica
                        verplaatsPion(pionGeelButton, pionGeel);
                    } catch (ParchisException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Pion kan niet verplaatst worden");
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    }
                }
            });

        }
        //pionnenGroen
        for (int i = 0; i < view.getGameBoardView().getPionnenGroen().size(); i++) {

            int finalI = i;
            Button pionGroenButton = view.getGameBoardView().getPionnenGroen().get(i);
            pionGroenButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    int pionNummer = finalI + 1;
                    Pion pionGroen = model.getSpelers().getSpeler(Kleur.GROEN).getPion(pionNummer);
                    try {
                        model.verzetPion(pionGroen);
                        updateInstructies();
                        verplaatsPion(pionGroenButton, pionGroen);
                    }catch(ParchisException e){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Pion kan niet verplaatst worden");
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    }

                }
            });

        }
        //pionnenRood
        for (int i = 0; i < view.getGameBoardView().getPionnenRood().size(); i++) {

            int finalI = i;
            Button pionRoodButton = view.getGameBoardView().getPionnenRood().get(i);
            pionRoodButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    int pionNummer = finalI + 1;
                    Pion pionRood = model.getSpelers().getSpeler(Kleur.ROOD).getPion(pionNummer);
                    try {
                        model.verzetPion(pionRood);
                        updateInstructies();
                        verplaatsPion(pionRoodButton, pionRood);
                    }catch(ParchisException e){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Pion kan niet verplaatst worden");
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    }
                }
            });

        }

        //pionnenBlauw
        for (int i = 0; i < view.getGameBoardView().getPionnenBlauw().size(); i++) {

            int finalI = i;
            Button pionBlauwButton = view.getGameBoardView().getPionnenBlauw().get(i);
            pionBlauwButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    int pionNummer = finalI + 1;
                    Pion pionBlauw = model.getSpelers().getSpeler(Kleur.BLAUW).getPion(pionNummer);
                    try {
                        model.verzetPion(pionBlauw);
                        updateInstructies();
                        verplaatsPion(pionBlauwButton, pionBlauw);
                    }catch(ParchisException e){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Pion kan niet verplaatst worden");
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    }

                }
            });

        }
        view.getCheatMI().
                setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        //openen van het scherm met proficiat
                        EndOfGameView endOfGameView = new EndOfGameView(uiSettings);
                        EndOfGamePresenter endOfGamePresenter = new EndOfGamePresenter(model, endOfGameView, uiSettings);
                        Stage endOfGameStage = new Stage();
                        endOfGameStage.initOwner(view.getScene().getWindow());
                        endOfGameStage.initModality(Modality.APPLICATION_MODAL);
                        Scene scene = new Scene(endOfGameView);
                        endOfGameStage.setScene(scene);
                        endOfGameStage.setTitle(uiSettings.getApplicationName() + " - End Of Game");
                        endOfGameStage.setX(view.getScene().getWindow().getX() + uiSettings.getResX() / 10);
                        endOfGameStage.setY(view.getScene().getWindow().getY() + uiSettings.getResY() / 10);
                        endOfGameView.getScene().getWindow().setHeight(uiSettings.getResY() / 2);
                        endOfGameView.getScene().getWindow().setWidth(uiSettings.getResX() / 2);
                        if (uiSettings.styleSheetAvailable()) {
                            endOfGameView.getScene().getStylesheets().removeAll();
                            try {
                                endOfGameView.getScene().getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
                            } catch (MalformedURLException ex) {
                                // do nothing, if toURL-conversion fails, program can continue
                            }
                        }
                        endOfGameStage.showAndWait();
                    }

                });

        view.getHighscoresMI().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                HighScoreView highScoreView=new HighScoreView(uiSettings);
                HighScorePresenter highScorePresenter=new HighScorePresenter(model,highScoreView,uiSettings);
                Stage highscoreStage=new Stage();
                highscoreStage.setTitle("Highscores");
                highscoreStage.initOwner(view.getScene().getWindow());
                highscoreStage.initModality(Modality.APPLICATION_MODAL);
                Scene scene = new Scene(highScoreView);
                highscoreStage.setScene(scene);
                highscoreStage.setTitle(uiSettings.getApplicationName() + " - Settings");
                highscoreStage.setX(view.getScene().getWindow().getX() + uiSettings.getResX() / 10);
                highscoreStage.setY(view.getScene().getWindow().getY() + uiSettings.getResY() / 10);
                if (Files.exists(uiSettings.getApplicationIconPath())) {
                    try {
                        highscoreStage.getIcons().add(new Image(uiSettings.getApplicationIconPath().toUri().toURL().toString()));
                    } catch (MalformedURLException ex) {
                        // do nothing, if toURL-conversion fails, program can continue
                    }
                } else { // do nothing, if ApplicationIconImage is not available, program can continue
                }
                highScoreView.getScene().getWindow().setHeight(7 * uiSettings.getResY() / 10);
                highScoreView.getScene().getWindow().setWidth(7 * uiSettings.getResX() / 10);
                if (uiSettings.styleSheetAvailable()) {
                    highscoreStage.getScene().getStylesheets().removeAll();
                    try {
                        highscoreStage.getScene().getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
                    } catch (MalformedURLException ex) {
                        // do nothing, if toURL-conversion fails, program can continue
                    }
                }
                highscoreStage.showAndWait();

            }

        });

        view.getSettingsItem().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SettingsView settingsView = new SettingsView(uiSettings);
                SettingsPresenter presenter = new SettingsPresenter(model, settingsView, uiSettings);
                Stage settingsStage = new Stage();
                settingsStage.setTitle("Settings");
                settingsStage.initOwner(view.getScene().getWindow());
                settingsStage.initModality(Modality.APPLICATION_MODAL);
                Scene scene = new Scene(settingsView);
                settingsStage.setScene(scene);
                settingsStage.setTitle(uiSettings.getApplicationName() + " - Settings");
                settingsStage.setX(view.getScene().getWindow().getX() + uiSettings.getResX() / 10);
                settingsStage.setY(view.getScene().getWindow().getY() + uiSettings.getResY() / 10);
                if (Files.exists(uiSettings.getApplicationIconPath())) {
                    try {
                        settingsStage.getIcons().add(new Image(uiSettings.getApplicationIconPath().toUri().toURL().toString()));
                    } catch (MalformedURLException ex) {
                        // do nothing, if toURL-conversion fails, program can continue
                    }
                } else { // do nothing, if ApplicationIconImage is not available, program can continue
                }
                settingsView.getScene().getWindow().setHeight(7 * uiSettings.getResY() / 10);
                settingsView.getScene().getWindow().setWidth(7 * uiSettings.getResX() / 10);
                if (uiSettings.styleSheetAvailable()) {
                    settingsStage.getScene().getStylesheets().removeAll();
                    try {
                        settingsStage.getScene().getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
                    } catch (MalformedURLException ex) {
                        // do nothing, if toURL-conversion fails, program can continue
                    }
                }
                settingsStage.showAndWait();
                if (uiSettings.styleSheetAvailable()) {
                    view.getScene().getStylesheets().removeAll();
                    try {
                        view.getScene().getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
                    } catch (MalformedURLException ex) {
                        // do nothing, if toURL-conversion fails, program can continue
                    }
                }
            }
        });
        view.getLoadItem().

                setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Load Data File");
                        fileChooser.getExtensionFilters().addAll(
                                new FileChooser.ExtensionFilter("Textfiles", "*.txt"),
                                new FileChooser.ExtensionFilter("All Files", "*.*"));
                        File selectedFile = fileChooser.showOpenDialog(view.getScene().getWindow());
                        if ((selectedFile != null)) { //^ (Files.isReadable(Paths.get(selectedFile.toURI())))) {
                            try {
                                List<String> input = Files.readAllLines(Paths.get(selectedFile.toURI()));
                                // begin implementeren ingelezen gegevens doorgeven aan model
                                for (int i = 0; i < input.size(); i++) {
                                    String inputline = input.get(i);
                                    String[] elementen = inputline.split(" ");
                                }
                                // einde implementeren ingelezen gegevens doorgeven aan model
                            } catch (IOException e) {
                                //
                            }
                        } else {
                            Alert errorWindow = new Alert(Alert.AlertType.ERROR);
                            errorWindow.setHeaderText("Problem with the selected input file:");
                            errorWindow.setContentText("File is not readable");
                            errorWindow.showAndWait();
                        }
                    }
                });
        view.getSaveItem().

                setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Save Data File");
                        fileChooser.getExtensionFilters().addAll(
                                new FileChooser.ExtensionFilter("Textfiles", "*.txt"),
                                new FileChooser.ExtensionFilter("All Files", "*.*"));
                        File selectedFile = fileChooser.showSaveDialog(view.getScene().getWindow());
                        if ((selectedFile != null) ^ (Files.isWritable(Paths.get(selectedFile.toURI())))) {
                            try {
                                Files.deleteIfExists(Paths.get(selectedFile.toURI()));
                            } catch (IOException e) {
                                //
                            }
                            try (Formatter output = new Formatter(selectedFile)) {
                                // Begin implementeren wegschrijven model-gegevens
                                output.format("%s%n", "Here comes the data!");
                                output.format("%s%n", "First record");
                                output.format("%s%n", "...");
                                output.format("%s%n", "Last record");
                                // Einde implementeren wegschrijven model-gegevens
                            } catch (IOException e) {
                                //
                            }
                        } else {
                            Alert errorWindow = new Alert(Alert.AlertType.ERROR);
                            errorWindow.setHeaderText("Problem with the selected output file:");
                            errorWindow.setContentText("File is not writable");
                            errorWindow.showAndWait();
                        }
                    }
                });
        view.getExitItem().

                setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        handleCloseEvent(event);
                    }
                });
        view.getAboutItem().

                setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        AboutScreenView aboutScreenView = new AboutScreenView(uiSettings);
                        AboutScreenPresenter aboutScreenPresenter = new AboutScreenPresenter(model, aboutScreenView, uiSettings);
                        Stage aboutScreenStage = new Stage();
                        aboutScreenStage.initOwner(view.getScene().getWindow());
                        aboutScreenStage.initModality(Modality.APPLICATION_MODAL);
                        Scene scene = new Scene(aboutScreenView);
                        aboutScreenStage.setScene(scene);
                        aboutScreenStage.setTitle(uiSettings.getApplicationName() + " - About");
                        aboutScreenStage.setX(view.getScene().getWindow().getX() + uiSettings.getResX() / 10);
                        aboutScreenStage.setY(view.getScene().getWindow().getY() + uiSettings.getResY() / 10);
                        if (Files.exists(uiSettings.getApplicationIconPath())) {
                            try {
                                aboutScreenStage.getIcons().add(new Image(uiSettings.getApplicationIconPath().toUri().toURL().toString()));
                            } catch (MalformedURLException ex) {
                                // do nothing, if toURL-conversion fails, program can continue
                            }
                        } else { // do nothing, if ApplicationIconImage is not available, program can continue
                        }
                        aboutScreenView.getScene().getWindow().setHeight(uiSettings.getResY() / 2);
                        aboutScreenView.getScene().getWindow().setWidth(uiSettings.getResX() / 2);
                        if (uiSettings.styleSheetAvailable()) {
                            aboutScreenView.getScene().getStylesheets().removeAll();
                            try {
                                aboutScreenView.getScene().getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
                            } catch (MalformedURLException ex) {
                                // do nothing, if toURL-conversion fails, program can continue
                            }
                        }
                        aboutScreenStage.showAndWait();
                    }
                });
        view.getInfoItem().

                setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        InfoScreenView infoScreenView = new InfoScreenView(uiSettings);
                        InfoScreenPresenter infoScreenPresenter = new InfoScreenPresenter(model, infoScreenView, uiSettings);
                        Stage infoScreenStage = new Stage();
                        infoScreenStage.initOwner(view.getScene().getWindow());
                        infoScreenStage.initModality(Modality.APPLICATION_MODAL);
                        Scene scene = new Scene(infoScreenView);
                        infoScreenStage.setScene(scene);
                        infoScreenStage.setTitle(uiSettings.getApplicationName() + " - Info");
                        infoScreenStage.setX(view.getScene().getWindow().getX() + uiSettings.getResX() / 10);
                        infoScreenStage.setY(view.getScene().getWindow().getY() + uiSettings.getResY() / 10);
                        if (Files.exists(uiSettings.getApplicationIconPath())) {
                            try {
                                infoScreenStage.getIcons().add(new Image(uiSettings.getApplicationIconPath().toUri().toURL().toString()));
                            } catch (MalformedURLException ex) {
                                // do nothing, if toURL-conversion fails, program can continue
                            }
                        } else { // do nothing, if ApplicationIconImage is not available, program can continue
                        }
                        infoScreenView.getScene().getWindow().setHeight(uiSettings.getResY() / 2);
                        infoScreenView.getScene().getWindow().setWidth(uiSettings.getResX() / 2);
                        if (uiSettings.styleSheetAvailable()) {
                            infoScreenView.getScene().getStylesheets().removeAll();
                            try {
                                infoScreenView.getScene().getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
                            } catch (MalformedURLException ex) {
                                // do nothing, if toURL-conversion fails, program can continue
                            }
                        }
                        infoScreenStage.showAndWait();
                    }
                });

    }

    public void windowsHandler() {
        view.getScene().getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                handleCloseEvent(event);
            }
        });
    }

    private void handleCloseEvent(Event event) {
        final Alert stopWindow = new Alert(Alert.AlertType.CONFIRMATION);
        stopWindow.setHeaderText("You're closing the application.");
        stopWindow.setContentText("Are you sure? Unsaved data may be lost.");
        stopWindow.setTitle("WARNING!");
        stopWindow.getButtonTypes().clear();
        ButtonType noButton = new ButtonType("No");
        ButtonType yesButton = new ButtonType("Yes");
        stopWindow.getButtonTypes().addAll(yesButton, noButton);
        stopWindow.showAndWait();
        if (stopWindow.getResult() == null || stopWindow.getResult().equals(noButton)) {
            event.consume();
        } else {
            view.getScene().getWindow().hide();
        }
    }
}
