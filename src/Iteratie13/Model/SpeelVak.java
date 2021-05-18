package Iteratie13.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Miet Smets
 * @version 1.0 11/03/2021 18:18
 */
public class SpeelVak extends Vak {

    private static final int AANTAL_SAFEPOINTS = 12;

    public SpeelVak(int vakNummer, int y, int x) {
        super(vakNummer, y, x);
    }
    //haalt eerste vaknummer op in het speelveld voor kleur
    public static int getStartVakNummer(Kleur kleur) {
        switch (kleur) {
            case BLAUW:
                return 22;
            case GEEL:
                return 5;
            case GROEN:
                return 56;
            case ROOD:
                return 39;
        }
        return 0;
    }
    //haalt laatste vaknummer op in het speelveld voor kleur. Na dit vaknummer kom je in landingsbaan terecht.
    public static int getEindVakNummer(Kleur kleur) {
        switch (kleur) {
            case BLAUW:
                return 17;
            case GEEL:
                return 68;
            case GROEN:
                return 51;
            case ROOD:
                return 34;
        }
        return 0;
    }

    //stopt alle vaknummers van safepoints in een lijst
    public static List<Integer> getSafePoints() {
        List <Integer> safePoints=new ArrayList<>();
        //1= safepoint= =5
        //2= safepoint =12
        safePoints.add(5);
        safePoints.add(12);
        int safePoint=12;
            //3 safepoint + 5 = 17
            //4 safepoint + 5 = 22
            //5 safepoint + 7 = 29
            //6 safepoint + 5= 34
            //7 safepoint + 5 = 39
            //8 safepoint + 7 = 46
            // 9 safepoint +5 = 51
            // 10 safepoint +5 = 56
            // 11 safepoint +7 = 63
            //12 safepoint + 5 = 68
            for (int j = 3; j <= AANTAL_SAFEPOINTS; j++) {
                    if (j == 5 || j == 8 || j == 11) {
                        safePoint += 7;
                    } else {
                        safePoint += 5;
                    }
                    safePoints.add(safePoint);
            }
        return safePoints;
    }

    //als vaknummer voorkomt in de lijst van safepoints dan geeft deze true terug
    @Override
    public boolean isSafePoint(){
        for (Integer safePoint : getSafePoints()) {
            if(getVakNummer()==safePoint){
                return true;
            }
        }
        return false;
    }

}



