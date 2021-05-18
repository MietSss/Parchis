package iteratie12.model;

import java.util.*;

import static iteratie12.model.Spelers.keyboard;

public final class HighScore {
    private static Map<String, Integer> highscores = new LinkedHashMap<>();


    public static void sortByValues() {
        List<Integer> mapAantalBeurten = new ArrayList<>(highscores.values());
        List<String> mapNamen = new ArrayList<>(highscores.keySet());
        //sorteer van klein naar groot
        Collections.sort(mapAantalBeurten);
        //sorteer namen alfabetisch
        Collections.sort(mapNamen);
        // maak map aan om in op te slaan
        HashMap<String, Integer> sortedMap = new LinkedHashMap<>();

        Iterator<Integer> valueIt = mapAantalBeurten.iterator();
        //ga alle scores af (van klein naar groot)
        while (valueIt.hasNext()) {
            int aantalBeurten = valueIt.next();
            Iterator<String> keyIt = mapNamen.iterator();
            //ga alle namen af (alfabetisch)
            while (keyIt.hasNext()) {
                String naam = keyIt.next();
                // score die bij naam hoort
                Integer comp1 = highscores.get(naam);
                //hoogste score (= laagste waarde)
                Integer comp2 = aantalBeurten;
                //selecteer naam die bij aantalBeurten hoort:
                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(naam, aantalBeurten);
                    //eens gevonden ga uit loop van namen
                    break;
                }
            }
        }
        highscores.clear();
        highscores = sortedMap;
    }

    public static void voegToe(Speler speler) {
        int aantalBeurten = speler.getAantalBeurten();
        String naamSpelerLaagsteScore = "";
        int hoogsteAantalBeurten = Integer.MAX_VALUE;
        //vul op wie staat op laatste plaats?
        if (highscores.size() > 0) {
            hoogsteAantalBeurten = Collections.max(highscores.values());
            for (String s : highscores.keySet()) {
                if (highscores.get(s) == hoogsteAantalBeurten) {
                    naamSpelerLaagsteScore = s;
                }
            }
        }
        System.out.printf("Proficiat %s je hebt het spel uitgespeeld in %d beurten!%n", speler.getNaam(), speler.getAantalBeurten());
        //er staat al 1 speler in top 5, maar er is nog plaats in top lijst
        if (highscores.size() < 5) {
            System.out.println("Met je aantal beurten behoor je in de top 5.");
            String naam = "";
            //moet nog als exception worden omgevormd en getoond in error boodschap op scherm.
            do {
                System.out.println("Geef een naam in");
                naam = keyboard.nextLine();
                if (highscores.containsKey(naam)) {
                    System.out.println("Deze naam bestaat al, kies een andere naam");
                }
            } while (highscores.containsKey(naam));
            highscores.put(naam, aantalBeurten);

        } else if (highscores.size() >= 5) {
            if (hoogsteAantalBeurten > aantalBeurten) {
                // verwijder eerst speler met laagste score=hoogst aantal beurten
                highscores.remove(naamSpelerLaagsteScore);
                System.out.println("Je hebt dit gedaan in zo weinig beurten dat je je naam mag toevoegen aan de top 5.");
                System.out.println("Geef een naam in");
                String naam = keyboard.nextLine();
                highscores.put(naam, aantalBeurten);
            } else {
                System.out.printf("Om in de top 5 terecht te komen zal je het spel in minder beurten moeten uitspelen dan %s. " +
                                "%s deed dit in %d beurten. Jij deed dit in %d beurten%n",
                        naamSpelerLaagsteScore, naamSpelerLaagsteScore, hoogsteAantalBeurten, aantalBeurten);
            }


        }
    }


    public static void toonHighscores(){
        sortByValues();
        System.out.printf("HIGHSCORES TOP 5:%n");
        System.out.println();
        System.out.printf("%-10s \t %s%n", "Naam", "Aantal Beurten");
        System.out.printf("---------- \t --------------%n");
        for (String s : highscores.keySet()) {
            System.out.printf("%-10s \t %-3d%n", s, highscores.get(s));
        }
    }
}

/*class testHighScores {
    public static void main(String[] args) {
        HighScore.getHighscores().clear();
        Speler speler1 = new Speler(Kleur.ROOD, "Ellen", new Speelbord());
        Speler speler2 = new Speler(Kleur.ROOD, "Els", new Speelbord());
        Speler speler3 = new Speler(Kleur.GEEL, "Elke", new Speelbord());
        Speler speler4 = new Speler(Kleur.ROOD, "Elien", new Speelbord());
        Speler speler5 = new Speler(Kleur.GROEN, "Miet", new Speelbord());
        Speler speler6 = new Speler(Kleur.GEEL, "Miet", new Speelbord());
        Speler speler7 = new Speler(Kleur.GROEN, "Ellen", new Speelbord());

        speler1.setAantalBeurten(20);
        speler1.setAanZet(true);
        HighScore.voegToe(speler1);
        speler1.setAanZet(false);
        speler2.setAantalBeurten(10);
        speler2.setAanZet(true);
        HighScore.voegToe(speler2);
        speler2.setAanZet(false);
        speler3.setAantalBeurten(20);
        speler3.setAanZet(true);
        HighScore.voegToe(speler3);
        speler3.setAanZet(false);
        speler4.setAantalBeurten(50);
        speler4.setAanZet(true);
        HighScore.voegToe(speler4);
        speler4.setAanZet(false);
        speler5.setAantalBeurten(30);
        speler5.setAanZet(true);
        HighScore.voegToe(speler5);
        speler5.setAanZet(false);
        speler6.setAantalBeurten(52);
        speler6.setAanZet(true);
        HighScore.voegToe(speler6);
        speler6.setAanZet(false);
        speler7.setAantalBeurten(22);
        speler7.setAanZet(true);
        HighScore.voegToe(speler7);
        speler7.setAanZet(false);
        HighScore.toonHighscores();
    }
}*/
