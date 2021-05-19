package Iteratie14.View.MainScreen;


import Iteratie14.Model.*;
import Iteratie14.View.UISettings;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


import java.net.MalformedURLException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class GameBoard extends GridPane {


    private final double vakBreedte;
    private final double vakHoogte;
    private Rectangle vak;
    private Circle nestGroen;
    private Circle nestRood;
    private Circle nestBlauw;
    private Circle nestGeel;
    private Ellipse safePoint;
  //  private static final int CANVAS_HOOGTE = 850;
  //  private static final int CANVAS_BREEDTE = 1000;
    private final double CANVAS_HOOGTE;
    private final double CANVAS_BREEDTE;

    private static final Color rood = Color.rgb(250, 0, 0, 0.3);
    private static final Color blauw = Color.rgb(0, 0, 180, 0.3);
    private static final Color groen = Color.rgb(0, 100, 0, 0.3);
    private static final Color geel = Color.rgb(250, 180, 0, 0.3);
    private Button pionRood;
    private Button pionGeel;
    private Button pionGroen;
    private Button pionBlauw;
    private ImageView pionRoodImageView;
    private ImageView pionGeelImageView;
    private ImageView pionGroenImageView;
    private ImageView pionBlauwImageView;
    private Speelbord speelbord;
    private Label naamSpeler;
    private Map<Kleur, Label> labelsNaamSpeler;
    private final UISettings uiSettings;
    private List<Kleur> kleurenSpelers;
    private List<String> namenSpelers;
    private List<Button> pionnenGroen;
    private List<Button> pionnenGeel;
    private List<Button> pionnenBlauw;
    private List<Button> pionnenRood;
    private ImageView gegooideWaardeSpelerBl;
    private ImageView gegooideWaardeSpelerGe;
    private ImageView gegooideWaardeSpelerRo;
    private ImageView gegooideWaardeSpelerGr;
    private Label aantalBeurtenRood, aantalBeurtenGeel, aantalBeurtenGroen, aantalBeurtenBlauw;



    public GameBoard(UISettings uiSettings, ParchisModel model) {
        this.speelbord = new Speelbord();
        CANVAS_HOOGTE=(9 * uiSettings.getResY() / 8)*0.7;
        CANVAS_BREEDTE=(9 * uiSettings.getResX() / 10)*0.7;
        this.vakBreedte = CANVAS_BREEDTE / Speelbord.AANTAL_KOLOMMEN;
        this.vakHoogte = CANVAS_HOOGTE / Speelbord.AANTAL_RIJEN;
        this.uiSettings = uiSettings;
        pionnenBlauw = new ArrayList<>();
        pionnenGeel = new ArrayList<>();
        pionnenRood = new ArrayList<>();
        pionnenGroen = new ArrayList<>();
        labelsNaamSpeler = new LinkedHashMap<>();
        try {
            kleurenSpelers = model.getSpelers().getKleurenSpelers();
            namenSpelers = model.getSpelers().getNamenSpelers();
        } catch (ParchisException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error attributen spelers");
            alert.setContentText("Er zijn geen namen of kleuren van spelers beschikbaar");
            alert.showAndWait();
        }

            this.initialiseNodes();
            this.layoutNodes();
        }


        private void initialiseNodes () {
            nestBlauw = new Circle((vakBreedte * 4) * 0.5, Color.TRANSPARENT);
            nestGroen = new Circle((vakBreedte * 4) * 0.5, Color.TRANSPARENT);
            nestGeel = new Circle((vakBreedte * 4) * 0.5, Color.TRANSPARENT);
            nestRood = new Circle((vakBreedte * 4) * 0.5, Color.TRANSPARENT);

            gegooideWaardeSpelerBl = new ImageView();
            gegooideWaardeSpelerGe = new ImageView();
            gegooideWaardeSpelerRo = new ImageView();
            gegooideWaardeSpelerGr = new ImageView();

            aantalBeurtenBlauw = new Label();
            aantalBeurtenGeel = new Label();
            aantalBeurtenGroen = new Label();
            aantalBeurtenRood = new Label();



            //teken speelbord
            for (int x = 0; x < Speelbord.AANTAL_KOLOMMEN; x++) {
                for (int y = 0; y < Speelbord.AANTAL_RIJEN; y++) {
                    for (Vak vak1 : speelbord.getVakken()) {
                        if (vak1.getX() == x && vak1.getY() == y) {
                            int vakNummer = vak1.getVakNummer();
                            Text vakNummerText = new Text("");
                            if (vakNummer < 69) {
                                vakNummerText = new Text(Integer.toString(vakNummer));
                            }

                            vakNummerText.setFont(Font.font("Bradley Hand ITC"));
                            vak = new Rectangle(x, y, vakBreedte, vakHoogte);
                            vak.setStroke(Color.BLACK);
                            Kleur kleur = vak1.getKleur();
                            switch (kleur) {
                                case BLAUW:
                                    vak.setFill(blauw);
                                    break;
                                case GEEL:
                                    vak.setFill(geel);
                                    break;
                                case ROOD:
                                    vak.setFill(rood);
                                    break;
                                case GROEN:
                                    vak.setFill(groen);
                                    break;
                                case BLANCO:
                                    vak.setFill(Color.TRANSPARENT);
                            }
                            vakNummerText.setTextAlignment(TextAlignment.CENTER);
                            //testen:

                            this.add(vak, x, y);
                            this.add(vakNummerText, x, y);

                            if (vak1.isSafePoint()) {
                                safePoint = new Ellipse(x, y,vakBreedte*0.5, vakHoogte*0.5);
                                safePoint.setFill(Color.TRANSPARENT);
                                safePoint.setStroke(Color.BLACK);
                                this.add(safePoint, x, y);

                            }
                        }
                    }
                }
            }

            //voeg namen toe van de spelers obv kleur
            if (kleurenSpelers.size() != 0) {
                plaatsNamenSpelers(kleurenSpelers, namenSpelers);
            }

            this.setPrefSize(CANVAS_HOOGTE, CANVAS_BREEDTE);

        }

        // Layout van de Nodes
// add… methodes (of set…)
// Insets, padding, alignment, …
        private void layoutNodes () {

            nestGroen.setStroke(groen);
            nestGroen.setStrokeWidth(10);
            nestBlauw.setStroke(blauw);
            nestBlauw.setStrokeWidth(10);
            nestGeel.setStroke(geel);
            nestGeel.setStrokeWidth(10);
            nestRood.setStroke(rood);
            nestRood.setStrokeWidth(10);

            //set column & row constraints:
            for (int i = 0; i < Speelbord.AANTAL_KOLOMMEN; i++) {

                ColumnConstraints column = new ColumnConstraints(vakBreedte);
                //column.setHalignment(HPos.CENTER);
                column.setHgrow(Priority.ALWAYS);
                this.getColumnConstraints().add(column);

            }

            for (int j = 0; j < Speelbord.AANTAL_RIJEN; j++) {
                RowConstraints row = new RowConstraints(vakHoogte);
                //row.setPercentHeight(50);
                row.setVgrow(Priority.ALWAYS);
                this.getRowConstraints().add(row);
            }

            //plaats nodes op pane:
            this.setPrefSize(CANVAS_BREEDTE, CANVAS_HOOGTE);
            this.add(nestBlauw, 2, 1, 6, 6);
            this.add(nestGeel, 15, 1, 19, 6);
            this.add(nestRood, 2, 13, 6, 18);
            this.add(nestGroen, 15, 13, 19, 18);




            plaatsPionnen(kleurenSpelers);

            this.setPadding(new Insets(10));

         //   this.setGridLinesVisible(true);

        }


        public Ellipse getSafePoint () {
            return safePoint;
        }


        public static Color getRood () {
            return rood;
        }

        public static Color getBlauw () {
            return blauw;
        }

        public static Color getGroen () {
            return groen;
        }

        public static Color getGeel () {
            return geel;
        }



        public Speelbord getSpeelbord () {
            return speelbord;
        }

        public Label getNaamSpeler () {
            return naamSpeler;
        }

        public UISettings getUiSettings () {
            return uiSettings;
        }



    public double getVakHoogte() {
        ObservableList<Node> childrens = this.getChildren();
        double hoogte=0;
        for (Node node : childrens) {
            if (node instanceof Rectangle) {
                hoogte=((Rectangle) node).getHeight();
                return hoogte;
            }

        }
        return hoogte;
    }

    public double getVakBreedte() {
        ObservableList<Node> childrens = this.getChildren();
        double breedte=0;
        for (Node node : childrens) {
            if (node instanceof Rectangle) {
                breedte=((Rectangle) node).getHeight();
                return breedte;
            }

        }
        return breedte;
    }


        public ImageView getGegooideWaardeSpelerBl () {
            return gegooideWaardeSpelerBl;
        }

        public ImageView getGegooideWaardeSpelerGe () {
            return gegooideWaardeSpelerGe;
        }

        public ImageView getGegooideWaardeSpelerRo () {
            return gegooideWaardeSpelerRo;
        }

        public ImageView getGegooideWaardeSpelerGr () {
            return gegooideWaardeSpelerGr;
        }

        public Map<Kleur, Label> getLabelsNaamSpeler () {
            return labelsNaamSpeler;
        }

        public List<Button> getPionnenGroen () {
            return pionnenGroen;
        }

        public List<Button> getPionnenGeel () {
            return pionnenGeel;
        }

        public List<Button> getPionnenBlauw () {
            return pionnenBlauw;
        }

        public List<Button> getPionnenRood () {
            return pionnenRood;
        }

        public List<Button> getPionnenOpVak(int x, int y){
            List<Button>pionnenOpVak=new ArrayList<>();
            ObservableList<Node> childrens = this.getChildren();
            for (Node child : childrens) {
                if(child instanceof Button && getRowIndex(child)==y && getColumnIndex(child)==x){
                    pionnenOpVak.add((Button)child);
                }
            }
            return pionnenOpVak;
        }

        public List<Button> getPionnen(Kleur kleur){
            switch (kleur) {
                case ROOD:
                    return pionnenRood;
                case GEEL:
                   return pionnenGeel;
                case GROEN:
                    return pionnenGroen;
                case BLAUW:
                    return pionnenBlauw;
            }
            return null;
        }
        public Button getPionButton(Kleur kleur,int pionNummer){
            for (Button button : getPionnen(kleur)) {
                int nummerButton=Integer.parseInt(button.getText());
                if(nummerButton==pionNummer){
                    return button;
                }
            }
        return null;
        }

        //plaats de nodige pionnen op bord obv gekozen kleuren van de spelers
       private void plaatsPionnen (List < Kleur > kleurenSpelers) {
            if (kleurenSpelers.size() != 0) {
                for (Kleur kleurSpeler : kleurenSpelers) {
                    switch (kleurSpeler) {
                        case ROOD:
                            List<Vak> vakkenNestRood = speelbord.getNest(Kleur.ROOD);
                            if (Files.exists(uiSettings.getPionRoodPah())) {
                                for (int i = 0; i < 4; i++) {
                                    try {
                                        ImageView pionRoodImage = new ImageView(new Image(uiSettings.getPionRoodPah().toUri().toURL().toString()));
                                        pionRoodImage.setFitHeight(vakHoogte / 2);
                                        pionRoodImage.setFitWidth(vakBreedte / 3);
                                        pionRoodImage.setPreserveRatio(false);
                                        pionRoodImage.setSmooth(true);
                                        int nummer = i + 1;
                                        String nummerPion = String.valueOf(nummer);
                                        pionRood = new Button(nummerPion, pionRoodImage);
                                        pionRood.setDisable(false);

                                        //zet text dichter bij image:
                                        pionRood.setGraphicTextGap(0);
                                        //zet text boven image
                                        pionRood.setContentDisplay(ContentDisplay.BOTTOM);
                                        pionRood.setFont(Font.font(9));
                                        pionRood.setDisable(true);
                                        int xr = vakkenNestRood.get(i).getX();
                                        int yr = vakkenNestRood.get(i).getY();
                                   //     getHBox(xr,yr).getChildren().add(pionRood);
                                        GridPane.setHalignment(pionRood,HPos.CENTER);
                                        this.add(pionRood,xr,yr);
                                        pionnenRood.add(pionRood);
                                    } catch (MalformedURLException e) {

                                    }
                                }
                            }
                            break;
                        case GROEN:
                            List<Vak> vakkenNestGroen = speelbord.getNest(Kleur.GROEN);
                            if (Files.exists(uiSettings.getPionGroenPath())) {
                                for (int i = 0; i < 4; i++) {
                                    try {
                                        ImageView pionGroenImage = new ImageView(new Image(uiSettings.getPionGroenPath().toUri().toURL().toString()));
                                        pionGroenImage.setFitHeight(vakHoogte / 2);
                                        pionGroenImage.setFitWidth(vakBreedte / 3);
                                        int nummer = i + 1;
                                        String nummerPion = String.valueOf(nummer);
                                        pionGroen = new Button(nummerPion, pionGroenImage);
                                        pionGroen.setFont(Font.font(9));
                                        //maak button transparant

                                        pionGroen.setDisable(false);
                                        //zet text dichter bij image:
                                        pionGroen.setGraphicTextGap(0);
                                        //zet text boven image
                                        pionGroen.setContentDisplay(ContentDisplay.BOTTOM);
                                        pionGroen.setDisable(true);
                                        int xgr = vakkenNestGroen.get(i).getX();
                                        int ygr = vakkenNestGroen.get(i).getY();
                                        //  HBox hboxgr = (HBox) getHBox(xgr, ygr);
                                        //  hboxgr.getChildren().add(pionGroen);
                                        //  hboxgr.setAlignment(Pos.CENTER);
                                        this.add(pionGroen, xgr, ygr);
                                        GridPane.setHalignment(pionGroen,HPos.CENTER);
                                        pionnenGroen.add(pionGroen);
                                    } catch (MalformedURLException ex) {

                                    }
                                }
                            }
                            break;
                        case GEEL:
                            List<Vak> vakkenNestGeel = speelbord.getNest(Kleur.GEEL);
                            if (Files.exists(uiSettings.getPionGeelPath())) {
                                for (int i = 0; i < 4; i++) {
                                    try {
                                        ImageView pionGeelImage = new ImageView(new Image(uiSettings.getPionGeelPath().toUri().toURL().toString()));
                                        int nummer = i + 1;
                                        pionGeelImage.setFitHeight(vakHoogte/2);
                                        pionGeelImage.setFitWidth(vakBreedte/3);
                                        String nummerPion = String.valueOf(nummer);
                                        pionGeel = new Button(nummerPion, pionGeelImage);

                                        pionGeel.setDisable(false);
                                        //zet text dichter bij image:
                                        pionGeel.setGraphicTextGap(0);
                                        //zet text boven image
                                        pionGeel.setContentDisplay(ContentDisplay.BOTTOM);
                                        pionGeel.setFont(Font.font(9));
                                        //pionGeel.setFitHeight(vakHoogte / 2);
                                        //pionGeel.setFitWidth(vakHoogte / 2);
                                        pionGeel.setDisable(true);
                                        //    pionGeel.setPrefSize(vakBreedte/2,vakHoogte/2);
                                        int xge = vakkenNestGeel.get(i).getX();
                                        int yge = vakkenNestGeel.get(i).getY();
                                        //     HBox hboxge = (HBox) getHBox(xge, yge);
                                        //     hboxge.getChildren().add(pionGeel);
                                        //     hboxge.setAlignment(Pos.CENTER);
                                        this.add(pionGeel, xge, yge);
                                        GridPane.setHalignment(pionGeel,HPos.CENTER);
                                        pionnenGeel.add(pionGeel);
                                    } catch (MalformedURLException e) {

                                    }
                                }
                            }
                            break;
                        case BLAUW:
                            List<Vak> vakkenNestBlauw = speelbord.getNest(Kleur.BLAUW);
                            if (Files.exists(uiSettings.getPionBlauwPath())) {
                                for (int i = 0; i < 4; i++) {
                                    try {
                                        ImageView pionBlauwImage = new ImageView(new Image(uiSettings.getPionBlauwPath().toUri().toURL().toString()));
                                        int nummer = i + 1;
                                        pionBlauwImage.setFitHeight(vakHoogte / 2);
                                        pionBlauwImage.setFitWidth(vakBreedte / 3);
                                        String nummerPion = String.valueOf(nummer);
                                        pionBlauw = new Button(nummerPion, pionBlauwImage);
                                        pionBlauw.setDisable(false);
                                        pionBlauw.setContentDisplay(ContentDisplay.BOTTOM);
                                        pionBlauw.setFont(Font.font(9));
                                        pionBlauw.setDisable(true);

                                        int xb = vakkenNestBlauw.get(i).getX();
                                        int yb = vakkenNestBlauw.get(i).getY();
                                     //   HBox hboxb = (HBox) getHBox(xb, yb);
                                     //   hboxb.getChildren().add(pionBlauw);
                                     //   hboxb.setAlignment(Pos.CENTER);
                                        this.add(pionBlauw,xb,yb);
                                        pionnenBlauw.add(pionBlauw);
                                        GridPane.setHalignment(pionBlauw,HPos.CENTER);
                                    } catch (MalformedURLException e) {

                                    }
                                }
                            }
                            break;

                    }
                }
            }
        }



        private void plaatsNamenSpelers (List < Kleur > kleurenSpelers, List < String > namenSpelers){
            for (int i = 0; i < namenSpelers.size(); i++) {
                String labeltext = namenSpelers.get(i);
                naamSpeler = new Label(labeltext);
                naamSpeler.setFont(Font.font(16));
                //  naamSpeler.setWrapText(true);
                // naamSpeler.setMaxWidth(vakBreedte * 4);
                naamSpeler.setPrefHeight(vakHoogte);
                naamSpeler.setPrefWidth(vakHoogte*4);
                naamSpeler.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY, new BorderWidths(3))));
                Kleur kleurSpeler = kleurenSpelers.get(i);
                switch (kleurSpeler) {
                    case BLAUW:
                        this.add(naamSpeler, 7, 7, 2,1);
                        labelsNaamSpeler.put(Kleur.BLAUW, naamSpeler);
                        this.add(gegooideWaardeSpelerBl, 1, 7);
                        this.add(aantalBeurtenBlauw, 7,6 );
                        break;
                    case GEEL:
                        this.add(naamSpeler, 13, 7, 2, 1);
                        labelsNaamSpeler.put(Kleur.GEEL, naamSpeler);
                        this.add(gegooideWaardeSpelerGe, 19,7);
                        this.add(aantalBeurtenGeel, 13,6);
                        break;
                    case ROOD:
                        this.add(naamSpeler, 7, 13, 2, 1);
                        labelsNaamSpeler.put(Kleur.ROOD, naamSpeler);
                        this.add(gegooideWaardeSpelerRo, 1, 13);
                        this.add(aantalBeurtenRood, 7,14);
                        break;
                    case GROEN:
                        this.add(naamSpeler, 13, 13, 2 ,1);
                        labelsNaamSpeler.put(Kleur.GROEN, naamSpeler);
                        this.add(gegooideWaardeSpelerGr, 19,13);
                        this.add(aantalBeurtenGroen,13,14 );
                        break;
                }
            }
        }


    public List<Kleur> getKleurenSpelers() {
        return kleurenSpelers;
    }

    public List<String> getNamenSpelers() {
        return namenSpelers;
    }

    public Label getAantalBeurtenRood() {
        return aantalBeurtenRood;
    }

    public Label getAantalBeurtenGeel() {
        return aantalBeurtenGeel;
    }

    public Label getAantalBeurtenGroen() {
        return aantalBeurtenGroen;
    }

    public Label getAantalBeurtenBlauw() {
        return aantalBeurtenBlauw;
    }

    // implementatie van de nodige
// package-private Getters
    }

