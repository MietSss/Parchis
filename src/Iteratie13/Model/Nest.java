package Iteratie13.Model;

/**
 * @author Miet Smets
 * @version 1.0 11/03/2021 12:18
 */
public class Nest extends Vak {
    //ook vakken in nest hebben een vaknummer
    //obv vaknummer halen we x en y positie op.

    public Nest(Kleur kleur, int vakNummer, int y, int x) {
        super(kleur, vakNummer, y, x);
    }

    //haalt eerste vaknummer op van nest per kleur
    public static int getStartVakNummer(Kleur kleur) {
        switch (kleur) {
            case BLAUW: return 101;
            case GEEL: return 105;
            case GROEN: return 109;
            case ROOD: return 113;
        }
        return 0;
    }

    @Override
    public String toString() {
        return String.format("%s + %s",super.toString(), "is een nest.");
    }
}
