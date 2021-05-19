package Iteratie14.View.MainScreen;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class TextSizeTransition extends Transition {

    private Label label;
    private final int END_FONT=80;
    private final int START_FONT=16;
    private final double DURATION_SECONDEN=1.0;
    private final int END=END_FONT-START_FONT;

    TextSizeTransition(Label label){
        this.label=label;
        setCycleDuration(Duration.seconds(DURATION_SECONDEN));
        setInterpolator(Interpolator.LINEAR);
    }

    @Override
    protected void interpolate(double frac) {
        //als frac 1 dan is font size gelijk aan START_FONT + END(=END_FONT - START_FONT)
        int size=(int)((END*frac)-START_FONT);
        if(size<=END){
            label.setFont(Font.font(size));
        }
    }


}
