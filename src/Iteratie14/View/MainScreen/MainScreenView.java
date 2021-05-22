package Iteratie14.View.MainScreen;

import Iteratie14.Model.*;
import Iteratie14.UISettings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class MainScreenView extends BorderPane {

    private MenuItem exitMI;
    private MenuItem saveMI;
    private MenuItem loadMI;
    private MenuItem settingsMI;
    private MenuItem aboutMI;
    private MenuItem infoMI;
    private MenuItem cheatMI;
    private MenuItem highscoresMI;
    private final UISettings uiSettings;
    private final GameBoard gameBoardView;
    private TextArea textInstructies;
    private ImageView dobbelsteen6;
    private ImageView dobbelsteen5;
    private ImageView dobbelsteen4;
    private ImageView dobbelsteen3;
    private ImageView dobbelsteen2;
    private ImageView dobbelsteen1;
    private ImageView dobbelsteen;
    private Label titel;
    private VBox vBox;
    //private Button dobbelsteen;
    private List<ImageView> dobbelstenen;
    private Button werp;
    private double WIDTH;
    private double HEIGHT;
    // private Button nextInstruction;

    public MainScreenView(UISettings uiSettings, ParchisModel model) {
        this.uiSettings = uiSettings;
        gameBoardView = new GameBoard(uiSettings, model);
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        this.exitMI = new MenuItem("Exit");
        this.saveMI = new MenuItem("Save");
        this.loadMI = new MenuItem("Load");
        this.settingsMI = new MenuItem("Settings");
        this.aboutMI = new MenuItem("About");
        this.infoMI = new MenuItem("Info");
        this.cheatMI = new MenuItem("Cheat the game");
        this.highscoresMI = new MenuItem("HighScores");
        textInstructies = new TextArea();
        dobbelstenen = new ArrayList<>();
        werp = new Button("Werp Dobbelsteen");
        WIDTH =9 * uiSettings.getResX() / 10;
        HEIGHT =9 * uiSettings.getResY() / 10;


        titel = new Label("Instructies:");
        if (Files.exists(uiSettings.getDobbelsteen())) {
            try {
                dobbelsteen = new ImageView(new Image(uiSettings.getDobbelsteen().toUri().toURL().toString()));
                dobbelsteen.setFitHeight(200);
                dobbelsteen.setFitWidth(200);
                //   dobbelstenenImages.add(dobbelsteen);

            } catch (MalformedURLException e) {

            }
        }

        if (Files.exists(uiSettings.getDobbelsteen6())) {
            try {
                dobbelsteen6 = new ImageView(new Image(uiSettings.getDobbelsteen6().toUri().toURL().toString()));
                dobbelsteen6.setFitHeight(100);
                dobbelsteen6.setFitWidth(100);
                dobbelstenen.add(dobbelsteen6);

            } catch (MalformedURLException e) {

            }
            if (Files.exists(uiSettings.getDobbelsteen5())) {
                try {
                    dobbelsteen5 = new ImageView(new Image(uiSettings.getDobbelsteen5().toUri().toURL().toString()));
                    dobbelsteen5.setFitHeight(100);
                    dobbelsteen5.setFitWidth(100);
                    dobbelstenen.add(dobbelsteen5);

                } catch (MalformedURLException e) {

                }
            }
            if (Files.exists(uiSettings.getDobbelsteen4())) {
                try {
                    dobbelsteen4 = new ImageView(new Image(uiSettings.getDobbelsteen4().toUri().toURL().toString()));
                    dobbelsteen4.setFitHeight(100);
                    dobbelsteen4.setFitWidth(100);
                    dobbelstenen.add(dobbelsteen4);

                } catch (MalformedURLException e) {

                }
            }
            if (Files.exists(uiSettings.getDobbelsteen3())) {
                try {
                    dobbelsteen3 = new ImageView(new Image(uiSettings.getDobbelsteen3().toUri().toURL().toString()));
                    dobbelsteen3.setFitHeight(100);
                    dobbelsteen3.setFitWidth(100);
                    dobbelstenen.add(dobbelsteen3);

                } catch (MalformedURLException e) {

                }
            }
            if (Files.exists(uiSettings.getDobbelsteen2())) {
                try {
                    dobbelsteen2 = new ImageView(new Image(uiSettings.getDobbelsteen2().toUri().toURL().toString()));
                    dobbelsteen2.setFitHeight(100);
                    dobbelsteen2.setFitWidth(100);
                    dobbelstenen.add(dobbelsteen2);

                } catch (MalformedURLException e) {

                }
            }
            if (Files.exists(uiSettings.getDobbelsteen1())) {
                try {
                    dobbelsteen1 = new ImageView(new Image(uiSettings.getDobbelsteen1().toUri().toURL().toString()));
                    //     dobbelsteen1.setFitHeight(100);
                    //     dobbelsteen1.setFitWidth(100);
                    dobbelstenen.add(dobbelsteen1);

                } catch (MalformedURLException e) {

                }
            }
        }
        vBox = new VBox();

    }


    private void layoutNodes() {
        Menu menuFile = new Menu("File", null, loadMI, saveMI, new SeparatorMenuItem(), settingsMI, new SeparatorMenuItem(), exitMI);
        Menu menuHelp = new Menu("Help", null, aboutMI, infoMI);
        Menu menuCheat = new Menu("HighScores", null, highscoresMI, cheatMI);
        MenuBar menuBar = new MenuBar(menuFile, menuHelp, menuCheat);
        setTop(menuBar);


        titel.setFont(Font.font(50));
        textInstructies.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        //   textInstructies.setMaxHeight(200);
        textInstructies.setMaxWidth(WIDTH * 0.2);
        textInstructies.setMaxHeight(HEIGHT * 0.3);
        textInstructies.setEditable(false);
        textInstructies.setWrapText(true);
        //   dobbelsteen.setMaxSize(100,100);
        werp.setMaxHeight(HEIGHT * 0.1);
        //   werp.setMaxWidth(200);
        //   setMaxHeight(200);


        vBox.setSpacing(20);
        vBox.setPadding(new Insets(10));
        VBox.setVgrow(titel, Priority.ALWAYS);
        VBox.setVgrow(textInstructies, Priority.ALWAYS);
        VBox.setVgrow(werp, Priority.ALWAYS);
        VBox.setVgrow(dobbelsteen, Priority.ALWAYS);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(titel, textInstructies, werp, dobbelsteen);

        //   VBox.setMargin(textInstructies, new Insets(10,10,10,10));

        //  VBox.setMargin(dobbelsteen, new Insets(10,10,10,10));
        //  BorderPane.setMargin(vBox, new Insets(10, 10, 10, 10));

        this.setCenter(gameBoardView);
        this.setRight(vBox);
        //  this.setPadding(new Insets(10));

    }

    public List<ImageView> getDobbelstenen() {
        return dobbelstenen;
    }

    MenuItem getExitItem() {
        return exitMI;
    }

    MenuItem getSaveItem() {
        return saveMI;
    }

    MenuItem getLoadItem() {
        return loadMI;
    }

    MenuItem getSettingsItem() {
        return settingsMI;
    }

    MenuItem getAboutItem() {
        return aboutMI;
    }

    MenuItem getInfoItem() {
        return infoMI;
    }

    public GameBoard getGameBoardView() {
        return gameBoardView;
    }

    public TextArea getTextInstructies() {
        return textInstructies;
    }

    public ImageView getDobbelsteen() {
        return dobbelsteen;
    }

    public void setDobbelsteen(ImageView dobbelsteen) {
        this.dobbelsteen = dobbelsteen;
    }

    public Button getWerp() {
        return werp;
    }


    public MenuItem getHighscoresMI() {
        return highscoresMI;
    }

    public MenuItem getCheatMI() {

        return cheatMI;
    }
}
