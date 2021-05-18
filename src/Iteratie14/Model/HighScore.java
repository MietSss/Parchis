package Iteratie14.Model;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static Iteratie14.Model.Spelers.keyboard;

public final class HighScore {
    private static Map<String, Integer> highscoreMap = new LinkedHashMap<>();
    private static final Path HIGHSCORE_PAD = Paths.get("resources/other/highscores.txt");
    private static final File highscoreFile = new File(HIGHSCORE_PAD.toString());
    private String instructieHighscore;

    public HighScore() {
        //als file nog niet bestaat (bvb eerste keer dat je spel uit speel: maak nieuwe file aan)
        //maakt alleen een file object aan.
        if (!Files.exists(HIGHSCORE_PAD)) {
            try {
                Files.createFile(HIGHSCORE_PAD);
                String headers = String.format("HIGHSCORES TOP 5:%n%-10s \t %s%n---------- \t --------------%n", "Naam", "Aantal Beurten");
                Files.write(HIGHSCORE_PAD, headers.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                throw new ParchisException("error in aanmaken van highscore file");
            }
        }
    }

    public void readHighscores() {
        highscoreMap.clear();
        if (Files.exists(HIGHSCORE_PAD)) {
            try {
                List<String> lijnen = Files.readAllLines(HIGHSCORE_PAD);
                //eerste 3 lijnen van bestand zijn titel en header, die slaan we over.
                //size -1, telt laatste enter mee??
                for (int i = 3; i < lijnen.size(); i++) {
                    String[] naamScore = lijnen.get(i).split("\\s+");
                    String naam = naamScore[0];
                    int score = Integer.parseInt(naamScore[1]);
                    highscoreMap.put(naam, score);
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new ParchisException("error in het lezen van highscore file");
            }
        }
        if (highscoreMap != null) {
            sortByValues();
        }
    }


    public void sortByValues() {
        List<Integer> listAantalBeurten = new ArrayList<>(highscoreMap.values());
        List<String> listNamen = new ArrayList<>(highscoreMap.keySet());
        //sorteer van klein naar groot
        Collections.sort(listAantalBeurten);
        //sorteer namen alfabetisch
        Collections.sort(listNamen);
        // maak map aan om in op te slaan
        HashMap<String, Integer> sortedMap = new LinkedHashMap<>();

        //ga alle scores af (van klein naar groot)
        for (int aantalBeurten : listAantalBeurten) {
            Iterator<String> keyIt = listNamen.iterator();
            //ga alle namen af (alfabetisch)
            while (keyIt.hasNext()) {
                String naam = keyIt.next();
                // score die bij naam hoort
                Integer comp1 = highscoreMap.get(naam);
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
        highscoreMap.clear();
        highscoreMap = sortedMap;
    }


    public void voegToe(Speler speler, String naam) {
        int aantalBeurten = speler.getAantalBeurten();
        String naamSpelerLaagsteScore = "";
        int hoogsteAantalBeurten = Integer.MAX_VALUE;
        //lees de highscore in van txt bestand
        readHighscores();

        if (highscoreMap.size() > 0) {
            hoogsteAantalBeurten = Collections.max(highscoreMap.values());
            for (String s : highscoreMap.keySet()) {
                if (highscoreMap.get(s) == hoogsteAantalBeurten) {
                    naamSpelerLaagsteScore = s;
                }
            }
        }
        // instructieHighscore=String.format("Proficiat %s je hebt het spel uitgespeeld in %d beurten!%n", speler.getNaam(), speler.getAantalBeurten());
        //er staat al 1 speler in top 5, maar er is nog plaats in top lijst
        if (highscoreMap.size() < 5) {
            //    instructieHighscore +="Met je aantal beurten behoor je in de top 5.";
            if (highscoreMap.containsKey(naam)) {
                throw new ParchisException("Deze naam bestaat al, kies een andere naam");
            } else {
                highscoreMap.put(naam, aantalBeurten);
                save();
            }

        } else {
            if (hoogsteAantalBeurten > aantalBeurten) {
                // verwijder eerst speler met laagste score=hoogst aantal beurten
                highscoreMap.remove(naamSpelerLaagsteScore);
                //  System.out.println("Je hebt dit gedaan in zo weinig beurten dat je je naam mag toevoegen aan de top 5.");
                //  String naam;

                if (highscoreMap.containsKey(naam)) {
                    throw new ParchisException("Deze naam bestaat al, kies een andere naam");
                } else{
                    highscoreMap.put(naam, aantalBeurten);
                    save();
            }
        } else{
            instructieHighscore=String.format("Om in de top 5 terecht te komen zal je het spel in minder beurten moeten uitspelen dan %s. " +
                            "%s deed dit in %d beurten. Jij deed dit in %d beurten%n",
                    naamSpelerLaagsteScore, naamSpelerLaagsteScore, hoogsteAantalBeurten, aantalBeurten);
        }
    }

}

    public void clearFile() {
        try {
            //maak tempfile op exact pad als originele file
            Path tempPath = Paths.get("resources/other/highscoreTemp.txt");
            Files.createFile(tempPath);
            File tempFile = tempPath.toFile();

            //lezen op huidige file
            // schrijven op tijdelijke file
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
            //schrijf alleen eerste 3 lijnen naar temp file
            List<String> lijnen = Files.readAllLines(HIGHSCORE_PAD);
            for (int i = 0; i < 3; i++) {
                pw.println(lijnen.get(i));
                pw.flush();
            }
            pw.close();

            //delete de originele file
            if (highscoreFile.delete()) {
                System.out.println("kan originele file niet deleten");
            }

            //hernoem de tempfile naar highscoreFile
            if (tempFile.renameTo(highscoreFile)) {
                System.out.println("kan temp file niet renamen naar originele file");
            }
        } catch (FileNotFoundException | FileAlreadyExistsException ex) {
            System.out.println("original file niet gevonden of temp file bestaat al");
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new ParchisException("error bij het saven van de score in de highscore");
        }

    }

    public void save() {
        //haal alle lijnen uit file
        clearFile();
        //vul file opnieuw op obv Map highscoreMap, zet append op true, anders overwrite
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(highscoreFile, true));
            sortByValues();
            //zet score om naar string:
            Map<String, String> highscoreMap2 = new LinkedHashMap<>();
            for (String naam : highscoreMap.keySet()) {
                int scoreInt = highscoreMap.get(naam);
                String scoreString = Integer.toString(scoreInt);
                highscoreMap2.put(naam, scoreString);
            }
            //schrijf elke lijn naar de highscoreFile
            for (String naam : highscoreMap2.keySet()) {
                pw.format("%-10s \t %-3s%n", naam, highscoreMap2.get(naam));
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ParchisException("error saving de highscore file");
        }
    }
}
/*
class testHighScores {
    public static void main(String[] args) throws IOException {
        HighScore highScore=new HighScore();
        HighScore.readHighscores();

        Speler speler1 = new Speler(Kleur.ROOD, "Ellen", new Speelbord());
        Speler speler2 = new Speler(Kleur.ROOD, "Els", new Speelbord());
        Speler speler3 = new Speler(Kleur.GEEL, "Elke", new Speelbord());
        Speler speler4 = new Speler(Kleur.ROOD, "Elien", new Speelbord());
        Speler speler5 = new Speler(Kleur.GROEN, "Miet", new Speelbord());
        Speler speler6 = new Speler(Kleur.GEEL, "Miet", new Speelbord());
        Speler speler7 = new Speler(Kleur.GROEN, "Ellen", new Speelbord());

        speler1.setAantalBeurten(20);
        speler2.setAantalBeurten(10);
        speler3.setAantalBeurten(20);
        speler4.setAantalBeurten(50);
        speler5.setAantalBeurten(30);
        speler6.setAantalBeurten(52);
        speler7.setAantalBeurten(22);

        HighScore.voegToe(speler1);
        HighScore.voegToe(speler2);
        HighScore.voegToe(speler3);
        HighScore.voegToe(speler4);
        HighScore.voegToe(speler5);
        HighScore.voegToe(speler6);
        HighScore.voegToe(speler7);





        /*
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
    }
 */



