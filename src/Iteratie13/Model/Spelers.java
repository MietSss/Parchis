package Iteratie13.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Spelers {
    public final static Scanner keyboard = new Scanner(System.in);
    public final int MAX_SPELERS = 4;
    private final List<Speler> spelers;
    private final static Kleur[] kleuren = {Kleur.ROOD, Kleur.BLAUW, Kleur.GEEL, Kleur.GROEN};
    private int aantalSpelers;
    private final Speelbord speelbord;
    private final List<Pion> allePionnen;


    public Spelers(Speelbord speelbord) {
        spelers = new ArrayList<>();
        this.speelbord = speelbord;
        setAantalSpelers();
        voegSpelersToe();
        toonSpelers();
        allePionnen = new ArrayList<>();
        for (Speler speler : spelers) {
            allePionnen.addAll(speler.getPionnen());
        }
    }

    public List<Speler> getSpelers() {
        return spelers;
    }


    public Speler getSpelerAanZet() {
        Speler spelerAanZet=null;
        for (Speler speler : spelers) {
            if (speler.isAanZet()) {
                spelerAanZet = speler;
            }
        }
        return spelerAanZet;
    }

    public Speler getSpeler(Kleur kleur){
        for (Speler speler : spelers) {
           if(speler.getKleur()==kleur){
               return speler;
           }
        }
        return null;
    }

    // geeft terug met hoeveel spelers je gaat spelen.
    public void setAantalSpelers() {
        boolean correctAantal = false;
        do {
            System.out.println("Met hoeveel spelers wil je spelen? Het minimum is 2, de maximum 4:");
            try {
                aantalSpelers = keyboard.nextInt();
            } catch (Exception e) {
                e.printStackTrace();
                correctAantal = false;
            }

            //indien ingegeven cijfer geen cijfer is 2..4: print foutmelding en probeer opnieuw.
            if (aantalSpelers > 1 && aantalSpelers <= MAX_SPELERS) {
                correctAantal = true;

            } else {
                System.out.println("Er ging iets mis. Het cijfer moet tussen de 2 en de 4 liggen. Probeer opnieuw.");
            }
            keyboard.nextLine();
        } while (!correctAantal);
        //nextInt verwerkt blijkbaar de Enter niet. Dus terug leesbuffer leeg maken.

    }

    // maakt spelers aan voor het aantal dat je krijgt bij getAantalSpelers.
    public void voegSpelersToe()
    {
        for (int i = 0; i < aantalSpelers; i++) {
            //om toe te voegen moet je een naam hebben:
            String naam;
            do {
                System.out.printf("Hallo speler %d. Geef een naam aan je speler:%n", i + 1);
                naam = keyboard.nextLine();
                //isBlank gekozen omdat een spatie ook niet geldig mag zijn.
                // Hier is String lengte wel meer dan 0 en zou dus bij isEmpty false geven
            } while (naam.isBlank());
            //daarnaast moet je ook een kleur kiezen
            //toon alleen de kleuren die nog beschikbaar zijn
            System.out.println("Met welke kleur wil je spelen? :" + getBeschikbareKleuren());
            boolean beschikbaar;
            do {
                String kleur = keyboard.nextLine();
                if (isKleurBeschikbaar(kleur)) {
                    beschikbaar = true;
                    Kleur kleurEnum = Kleur.valueOf(kleur.toUpperCase());
                    //in klasse Speler worden de 4 pionnen aangemaakt met kleur van speler en in hun nest gestoken.
                    Speler speler = new Speler(kleurEnum, naam, speelbord);
                    spelers.add(speler);
                } else {
                    beschikbaar = false;
                    System.out.println("Deze kleur is geen beschikbare kleur. Kies één uit deze lijst: " +
                            getBeschikbareKleuren());
                }
            } while (!beschikbaar);
        }
    }

    //speler mag alleen kleur kiezen die beschikbaar is.
    public boolean isKleurBeschikbaar(String kleur) {
        //kleur is beschikbaar als er nog geen speler deze kleur heeft
        for (Speler speler : spelers) {
            if (speler.getKleur().name().equalsIgnoreCase(kleur)) {
                return false;
            }
        }
        //als de kleur nog niet toegewezen is aan een speler moet gekeken worden of het gaat om 1 van de 4 kleuren
        for (Kleur mogelijkeKleur : kleuren) {
            if (kleur.equalsIgnoreCase(mogelijkeKleur.name())) {
                return true;
            }
        }
        return false;
    }

    // toont de kleuren die nog beschikbaar zijn
    public String getBeschikbareKleuren() {
        StringBuilder builder = new StringBuilder();
        for (Kleur kleur : kleuren) {
            if (isKleurBeschikbaar((kleur.name()))) {
                builder.append(kleur).append(" ");
            }
        }
        return builder.toString();
    }

    public void toonSpelers() {
        StringBuilder builder = new StringBuilder();
        for (Speler speler : getSpelers()) {
            builder.append(speler);
        }
        System.out.println("Dit zijn de spelers: \n" + builder);
    }

    //we gebruiken een lijst omdat er meerdere pionnen op eenzelfde vak kunnen staan.
    public List<Pion> getPionnenOpVak(int vakNummer) {
        List<Pion> pionnenOpVak = new ArrayList<>();
        for (Pion pion : allePionnen) {
            if (pion.getVakNummer() == vakNummer) {
                pionnenOpVak.add(pion);
            }
        }
        return pionnenOpVak;
    }



}
