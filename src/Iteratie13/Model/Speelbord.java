package Iteratie13.Model;

import java.util.ArrayList;
import java.util.List;

public class Speelbord {
    private static final int AANTAL_RIJEN = 21;
    private static final int AANTAL_KOLOMMEN = 21;
    private final List<Vak> vakken;
    String[][] bordZonderPion = new String[AANTAL_RIJEN][AANTAL_KOLOMMEN];
    private final List<Integer> padRood;
    private final List<Integer> padGeel;
    private final List<Integer> padBlauw;
    private final List<Integer> padGroen;


    public Speelbord() {

        vakken = new ArrayList<>();
        //gewone vakken
        //in een lus gieten
        // gewone speelvakken
        int z = 1; // verhogen van de vaknummers
        for (int j = 19; j > 11; j--, z++) {
            vakken.add(new SpeelVak(z, j, 9));
        } // tem 8
        for (int k = 8; k > 1; k--, z++) {
            vakken.add(new SpeelVak(z, 11, k));
        } // tem 15
        for (int l = 11; l > 9; l--, z++) {
            vakken.add(new SpeelVak(z, l, 1));
        } // tem 17
        for (int m = 1; m < 9; m++, z++) {
            vakken.add(new SpeelVak(z, 9, m));
        }//tem 25
        for (int n = 8; n > 1; n--, z++) {
            vakken.add(new SpeelVak(z, n, 9));
        }//tem 32
        for (int o = 9; o < 11; o++, z++) {
            vakken.add(new SpeelVak(z, 1, o));
        } //tem 34
        for (int p = 1; p < 9; p++, z++) {
            vakken.add(new SpeelVak(z, p, 11));
        }//tem 42
        for (int q = 12; q < 19; q++, z++) {
            vakken.add(new SpeelVak(z, 9, q));
        } //tem 49
        for (int r = 9; r < 11; r++, z++) {
            vakken.add(new SpeelVak(z, r, 19));
        } // tem 51
        for (int s = 19; s > 11; s--, z++) {
            vakken.add(new SpeelVak(z, 11, s));
        } // tem 59
        for (int t = 12; t < 19; t++, z++) {
            vakken.add(new SpeelVak(z, t, 11));
        } //tem 66
        for (int u = 11; u > 9; u--, z++) {
            vakken.add(new SpeelVak(z, 19, u));
        } // tem 68

// landingsbanen
        for (int j = 18; j > 11; j--, z++) {
            vakken.add(new LandingsBaan(Kleur.GEEL, z, j, 10));
        }// 69 - tem 76
        for (int l = 2; l < 10; l++, z++) {
            vakken.add(new LandingsBaan(Kleur.BLAUW, z, 10, l));
        }// 77 - tem 84
        for (int m = 2; m < 10; m++, z++) {
            vakken.add(new LandingsBaan(Kleur.ROOD, z, m, 10));
        }  // 85 - tem 92
        for (int k = 18; k > 11; k--, z++) {
            vakken.add(new LandingsBaan(Kleur.GROEN, z, 10, k));
        }  //  93- tem 100

//nesten
        vakken.add(new Nest(Kleur.BLAUW, 101, 4, 3));
        vakken.add(new Nest(Kleur.BLAUW, 102, 4, 4));
        vakken.add(new Nest(Kleur.BLAUW, 103, 5, 3));
        vakken.add(new Nest(Kleur.BLAUW, 104, 5, 4));
        vakken.add(new Nest(Kleur.GEEL, 105, 15, 3));
        vakken.add(new Nest(Kleur.GEEL, 106, 15, 4));
        vakken.add(new Nest(Kleur.GEEL, 107, 16, 3));
        vakken.add(new Nest(Kleur.GEEL, 108, 16, 4));
        vakken.add(new Nest(Kleur.GROEN, 109, 15, 16));
        vakken.add(new Nest(Kleur.GROEN, 110, 15, 17));
        vakken.add(new Nest(Kleur.GROEN, 111, 16, 16));
        vakken.add(new Nest(Kleur.GROEN, 112, 16, 17));
        vakken.add(new Nest(Kleur.ROOD, 113, 4, 16));
        vakken.add(new Nest(Kleur.ROOD, 114, 4, 17));
        vakken.add(new Nest(Kleur.ROOD, 115, 5, 16));
        vakken.add(new Nest(Kleur.ROOD, 116, 5, 17));


        //we vullen pad in obv vaknummers

        padRood = padPerKleur(Kleur.ROOD);
        padGeel = padPerKleur(Kleur.GEEL);
        padBlauw = padPerKleur(Kleur.BLAUW);
        padGroen = padPerKleur(Kleur.GROEN);

    }

