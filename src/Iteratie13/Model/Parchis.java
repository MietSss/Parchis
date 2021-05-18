package Iteratie13.Model;


import java.util.*;


public class Parchis {
    private boolean eindeSpel = false;
    private final Scanner keyboard;
    private final Spelers spelers;
    private final Dobbelsteen dobbelsteen;
    private final Speelbord speelbord;
    private Speler spelerAanZet;
    private int worp;
    private final List<Pion> mogelijkeZetten;


    // Constructor
    public Parchis() {
        speelbord = new Speelbord();
        dobbelsteen = new Dobbelsteen();
        spelers = new Spelers(speelbord);
        keyboard = Spelers.keyboard;
        mogelijkeZetten = new ArrayList<>();
    }

    public void start() {
        speelVoorEersteBeurt();
        do {
            beurt();
            if (isEindeSpel()) {
                break;
            }
            kiesVolgendeSpeler();
        }
        while (!eindeSpel);

        eindeSpel();
    }


    // wie mag beginnen?
    public void speelVoorEersteBeurt() {
        int grootsteWaarde = 0;
        int worp;
        Map<Speler, Integer> gegooideWaardes = new HashMap<>();

        System.out.println("Jullie mogen nu één voor één de dobbelsteen gooien. Diegene met het hoogste aantal begint.");
        for (Speler speler : spelers.getSpelers()) {
            worp = speler.gooi(dobbelsteen);
            gegooideWaardes.put(speler, worp);
            if (worp > grootsteWaarde) {
                grootsteWaarde = worp;
                spelerAanZet = speler;
            }
        }
        List<Speler> spelers2 = new ArrayList<>();
        while (Collections.frequency(gegooideWaardes.values(), grootsteWaarde) > 1) {
            spelers2.clear();
            for (Speler speler2 : gegooideWaardes.keySet()) {
                if (gegooideWaardes.get(speler2) == grootsteWaarde) {
                    spelers2.add(speler2);
                }
            }
            gegooideWaardes.clear();
            System.out.print("Het lijkt erop dat zowel ");
            for (int i = 0; i < spelers2.size(); i++) {
                if (i != spelers2.size() - 1) {
                    System.out.print(spelers2.get(i).getNaam() + " als ");
                } else {
                    System.out.print(spelers2.get(i).getNaam());
                }
            }
            System.out.println(" het hoogste aantal hebben gegooid. Jullie mogen opnieuw gooien.");
            grootsteWaarde = 0;
            for (Speler speler2 : spelers2) {
                do {
                    System.out.printf("%s, jij mag gooien%n", speler2.getNaam());
                    System.out.println("Druk op een Enter om te gooien");
                }
                while (!keyboard.nextLine().isEmpty());
                worp = spelerAanZet.gooi(dobbelsteen);
                gegooideWaardes.put(speler2, worp);
                System.out.printf("Je gooide %d%n", worp);
                if (worp > grootsteWaarde) {
                    grootsteWaarde = worp;
                    spelerAanZet = speler2;
                }
            }
        }


        System.out.printf("Speler %s mag als eerste beginnen%n", spelerAanZet.getNaam());
        spelerAanZet.setAanZet(true);
        //zet aantal worpen van alle spelers terug op 0. Deze worpen tellen niet mee.
        for (Speler speler : spelers.getSpelers()) {
            speler.resetAantalWorpen();
        }
        printBord();
    }

