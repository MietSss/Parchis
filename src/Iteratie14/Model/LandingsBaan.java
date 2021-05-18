package Iteratie14.Model;


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
                return 69;
            case GEEL:
                return 85;
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
                return 76;
            case GEEL:
                return 92;
        }
        return 0;
    }

    //geeft true als het vak = laatste vaknummer van de landingsbaan
    @Override
    public boolean isEindBestemming(Kleur kleur) {
        return getVakNummer() == getEindVakNummer(kleur);
    }
}