    public List<Integer> getPad(Kleur kleur) {
        switch (kleur) {
            case BLAUW:
                return padBlauw;
            case GEEL:
                return padGeel;
            case GROEN:
                return padGroen;
            case ROOD:
                return padRood;
        }
        return null;
    }

    public int getIndexInPad(int vakNummer, Kleur kleur) {
        List<Integer> padKleur = getPad(kleur);
        for (int i = 0; i < padKleur.size(); i++) {
            if (padKleur.get(i) == vakNummer) {
                return i;
            }
        }
        return 0;
    }


    public Vak getVak(int vaknummer) {
        for (Vak vak : vakken) {
            if (vak.getVakNummer() == vaknummer) {
                return vak;
            }
        }
        return null;
    }

    public List<Integer> padPerKleur(Kleur kleur) {
        List<Integer> pad = new ArrayList<>();
        for (int i = SpeelVak.getStartVakNummer(kleur); i <= 68; i++) {
            pad.add(i);
        }
        //tenzij als kleur=geel, komt na 68, vaknr 1
        if (kleur != Kleur.GEEL) {
            for (int i = 1; i <= SpeelVak.getEindVakNummer(kleur); i++) {
                pad.add(i);
            }
        }
        for (int i = LandingsBaan.getStartVakNummer(kleur); i <= LandingsBaan.getEindVakNummer(kleur); i++) {
            pad.add(i);
        }
        return pad;
    }


    public List<Vak> getNest(Kleur kleur) {
        List<Vak> nest = new ArrayList<>();
        for (Vak vak : vakken) {
            if (vak instanceof Nest && vak.getKleur().equals(kleur)) {
                nest.add(vak);
            }
        }
        return nest;
    }


    public String[][] getBordZonderPion() {
        for (int i = 0; i < AANTAL_RIJEN; i++) {
            for (int j = 0; j < AANTAL_KOLOMMEN; j++) {
                if (i == 0 || i == AANTAL_RIJEN - 1) {
                    bordZonderPion[i][j] = "-\t";
                } else if (j == 0 || j == AANTAL_KOLOMMEN - 1) {
                    bordZonderPion[i][j] = "|\t";
                } else {
                    for (Vak vak : vakken) {
                        if (vak.getX() == i && vak.getY() == j) {
                            bordZonderPion[i][j] = (vak.getVakNummer()) + "\t";
                            break;
                        } else {
                            bordZonderPion[i][j] = " \t";
                        }
                    }
                }
            }
        }
        return bordZonderPion;
    }

    public void print(String[][] bord) {
        for (int i = 0; i < AANTAL_RIJEN; i++) {
            for (int j = 0; j < AANTAL_KOLOMMEN; j++) {
                System.out.print(bord[i][j]);
            }
            System.out.println();
        }

    }



          /*  for (int i = 0; i < pionList.size(); i++) {
                Pion pion = pionList.get(i);
                int vaknummer = pion.getVakNummer();
                System.out.printf("[P%d - %d]", pionList.get(i).getPionNummer(), vaknummer);
            }


            System.out.println();

            for (int j = 0; j < pionList.size(); j++) {
                System.out.println(pionList.get(j));
            }
            System.out.println(pionList.size());
*/
}