    //spelverloop van een beurt. Hier zitten de spelregels in
    public void beurt() {
        do {
            spelerAanZet = spelers.getSpelerAanZet();
            worp = spelerAanZet.gooi(dobbelsteen);
            // haal lijst op met mogelijke pionnen die verzetbaar zijn
            //ophalen lijst van de speelbare pionnen van de speler
            //zijn de pionnen verplaatsbaar?
            //zo ja: wat is hun nieuw mogelijks vak?
            // toon opties en kies
            if (getAantalZetten(spelerAanZet) != 0) {
                //zijn er zetten die prioriteit krijgen?
                //Wanneer er 3 keer 6 na elkaar gegooid wordt, moet de laatste pion die bewogen werd
                // terug naar zijn nest. Tenzij die pion zich reeds op de gekleurde “landingsbaan” bevond.
                // In dat geval moet die pion gewoon verplaatst worden naar het begin van de
                //“landingsbaan”
                Pion laatstVerzettePion = spelerAanZet.getLaatstVerzet();
                if (worp == 6) {
                    if (spelerAanZet.getAantalWorpen() == 3) {
                        if (laatstVerzettePion.getVak() instanceof LandingsBaan) {
                            System.out.println("Ai je hebt 3 keer 6 gegooid. " +
                                    "Je laatst verzette pion moet terug naar begin landingsbaan :(");
                            spelerAanZet.verplaats(laatstVerzettePion.getPionNummer(),
                                    LandingsBaan.getStartVakNummer(laatstVerzettePion.getKleur()));

                        }
                        if (laatstVerzettePion.getVak() instanceof SpeelVak) {
                            System.out.println("Ai je hebt 3 keer 6 gegooid. Je laatst verzette pion moet terug naar zijn nest :(");
                            spelerAanZet.pionNaarNest(laatstVerzettePion);

                        }
                        // Wanneer een speler 4 pionnen op het speelveld heeft staan (niet in het nest) en die
                        //speler gooit een 6, dan mag die speler een pion 7 vakjes laten opschuiven
                    } else {
                        if (spelerAanZet.pionnenUitNest()) {
                            System.out.println("Je hebt 4 pionnen op het speelveld staan en je gooide een 6. " +
                                    "Je mag nu met een pion 7 vakjes vooruit gaan.");
                            worp = 7;
                        }
                        //Als een speler een 6 gooit, dan moet
                        //die speler verplicht één van zijn/haar barrières verbreken door één pion uit het
                        //barrièrevak te verplaatsen.
                        if (spelerAanZet.heeftBarriere()) {
                            int barriere = spelerAanZet.getBarriere();
                            System.out.printf("Na het gooien van een 6 ben je verplicht je barriere te verbreken. " +
                                            "1 van deze pionnen is hierdoor verplicht met %d vakjes vooruit gegaan%n"
                                    , worp);
                            Pion teVerzettenPion = spelers.getPionnenOpVak(barriere).get(0);
                            int nieuwVakNr = getNieuwVakNummer(6, teVerzettenPion);
                            spelerAanZet.verplaats(teVerzettenPion.getPionNummer(), nieuwVakNr);
                            //als er een pion al op staat , dan mag hij die pion opeten.
                            if (ietsOpTeEten(nieuwVakNr)) {
                                for (Pion pion : spelers.getPionnenOpVak(nieuwVakNr)) {
                                    if (pion.getKleur() != spelerAanZet.getKleur()) {
                                        eetPion(pion, teVerzettenPion);
                                        break;
                                    }
                                }
                            }
                        } else {
                            speelNormaal();
                        }
                    }
                    //indien worp niet 6 is, zijn er geen uitzonderingen
                } else {
                    speelNormaal();
                }
                //te testen:
                if (laatstVerzettePion.eindBestemmingBereikt()) {
                    System.out.printf("je pion %d heeft eindbestemming bereikt. Je mag indien mogelijk een pion naar keuze" +
                            "verzetten met 10 vakjes%n", laatstVerzettePion.getPionNummer());
                    worp = 10;
                    speelNormaal();
                }
                printBord();
            } else System.out.println("er zijn geen mogelijke zetten");

        } while (extraBeurt(spelerAanZet.getAantalWorpen()) && !spelerAanZet.isWinnaar());
    }

