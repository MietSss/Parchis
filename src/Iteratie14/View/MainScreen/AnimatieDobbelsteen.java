package Iteratie14.View.MainScreen;


import Iteratie14.Model.Kleur;
import Iteratie14.Model.ParchisModel;
import Iteratie14.Model.Speler;
import Iteratie14.View.UISettings;
import javafx.animation.AnimationTimer;
import javafx.animation.Transition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class AnimatieDobbelsteen extends AnimationTimer {


    private long FRAMES_PER_SEC=20L;
    private long INTERVAL=1000000000L/FRAMES_PER_SEC;
    private int MAX_ROLLS=20;

    private long last=0;
    private int count=0;

    private Image dobbelsteen6;
    private Image dobbelsteen5;
    private Image dobbelsteen4;
    private Image dobbelsteen3;
    private Image dobbelsteen2;
    private Image dobbelsteen1;
    private List<Image> dobbelstenen;
    private UISettings uiSettings;
    private ImageView imageViewOnderInstructies;
    private ImageView imageViewSpelerAanZet;
    private ParchisModel model;
    private MainScreenView view;


    AnimatieDobbelsteen(ParchisModel model, MainScreenView view){
        this.model=model;
        this.view=view;
        this.imageViewOnderInstructies=view.getDobbelsteen();
        dobbelstenen=new ArrayList<>();
        uiSettings=new UISettings();

        if (Files.exists(uiSettings.getDobbelsteen6())) {
            try {
                dobbelsteen6 = new Image(uiSettings.getDobbelsteen6().toUri().toURL().toString());
                dobbelstenen.add(dobbelsteen6);
            } catch (MalformedURLException e) {

            }
            if (Files.exists(uiSettings.getDobbelsteen5())) {
                try {
                    dobbelsteen5 = new Image(uiSettings.getDobbelsteen5().toUri().toURL().toString());
                    dobbelstenen.add(dobbelsteen5);
                } catch (MalformedURLException e) {

                }
            }
            if (Files.exists(uiSettings.getDobbelsteen4())) {
                try {
                    dobbelsteen4 = new Image(uiSettings.getDobbelsteen4().toUri().toURL().toString());
                    dobbelstenen.add(dobbelsteen4);

                } catch (MalformedURLException e) {

                }
            }
            if (Files.exists(uiSettings.getDobbelsteen3())) {
                try {
                    dobbelsteen3 = new Image(uiSettings.getDobbelsteen3().toUri().toURL().toString());
                    dobbelstenen.add(dobbelsteen3);
                } catch (MalformedURLException e) {

                }
            }
            if (Files.exists(uiSettings.getDobbelsteen2())) {
                try {
                    dobbelsteen2 =new Image(uiSettings.getDobbelsteen2().toUri().toURL().toString());
                    dobbelstenen.add(dobbelsteen2);
                } catch (MalformedURLException e) {

                }
            }
            if (Files.exists(uiSettings.getDobbelsteen1())) {
                try {
                    dobbelsteen1 = new Image(uiSettings.getDobbelsteen1().toUri().toURL().toString());
                    //     dobbelsteen1.setFitHeight(100);
                    //     dobbelsteen1.setFitWidth(100);
                    dobbelstenen.add(dobbelsteen1);

                } catch (MalformedURLException e) {

                }
            }
        }
        imageViewSpelerAanZet=null;



    }

    //update dobbelsteen onder instructie: wordt gebruikt voor opeenvolgende images te tonen om het rollen
    //te simuleren
        void setDieImageOnderInstructie(int r){

        switch (r){
            case 1:
                imageViewOnderInstructies.setImage(dobbelsteen1);
                break;
            case 2:
                imageViewOnderInstructies.setImage(dobbelsteen2);
                break;
            case 3:
                imageViewOnderInstructies.setImage(dobbelsteen3);
                break;
            case 4:
                imageViewOnderInstructies.setImage(dobbelsteen4);
                break;
            case 5:
                imageViewOnderInstructies.setImage(dobbelsteen5);
                break;
            case 6:
                imageViewOnderInstructies.setImage(dobbelsteen6);
            default:
                imageViewOnderInstructies.setImage(dobbelsteen6);
        }
    }
    //update dobbelsteen op het bord bij speler aan zet
        void setDieImageBijSpeler(int r){
            Speler spelerAanZet = model.getSpelerAanZet();
            switch (spelerAanZet.getKleur()) {
                case ROOD:
                    imageViewSpelerAanZet=view.getGameBoardView().getGegooideWaardeSpelerRo();
                    break;
                case GEEL:
                    imageViewSpelerAanZet= view.getGameBoardView().getGegooideWaardeSpelerGe();
                    break;
                case GROEN:
                    imageViewSpelerAanZet= view.getGameBoardView().getGegooideWaardeSpelerGr();
                    break;
                case BLAUW:
                    imageViewSpelerAanZet= view.getGameBoardView().getGegooideWaardeSpelerBl();
                    break;
            }

        switch (r){
            case 1:
                imageViewSpelerAanZet.setImage(dobbelsteen1);
                break;
            case 2:
                imageViewSpelerAanZet.setImage(dobbelsteen2);
                break;
            case 3:
                imageViewSpelerAanZet.setImage(dobbelsteen3);
                break;
            case 4:
                imageViewSpelerAanZet.setImage(dobbelsteen4);
                break;
            case 5:
                imageViewSpelerAanZet.setImage(dobbelsteen5);
                break;
            case 6:
                imageViewSpelerAanZet.setImage(dobbelsteen6);
            default:
                imageViewSpelerAanZet.setImage(dobbelsteen6);
        }
    }

    // vergroot label naam speler die aan zet is
        void animateSpelerAanZet() {
        Speler spelerAanZet = model.getSpelerAanZet();
        Kleur kleurSpelerAanZet = spelerAanZet.getKleur();
        Label labelSpelerAanZet = null;
        //zet eerst alle labels terug op hun normale font
        for (Kleur kleur : view.getGameBoardView().getLabelsNaamSpeler().keySet()) {
            view.getGameBoardView().getLabelsNaamSpeler().get(kleur).setFont(Font.font(16));
        }
        //bekijk dan welke label behoort tot speler aanzet.
        for (Kleur kleurLabel : view.getGameBoardView().getLabelsNaamSpeler().keySet()) {
            if (kleurLabel.equals(kleurSpelerAanZet)) {
                labelSpelerAanZet = view.getGameBoardView().getLabelsNaamSpeler().get(kleurLabel);
            }
        }

        TextSizeTransition transition = new TextSizeTransition(labelSpelerAanZet);
        transition.play();

    }


    @Override
    public void handle(long now) {
        if(now-last >INTERVAL){
            int r=1+(int)(Math.random()*5);
            setDieImageOnderInstructie(r);
            last=now;
            count++;
            if(count>MAX_ROLLS){
                this.stop();
                //als het voor eerste beurt is mag je knop gooi direct terug aan zetten. Anders moet je eerst een pion verzetten.
                if(model.isGooiVoorEersteBeurt()) {
                    view.getWerp().setDisable(false);
                }
                //zet de uiteindelijk gegooide waarde
                setDieImageOnderInstructie(model.getWorp());
                setDieImageBijSpeler(model.getWorp());
                if(model.isGooiVoorEersteBeurt()) {
                    //bepaal speler aan zet. indien laatste speler in rij, wordt eerste speler gekozen
                    model.kiesVolgendeSpeler();
                    //instructies worden geupdate
                    animateSpelerAanZet();
                    //geeft instructie om te gooien aan de speler aan zet
                    model.getInstructiesGooi(model.getSpelerAanZet());
                }else{
                    //maak pionnen klikbaar van speler aan zet
                    if(model.isNormaalSpel()) {
                        List<Button> pionnen = view.getGameBoardView().getPionnen(model.getSpelerAanZet().getKleur());
                        for (Button button : pionnen) {
                            button.setDisable(false);
                        }
                    }
                }
                view.getTextInstructies().setText(model.getInstruction());
                count=0;
            }

        }

    }
}
