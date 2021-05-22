package Iteratie14;

import javafx.stage.Screen;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UISettings {

    private int resX;
    private int resY;
    private int insetsMargin;
    public static final char FILE_SEPARATOR = System.getProperties().getProperty("file.separator").charAt(0);
    private String ApplicationName;
    private String homeDir;
    private String defaultCss = "themes02.css";
    private Path styleSheetPath = Paths.get("resources"+FILE_SEPARATOR+"stylesheets"+FILE_SEPARATOR+"themes.css");
    private Path AboutImagePath = Paths.get("resources"+FILE_SEPARATOR+"images"+FILE_SEPARATOR+"AboutImage.png");
    private Path applicationIconPath = Paths.get("resources"+FILE_SEPARATOR+"images"+FILE_SEPARATOR+"ApplicationIcon.png");
    private Path startScreenImagePath = Paths.get("resources"+FILE_SEPARATOR+"images"+FILE_SEPARATOR+"StartScreenImage.png");
    private Path infoTextPath = Paths.get("resources"+FILE_SEPARATOR+"other"+FILE_SEPARATOR+"info.txt");
    private Path highscoreTextPath = Paths.get("resources"+FILE_SEPARATOR+"other"+FILE_SEPARATOR+"highscores.txt");
    private Path titleParchis = Paths.get("resources"+FILE_SEPARATOR+"images"+ FILE_SEPARATOR +"ParchisWBG.png");
    private Path pionGeelPath = Paths.get("resources"+FILE_SEPARATOR+"images"+ FILE_SEPARATOR +"PionGeel.png");
    private Path pionGroenPath = Paths.get("resources"+FILE_SEPARATOR+"images"+ FILE_SEPARATOR +"PionGroen.png");
    private Path pionRoodPah = Paths.get("resources"+FILE_SEPARATOR+"images"+ FILE_SEPARATOR +"PionRood.png");
    private Path pionBlauwPath= Paths.get("resources"+FILE_SEPARATOR+"images"+ FILE_SEPARATOR +"PionBlauw.png");
    private Path dobbelsteen6= Paths.get("resources"+FILE_SEPARATOR+"images"+ FILE_SEPARATOR +"die6.png");
    private Path dobbelsteen5= Paths.get("resources"+FILE_SEPARATOR+"images"+ FILE_SEPARATOR +"die5.png");
    private Path dobbelsteen4= Paths.get("resources"+FILE_SEPARATOR+"images"+ FILE_SEPARATOR +"die4.png");
    private Path dobbelsteen3= Paths.get("resources"+FILE_SEPARATOR+"images"+ FILE_SEPARATOR +"die3.png");
    private Path dobbelsteen2= Paths.get("resources"+FILE_SEPARATOR+"images"+ FILE_SEPARATOR +"die2.png");
    private Path dobbelsteen1= Paths.get("resources"+FILE_SEPARATOR+"images"+ FILE_SEPARATOR +"die1.png");
    private Path dobbelsteen= Paths.get("resources"+FILE_SEPARATOR+"images"+ FILE_SEPARATOR +"Dobbelsteen.png");
    private Path confettiPath = Paths.get("resources"+FILE_SEPARATOR+"images"+ FILE_SEPARATOR +"confetti.gif");




    public UISettings() {
        this.resX= (int) Screen.getPrimary().getVisualBounds().getWidth();
        this.resY = (int) Screen.getPrimary().getVisualBounds().getHeight();
        this.insetsMargin = this.getLowestRes()/100;
        this.homeDir = System.getProperties().getProperty("user.dir");
        this.ApplicationName = "Parchis";
    };

    public int getResX () {return this.resX;}

    public int getResY () {return this.resY;}

    public int getInsetsMargin () {return this.insetsMargin;}

    public int getLowestRes () {return (resX>resY?resX:resY);}

    public boolean styleSheetAvailable (){return Files.exists(styleSheetPath);}

    public Path getStyleSheetPath () {return this.styleSheetPath;}

    public void setStyleSheetPath (Path styleSheetPath) {this.styleSheetPath = styleSheetPath;}

    public String getHomeDir () {return this.homeDir;}

    public Path getApplicationIconPath () {return this.applicationIconPath;}

    public Path getStartScreenImagePath () {return this.startScreenImagePath;}

    public Path getAboutImagePath () {return this.AboutImagePath;}

    public Path getInfoTextPath () {return this.infoTextPath;}

    public String getApplicationName () {return this.ApplicationName;}

    public Path getTitleParchis() { return titleParchis; }

    public Path getPionGeelPath() {
        return pionGeelPath;
    }

    public Path getPionGroenPath() {
        return pionGroenPath;
    }

    public Path getPionRoodPah() {
        return pionRoodPah;
    }

    public Path getPionBlauwPath() {
        return pionBlauwPath;
    }

    public Path getDobbelsteen6() {
        return dobbelsteen6;
    }

    public Path getDobbelsteen5() {
        return dobbelsteen5;
    }

    public Path getDobbelsteen4() {
        return dobbelsteen4;
    }

    public Path getDobbelsteen3() {
        return dobbelsteen3;
    }

    public Path getDobbelsteen2() {
        return dobbelsteen2;
    }

    public Path getDobbelsteen1() {
        return dobbelsteen1;
    }

    public Path getDobbelsteen() {
        return dobbelsteen;
    }

    public Path getConfettiPath() {
        return confettiPath;
    }

    public Path getHighscoreTextPath() {
        return highscoreTextPath;
    }
}