    //verloop van een normale beurt als er geen uitzonderingen zich voordoen
    public void speelNormaal() {
        toonOpties(spelerAanZet);
        int pionNummerTeVerzetten = kiesOptie(spelerAanZet);
        //verplaats pion:
        Pion teVerzettenPion = spelerAanZet.getPion(pionNummerTeVerzetten);
        int nieuwVakNr = getNieuwVakNummer(worp, teVerzettenPion);
        spelerAanZet.verplaats(pionNummerTeVerzetten, nieuwVakNr);
        //moet er iets opgegeten worden? Zo ja? opeten!
        if (ietsOpTeEten(nieuwVakNr)) {
            for (Pion pion : spelers.getPionnenOpVak(nieuwVakNr)) {
                if (pion.getKleur() != teVerzettenPion.getKleur()) {
                    eetPion(pion, teVerzettenPion);
                    break;
                }
            }
        }
        //Wanneer een pion in het centrum is geraakt, mag een vrij gekozen pion van dezelfde
        //kleur 10 vakjes vooruit gezet worden (als dat mogelijk is)
        Vak nieuwVak = speelbord.getVak(nieuwVakNr);
        if (nieuwVak.isEindBestemming(teVerzettenPion.getKleur())) {
            worp = 10;
            toonOpties(spelerAanZet);
            if (mogelijkeZetten.size() != 0) {
                pionNummerTeVerzetten = kiesOptie(spelerAanZet);
                teVerzettenPion = spelerAanZet.getPion(pionNummerTeVerzetten);
                nieuwVakNr = getNieuwVakNummer(worp, teVerzettenPion);
                spelerAanZet.verplaats(pionNummerTeVerzetten, nieuwVakNr);
            }

        } // einde nieuwe vak is eindbestemming
    }

    public boolean ietsOpTeEten(int nieuwVakNr) {
        Vak nieuwVak = speelbord.getVak(nieuwVakNr);
        //Als het vak zich niet in het speelveld bevindt, kan je niet opeten
        if (!(nieuwVak instanceof SpeelVak)) {
            return false;
        }
        //als het vak een safepoint is en het is geen startvak van de speler aan zet, kan je niet opeten:
        //Als er op het moment dat pion uit nest komt reeds een anderskleurige pion op startvak staat,
        // dan wordt anderskleurige pion opgegeten.
        Kleur kleurSpeler = spelerAanZet.getKleur();
        if ((nieuwVak.isSafePoint()
                && SpeelVak.getStartVakNummer(kleurSpeler) != nieuwVakNr)) {
            return false;
        }
        //je kan opeten als er een anderskleurige pion op het nieuwe vak staat
        List<Pion> pionnenReedsOpVak = spelers.getPionnenOpVak(nieuwVakNr);
        for (Pion pionReedsOpVak : pionnenReedsOpVak) {
            Kleur kleurPionReedsOpvak = pionReedsOpVak.getKleur();
            if (kleurPionReedsOpvak != kleurSpeler) {
                return true;
            }
        }

        return false;
    }

    //Als een speler 6 gooit mag de speler onmiddellijk een tweede beurt spelen. Dit mag
    //maximaal 3 keer na elkaar voorkomen.
    //Wanneer een speler met de dobbelsteen gooit en er is geen pion die met het aantal
    //gegooide ogen op een zinnige plaats kan landen, dan wordt de beurt doorgegeven aan
    //de volgende speler.
    public boolean extraBeurt(int aantalWorpen) {
        if (worp == 6 && aantalWorpen < 3 && mogelijkeZetten.size() != 0) {
            System.out.println("Omdat je 6 gooide, mag je nog een keer");
            return true;
        } else {
            return false;
        }
    }


