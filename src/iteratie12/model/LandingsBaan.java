package iteratie12.model;

/**
 * @author Miet Smets
 * @version 1.0 11/03/2021 18:20
 */
public class LandingsBaan extends Vak {
    //landingsbaan is incl. centrumvak (eindbestemming)

    public LandingsBaan(Kleur kleur, int vakNummer, int y, int x) {
        super(kleur, vakNummer, y, x);
    }
    // we hebben ook de vakken in de landingsnaam een vaknummer gegeven

    //haalt vaknummer op van eerste vak landingsbaan per kleur
    public static int getStartVakNummer(Kleur kleur) {
        switch (kleur) {
            case GROEN:
                return 93;
            case BLAUW:
                return 77;
            case ROOD:
                return 85;
            case GEEL:
                return 69;
        }
        return 0;
    }
    //haalt eindbestemming vaknummer op per kleur. laatste vaknummer in landingsbaan=centrumvak=eindbestemming
    public static int getEindVakNummer(Kleur kleur) {
        switch (kleur) {
            case GROEN:
                return 100;
            case BLAUW:
                return 84;
            case ROOD:
                return 92;
            case GEEL:
                return 76;
        }
        return 0;
    }

    //geeft true als het vak = laatste vaknummer van de landingsbaan
    public boolean isEindBestemming(Kleur kleur) {
        if (getVakNummer() == getEindVakNummer(kleur)) {
            return true;
        }
        return false;
    }
}
