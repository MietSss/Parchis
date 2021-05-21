package Iteratie14.View.EnterPlayersScreen;


import Iteratie14.Model.*;
import Iteratie14.View.MainScreen.MainScreenPresenter;
import Iteratie14.View.MainScreen.MainScreenView;
import Iteratie14.View.UISettings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EnterPlayersPresenter {

    private ParchisModel model;
    private EnterPlayersView view;
    private UISettings uiSettings;
    private int aantalSpelers;
    Map<Integer, String> gekozenKleuren;
    Map<Integer, String> gekozenNamen;


    public EnterPlayersPresenter(ParchisModel model, EnterPlayersView view, UISettings uiSettings) {
        this.model = model;
        this.view = view;
        this.uiSettings = uiSettings;
        gekozenKleuren = new LinkedHashMap<>();
        gekozenNamen = new LinkedHashMap<>();
        this.addEventHandlers();
        this.updateView();
        this.addWindowEventHandlers();

    }

    private void addEventHandlers() {
        view.getConfirm().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                aantalSpelers = 0;
                try {
                    aantalSpelers = Integer.parseInt(view.getActivePlayers().getText());
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error aantal Spelers");
                    alert.setContentText("Dit is geen cijfer!");
                    alert.showAndWait();
                    view.getActivePlayers().clear();
                    return;
                }
                if (aantalSpelers < 2 || aantalSpelers > Spelers.MAX_SPELERS) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error aantal Spelers");
                    alert.setContentText("Dit is geen cijfer tussen 2 en 4");
                    alert.showAndWait();
                    view.getActivePlayers().clear();

                } else {
                    updateView();
                }

            }
        });

        view.getColorPlayer1().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    gekozenKleuren.put(1, view.getColorPlayer1().getValue().toString());
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error gekozen kleur");
                    alert.setContentText("Er is geen kleur gekozen voor speler1");
                    alert.showAndWait();
                }
                updateView();
            }
        });

        view.getColorPlayer2().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    gekozenKleuren.put(2, view.getColorPlayer2().getValue().toString());
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error gekozen kleur");
                    alert.setContentText("Er is geen kleur gekozen voor speler2");
                    alert.showAndWait();
                }
                updateView();
            }
        });
        view.getColorPlayer3().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    gekozenKleuren.put(3, view.getColorPlayer3().getValue().toString());
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error gekozen kleur");
                    alert.setContentText("Er is geen kleur gekozen voor speler3");
                    alert.showAndWait();
                }
                updateView();
            }
        });
        view.getColorPlayer4().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    gekozenKleuren.put(4, view.getColorPlayer4().getValue().toString());
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error gekozen kleur");
                    alert.setContentText("Er is geen kleur gekozen voor speler4");
                    alert.showAndWait();
                }
                updateView();
            }
        });


        view.getStart().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    switch (aantalSpelers) {
                        case 4:
                            if (view.getNamePlayer4().getText().isEmpty()) {
                                throw new ParchisException("Naam ontbreekt bij speler 4");
                            }
                            gekozenNamen.put(4, view.getNamePlayer4().getText());
                        case 3:
                            if (view.getNamePlayer3().getText().isEmpty()) {
                                throw new ParchisException("Naam ontbreekt bij speler 3");
                            }
                            gekozenNamen.put(3, view.getNamePlayer3().getText());
                        case 2:
                            if (view.getNamePlayer2().getText().isEmpty()) {
                                throw new ParchisException("Naam ontbreekt bij speler 2");
                            }
                            if (view.getNamePlayer1().getText().isEmpty()) {
                                throw new ParchisException("Naam ontbreekt bij speler 1");
                            }
                            gekozenNamen.put(2, view.getNamePlayer2().getText());
                            gekozenNamen.put(1, view.getNamePlayer1().getText());
                    }
                } catch (ParchisException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error gekozen naam");
                    alert.setContentText(e.getMessage() + " en misschien nog meerdere namen");
                    alert.showAndWait();
                }
                if (gekozenKleuren.size() != aantalSpelers) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error gekozen kleur");
                    alert.setContentText("Er ontbreken nog 1 of meerder kleuren");
                    alert.showAndWait();
                }
                try {
                    model.start(gekozenKleuren, gekozenNamen);
                    MainScreenView mView = new MainScreenView(uiSettings, model);
                    MainScreenPresenter mPresenter = new MainScreenPresenter(model, mView, uiSettings);
                    view.getScene().setRoot(mView);


                    mView.getScene().getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
                    mView.getScene().getWindow().sizeToScene();
                    mView.getScene().getWindow().setX(uiSettings.getResX() / 20);
                    mView.getScene().getWindow().setY(uiSettings.getResY() / 20);
                    mView.getScene().getWindow().setHeight(9 * uiSettings.getResY() / 10);
                    mView.getScene().getWindow().setWidth(9 * uiSettings.getResX() / 10);
                    mPresenter.addWindowEventHandlers();
                } catch (MalformedURLException | NullPointerException e) {
                    e.printStackTrace();
                }
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


    private void updateView() {
        model.getSpelers().setAantalSpelers(aantalSpelers);
        view.enablePlayers(aantalSpelers);
        ObservableList<String> availableColors = FXCollections.observableArrayList(getBeschikbareKleuren());
        switch (aantalSpelers) {
            case 4:
                if (view.getColorPlayer4().getValue() == null) {
                    view.getColorPlayer4().setItems(availableColors);
                }
            case 3:
                if (view.getColorPlayer3().getValue() == null) {
                    view.getColorPlayer3().setItems(availableColors);
                }
            case 2:
                if (view.getColorPlayer2().getValue() == null) {
                    view.getColorPlayer2().setItems(availableColors);
                }
                if (view.getColorPlayer1().getValue() == null) {
                    view.getColorPlayer1().setItems(availableColors);
                }

        }

    }

    private void addWindowEventHandlers(){

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


    public List<String> getBeschikbareKleuren() {
        List<String> beschikbareKleuren = new ArrayList<>();
        for (Kleur kleur : model.getKleuren()) {
            if (isKleurBeschikbaar((kleur.name()))) {
                beschikbareKleuren.add(kleur.name());
            }
        }
        return beschikbareKleuren;
    }

    public boolean isKleurBeschikbaar(String kleur) {
        //kleur is beschikbaar als ze niet voorkomt in colorPlayer
        for (Integer nrSpeler : gekozenKleuren.keySet()) {
            if (gekozenKleuren.get(nrSpeler).equals(kleur)) {
                return false;
            }
        }
        return true;
    }
}