    //is pion verplaatsbaar? Gaat niet kijken naar welke zet prioriteit krijgt!!!
    public boolean isVerplaatsbaar(int worp, Pion pion) {
        // Als een speler 5 gooit moet die speler een pion uit zijn/haar nest vrijlaten (als er nog
        // pionnen in het nest zitten).
        //als er reeds 2 pionnen op het startvak staan, kan hij geen pion er meer bij zetten. Ze vormen dan barriere
        Kleur kleur = pion.getKleur();
        int starVakNr = SpeelVak.getStartVakNummer(kleur);
        if (pion.getVak() instanceof Nest && worp == 5 && spelerAanZet.getAantalPionnenOpVak(starVakNr) < 2) {
            return true;
        }
        //als het in speelveld zit, is het alleen verplaatsbaar indien er geen barriere is
        if (pion.getVak() instanceof SpeelVak) {
            int huidigVakNummer = pion.getVakNummer();
            int nieuwVakNummer = huidigVakNummer + worp;
            for (Integer safePointVaknr : SpeelVak.getSafePoints()) {
                //als er een barriere tussen huidig vak en nieuw vak staat:
                if (huidigVakNummer < safePointVaknr && safePointVaknr < nieuwVakNummer) {
                    if (isBarriere(safePointVaknr)) {
                        //extra check kleur. pion van andere kleur moet er wel bij kunnen staan
                        //System.out.printf("pion %d kan niet verplaatst worden omdat er een barriere is%n", pion.getPionNummer());
                        return false;
                    }
                }
                //als nieuw vak een barriere is:
                if (nieuwVakNummer == safePointVaknr) {
                    if (isBarriere(safePointVaknr)) {
                        //alleen als pion andere kleur heeft dan pionnen die barriere vormen mag deze erbij komen te staan
                        //alternatief: check of het een barriere is van kleur speler aanzet
                        //if (!spelerAanZet.heeftBarriere(safePointVaknr)
                        return pion.getKleur() != getKleurBarriere(safePointVaknr);
                    }
                }

            }
            return true;
        }
        //als het in landingsbaan zit, alleen verplaatsbaar indien het niet op eindbestemming staat.
        // Wanneer een pion heel het bord is rondgegaan en op de “landingsbaan” staat, kan die
        //pion enkel uitspelen door het exacte aantal ogen te werpen waarmee de pion in het
        //middenveld beland.
        if (pion.getVak() instanceof LandingsBaan) {
            if (pion.getVakNummer() == LandingsBaan.getEindVakNummer(pion.getKleur())) {
                // System.out.printf("pion %d kan niet verplaatst worden omdat deze reeds in centrum staat%n",
                //        pion.getPionNummer());
                return false;

            }
            //   System.out.printf("pion %d kan enkel uitspelen door het exacte aantal ogen te werpen " +
            //                  "waarmee de pion in het middenveld beland%n",
            //        pion.getPionNummer());
            return pion.getVakNummer() + worp <= LandingsBaan.getEindVakNummer(pion.getKleur());
        }
        //   System.out.printf("pion %d zit in nest en je hebt geen 5 gegooit of er staan al 2 pionnen op startvak%n", pion.getPionNummer());
        return false;
    }

    //bekijkt obv vaknummer of het vak een barriere is
    public boolean isBarriere(int vakNummer) {
        //het vak moet een safepoint zijn
        Vak vak = speelbord.getVak(vakNummer);
        if (vak.isSafePoint()) {
            //het safepoint moet meer dan 1 pion bevatten
            List<Pion> pionnenOpVak = spelers.getPionnenOpVak(vakNummer);
            //haal de kleur op van alle pionnen op vak indien er 2 of meerdere pionnen opstaan
            // de eerste 2 pionnen op vak moeten van dezelfde kleur zijn
            if (pionnenOpVak.size() >= 2) {
               Pion eerstePion=pionnenOpVak.get(0);
               Pion tweedePion=pionnenOpVak.get(1);
               return eerstePion.getKleur().equals(tweedePion.getKleur());
            }

        }
        //zoniet, geen barriere
        return false;
    }

    //welke kleur van pionnen vormen een barrier op dat vak
    public Kleur getKleurBarriere(int vakNummer) {
        if (isBarriere(vakNummer)){
            List<Pion> pionnenOpVak = spelers.getPionnenOpVak(vakNummer);
            return pionnenOpVak.get(0).getKleur();
        }
        return null;
    }

