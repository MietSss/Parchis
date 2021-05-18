package Iteratie14.Model;

import java.util.Random;

public class Dobbelsteen {
    private final Random random = new Random();
    //om te testen:
    private int[] waardes = {2, 3, 5, 5, 5, 5, 5,5,6,2,4,1,4,5,1,6};
    int index = 0;


    public Dobbelsteen() {
    }

    //als dobbelsteen wordt gegooid krijg je een random waarde terug van 1 t.e.m. 6
    //deze waarde moet gelinkt worden aan speler aan zet
    public int getWaarde() {
        //return random.nextInt(6) + 1;

        if (index + 1 >= waardes.length) {
            index = 0;
        }
        int waarde = waardes[index++];

        return waarde;


    }
}



