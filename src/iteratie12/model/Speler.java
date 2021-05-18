package iteratie12.model;

import java.util.*;

import static iteratie12.model.Spelers.keyboard;

public class Speler {
    final int MAX_PIONNEN_PER_SPELER = 4;
    private Kleur kleur;
    private String naam;
    //geen aparte klasse pionnen omdat deze toch altijd aan een speler gelinkt is en alleen oproepbaar is via de speler.
    private List<Pion> pionnen;
    private boolean isAanZet;
    private int aantalBeurten;
    private Speelbord speelbord;
    private int aantalWorpen;
    private int worp;
    private Pion laatstVerzet;
    private List<Integer> barrieres;

    //bij aanmaak speler worden meteen pionnen aangemaakt.
    public Speler(Kleur kleur, String naam, Speelbord speelbord) {
        this.kleur = kleur;
        this.naam = naam;
        pionnen = new ArrayList<>();
        this.speelbord = speelbord;
        for (int j = 0; j < MAX_PIONNEN_PER_SPELER; j++) {
            int vakNummer = Nest.getStartVakNummer(kleur) + j;
            pionnen.add(new Pion(kleur, j + 1, speelbord.getNest(kleur).get(j)));
        }
    }

    public boolean isAanZet() {
        return isAanZet;
    }

    public int getAantalBeurten() {
        return aantalBeurten;
    }

    //enkel nodig om stuk apart te testen: (later verwijderen)
    public void setAantalBeurten(int aantalBeurten) {
        this.aantalBeurten = aantalBeurten;
    }

    // methode geeft aantal pionnen terug dat speler op een vaknummer heeft staan
    public int getAantalPionnenOpVak(int vakNummer) {
        int aantal = 0;
        for (Pion pion : pionnen) {
            if (pion.getVakNummer() == vakNummer) {
                aantal++;
            }
        }
        return aantal;
    }
    // speler gooit met dobbelsteen. Methode geeft een waarde terug van dobbelsteen.
    public int gooi(Dobbelsteen dobbelsteen) {
        System.out.printf("%s, jij mag gooien%n", naam);
        System.out.println("Druk op een Enter om te gooien");
        keyboard.nextLine().isEmpty();
        worp = dobbelsteen.getWaarde();
        System.out.printf("Je gooide %d%n", worp);
        aantalWorpen++;
        return worp;
    }

    // De eerste speler die alle 4 pionnen in het middenveld heeft
    //gekregen wint.
    public boolean isWinnaar() {
        int pionnenThuis = 0;
        for (Pion pion : pionnen) {
            if (pion.eindBestemmingBereikt()) {
                pionnenThuis++;
            }
        }
        if (pionnenThuis == 4) {
            return true;
        }
        return false;
    }

    //zet aantal worpen van speler terug op 0
    //wordt gebruikt na speel voor eerste beurt + als je van speler wisselt-kiesVolgendeSpeler()
    public void resetAantalWorpen() {
        this.aantalWorpen = 0;
    }

    //nodig voor uitzondering 3x 6 gooien
    public int getAantalWorpen() {
        return aantalWorpen;
    }

    //checkt of alle pionnen uit het nest zijn =
    public boolean pionnenUitNest() {
        int pionnenUitNest = 0;
        for (Pion pion : pionnen) {
            if (!(pion.getVak() instanceof Nest)) {
                pionnenUitNest++;
            }
        }
        if (pionnenUitNest == 4) {
            return true;
        }
        return false;

    }

    //stuurt pion terug naar zijn nest:
    public void pionNaarNest(Pion pionNaarNest) {
        List<Vak> nest = speelbord.getNest(kleur);
        List<Vak> beschikbaarNest = new ArrayList<>();
        beschikbaarNest.addAll(nest);
        for (Vak vak : nest) {
            for (Pion pion1 : pionnen) {
                if (pion1.getVak().equals(vak)) {
                    beschikbaarNest.remove(vak);
                }
            }
        }
        Vak beschikbaarVak = beschikbaarNest.get(0);
        pionNaarNest.setVak(beschikbaarVak);
    }

    //verplaatst pionNummer van speler naar het nieuwVaknummer
    //update welke pion laatst verzet is van speler
    public void verplaats(int pionNummer, int nieuwVaknummer) {
        if (nieuwVaknummer != 0) {
            for (Pion pion : pionnen) {
                if (pion.getPionNummer() == pionNummer) {
                    //update vak van pion:
                    pion.setVak(speelbord.getVak(nieuwVaknummer));
                    //hou bij welke pion je laatst hebt verzet. Nodig voor spelregel als je 3 keer 6 gooit.
                    if (!(speelbord.getVak(nieuwVaknummer) instanceof Nest)) {
                        laatstVerzet = pion;
                    }
                }
            }
        }
    }

    //haalt laatst verzette pion op van speler:
    public Pion getLaatstVerzet() {

        return laatstVerzet;

    }

    public void setAanZet(boolean aanZet) {
        isAanZet = aanZet;
        //als speler aan de beurt is verhogen we de beurt.
        // Als we deze terug op false, zetten mag dit niet ook als beurt tellen
        if (isAanZet) {
            aantalBeurten++;
        }
    }

    public Kleur getKleur() {
        return kleur;
    }

    public String getNaam() {
        return naam;
    }

    public List<Pion> getPionnen() {
        return pionnen;
    }

    //haalt pion op van speler obv pionNummer
    public Pion getPion(int pionNummer) {
        for (Pion pion : pionnen) {
            if (pion.getPionNummer() == pionNummer) {
                return pion;
            }
        }
        return null;
    }

    //vult lijst in met vaknummers die een safepoint zijn en waar de speler 2 van zijn pionnen heeft op staan
    public void setBarrieres() {
        Map<Pion, Integer> pionnenOpSafePoint = new HashMap<>();
        barrieres = new ArrayList<>();
        barrieres.clear();
        for (Integer safePoint : SpeelVak.getSafePoints()) {
            for (Pion pion : pionnen) {
                if (pion.getVakNummer() == safePoint) {
                    pionnenOpSafePoint.put(pion, safePoint);
                }
            }
            if (Collections.frequency(pionnenOpSafePoint.values(), safePoint) > 1) {
                barrieres.add(safePoint);
            }
        }
    }

    //geeft true als speler barrieres heeft
    public boolean heeftBarriere() {
        setBarrieres();
        if (barrieres.size() > 0) {
            return true;
        }
        return false;

    }

    //haalt vaknummer op die een safepoint is en waar speler 2 pionnen heeft op staan
    public int getBarriere() {
        if (heeftBarriere()) {
            return barrieres.get(0);
        }
        return 0;
    }

    @Override
    public String toString() {
        return String.format("%s voor kleur %s%n", naam, kleur);
    }
}