    //deze methode vult de list "mogelijke zetten" op voor speler
    public void setMogelijkeZetten(Speler speler) {
        List<Pion> pionnen = speler.getPionnen();
        mogelijkeZetten.clear();
        //voeg de pionnen toe die verplaatsbaar zijn:
        for (Pion pion : pionnen) {
            if (isVerplaatsbaar(worp, pion)) {
                mogelijkeZetten.add(pion);
            }
        }
    }

    //telt het aantal zetten die mogelijk zijn voor de speler obv worp
    //deze methode vult ook de list "mogelijke zetten" op
    public int getAantalZetten(Speler speler) {
        setMogelijkeZetten(speler);
        return mogelijkeZetten.size();
    }

    //toont de opties (mogelijke zetten) van speler
    //Opm.: parameter Speler niet echt nodig want speleraanzet wordt bijgehouden, maar leest beter + veiliger
    public void toonOpties(Speler speler) {
        int aantalZetten = getAantalZetten(speler);
        if (aantalZetten != 0) {

            System.out.println("Dit zijn je mogelijke zetten:");
            for (Pion pion : mogelijkeZetten) {
                System.out.printf("%s kan je verplaatsen naar vak %d%n", pion, getNieuwVakNummer(worp, pion));
            }
        } else {
            System.out.println("Er zijn geen pionnen die je kan verzetten");
        }
    }

    //geeft pionnummer terug die je wil verzetten
    //als je maar 1 mogelijke zet hebt, wordt pionnummer automatisch gekozen
    //paramter Speler niet echt nodig want speleraanzet wordt bijgehouden, maar leest beter + veiliger
    public int kiesOptie(Speler speler) {
        int aantalZetten = getAantalZetten(spelerAanZet);
        if (aantalZetten == 1) {
            //als je maar 1 keuze hebt, neemt dan de pionnummer van enige mogelijkse keuze
            return mogelijkeZetten.get(0).getPionNummer();
        } else {
            boolean geldigePionNummer = true;
            int pionNummer = 0;
            do {
                System.out.println("Geef de nummer van je pion die je wil verzetten");
                try {
                    pionNummer = keyboard.nextInt();
                    keyboard.nextLine();
                    Pion pion = speler.getPion(pionNummer);
                    if (!mogelijkeZetten.contains(pion)) {
                        geldigePionNummer = false;
                        throw new IllegalArgumentException("geen geldig pionnummer");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } while (!geldigePionNummer);
            return pionNummer;
        }
    }

    // haalt het nieuw vaknummer van pion op obv worp.
    public int getNieuwVakNummer(int worp, Pion pion) {
        Kleur kleur = pion.getKleur();
        int huidigVakNummer = pion.getVakNummer();
        //als pion in nest zit
        if (pion.getVak() instanceof Nest) {
            //als er al 2 pionnen staan op startvak kan er geen nieuwe pion bijkomen,want vormen dan barriere.
            if (spelerAanZet.getAantalPionnenOpVak(SpeelVak.getStartVakNummer(kleur)) < 2) {
                return SpeelVak.getStartVakNummer(kleur);
            }
        }
        //als pion in speelveld zit:
        if (pion.getVak() instanceof SpeelVak) {
            int index = speelbord.getIndexInPad(huidigVakNummer, kleur);
            int nieuweIndex = index + worp;
            return speelbord.getPad(kleur).get(nieuweIndex);
        }
        //als pion in landingsbaan zit:
        if (pion.getVak() instanceof LandingsBaan) {
            //gooi exact aantal ogen om in middenveld(=eindvaknummer Landingsbaan) te belanden
            if (huidigVakNummer + worp <= LandingsBaan.getEindVakNummer(kleur)) {
                return huidigVakNummer + worp;
            }
        }
        return 0;
    }

    // zet pion terug naar zijn nest
    //andere pion mag 20 vakjes vooruit
    public void eetPion(Pion pionReedsOpVak, Pion pionTeVerzetten) {

        Kleur kleur = pionReedsOpVak.getKleur();
        Speler spelerVanPion = spelers.getSpeler(kleur);
        //print geldige spelregel af:
        System.out.printf("ai %s, je pion %d wordt opgegeten door pion %d van speler %s%n",
                spelerVanPion.getNaam(), pionReedsOpVak.getPionNummer(), pionTeVerzetten.getPionNummer(),
                spelerAanZet.getNaam());
        // pion moet terug naar beschikbaar vak in nest:
        spelerVanPion.pionNaarNest(pionReedsOpVak);
        // te verzetten pion mag 20 vakjes vooruit indien mogelijk
        if (isVerplaatsbaar(20, pionTeVerzetten)) {
            System.out.printf("%s, omdat je pion %d een andere pion heeft opgegeten, mag deze nu" +
                    "20 vakjes vooruit%n", spelerAanZet.getNaam(), pionTeVerzetten.getPionNummer());
            int nieuwVakNr = getNieuwVakNummer(20, pionTeVerzetten);
            int pionNummer = pionTeVerzetten.getPionNummer();
            spelerAanZet.verplaats(pionNummer, nieuwVakNr);
        }

    }

    //deze methode geeft volgende speler terug en zet boolean "aanzet"=false voor huidige speler + nieuwe speler "aanzet"=true
    public void kiesVolgendeSpeler() {
        Speler huidigeSpeler = spelerAanZet;
        huidigeSpeler.setAanZet(false);
        huidigeSpeler.resetAantalWorpen();
        Speler volgendeSpeler;
        List<Speler> spelersList = spelers.getSpelers();
        if (spelersList.indexOf(huidigeSpeler) != spelersList.size() - 1) {
            int index = spelersList.indexOf(huidigeSpeler);
            volgendeSpeler = spelersList.get(index + 1);
        } else {
            volgendeSpeler = spelersList.get(0);
        }
        volgendeSpeler.setAanZet(true);
        spelerAanZet = volgendeSpeler;
    }

    public void printBord() {
        String[][] bordMetPion = speelbord.getBordZonderPion();
        for (Speler speler : spelers.getSpelers()) {
            for (Pion pion : speler.getPionnen()) {
                Vak vakPion = speelbord.getVak(pion.getVakNummer());
                // probleem als er een pion minder komt op vak, nog niet gezocht naar oplossing om x-en af te trekken:
                // geen goede oplossing maar moeten dit toch anders doen met user interface?
                switch (bordMetPion[vakPion.getX()][vakPion.getY()]) {
                    case "x\t":
                        bordMetPion[vakPion.getX()][vakPion.getY()] = "xx\t";
                        break;
                    case "xx\t":
                        bordMetPion[vakPion.getX()][vakPion.getY()] = "xxx\t";
                        break;
                    case "xxx\t":
                        bordMetPion[vakPion.getX()][vakPion.getY()] = "xxxx\t";
                        break;
                    default:
                        bordMetPion[vakPion.getX()][vakPion.getY()] = "x\t";
                        break;
                }
            }
        }
        speelbord.print(bordMetPion);
        // maak terug leeg
        for (Speler speler : spelers.getSpelers()) {
            for (Pion pion : speler.getPionnen()) {
                Vak vakPion = speelbord.getVak(pion.getVakNummer());
                bordMetPion[vakPion.getX()][vakPion.getY()] = "";
            }
        }
    }

    public boolean isEindeSpel() {
        if (spelerAanZet.isWinnaar()) {
            return eindeSpel = true;
        }
        return eindeSpel = false;
    }

    public void eindeSpel() {
        HighScore.voegToe(spelerAanZet);
        HighScore.toonHighscores();
    }

    public void cheatKnop(){
        spelerAanZet.setIsWinnaar();
    }
}
