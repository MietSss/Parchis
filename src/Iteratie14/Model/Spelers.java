package Iteratie14.Model;


import java.util.*;

public class Spelers {
    public final static Scanner keyboard = new Scanner(System.in);
    public static final int MAX_SPELERS = 4;
    private final List<Speler> spelers;
    private int aantalSpelers;
    private final Speelbord speelbord;
    private final List<Pion> allePionnen;
    private final List<Kleur> kleurenSpelers;
    private final List<String> namenSpelers;


    public Spelers(Speelbord speelbord) {
        spelers = new ArrayList<>();
        this.speelbord = speelbord;
        this.aantalSpelers = 0;
        allePionnen = new ArrayList<>();
        kleurenSpelers = new ArrayList<>();
        namenSpelers = new ArrayList<>();
    }

    public int getAantalSpelers() {
        aantalSpelers = spelers.size();
        return aantalSpelers;
    }

    public List<Speler> getList() {
        return spelers;
    }

    public boolean laatsteSpelerInLijst(Speler speler){
        int index=0;
        for (int i = 0; i < spelers.size(); i++) {
            if(speler.equals(spelers.get(i))){
               index=i;
            }
        }
        return index==spelers.size()-1;
    }

    public int getSize(){
        return spelers.size();
    }

    public Speler getSpeler(int index){
        return spelers.get(index);
    }


    public Speler getSpelerAanZet() {
        Speler spelerAanZet = null;
        for (Speler speler : spelers) {
            if (speler.isAanZet()) {
                spelerAanZet = speler;
            }
        }
        return spelerAanZet;
    }

    public Speler getSpeler(Kleur kleur) {
        for (Speler speler : spelers) {
            if (speler.getKleur() == kleur) {
                return speler;
            }
        }
        return null;
    }

    public void setAantalSpelers(int aantalSpelers) {
        this.aantalSpelers = aantalSpelers;
    }

    public List<Kleur> getKleurenSpelers() {
        for (Speler speler : spelers) {
            kleurenSpelers.add(speler.getKleur());
        }
        if (kleurenSpelers.size() == 0) {
            throw new ParchisException("Geen kleuren van spelers beschikbaar");
        }
        return kleurenSpelers;
    }

    public List<String> getNamenSpelers() {
        for (Speler speler : spelers) {
            namenSpelers.add(speler.getNaam());
        }
        {
            if (namenSpelers.size() == 0) {
                throw new ParchisException("Geen namen van spelers beschikbaar");
            }
            return namenSpelers;
        }
    }

    /*
    // geeft terug met hoeveel spelers je gaat spelen.
     public int setAantalSpelers() {
        boolean correctAantal = false;
        do {
          //  System.out.println("Met hoeveel spelers wil je spelen? Het minimum is 2, de maximum 4:");
            try {
                aantalSpelers = aantalSpelers;
            } catch (Exception e) {
                e.printStackTrace();
                throw new ParchisException("De input is geen cijfer. Probeer opnieuw");

            }

            //indien ingegeven cijfer geen cijfer is 2..4: print foutmelding en probeer opnieuw.
            if (aantalSpelers > 1 && aantalSpelers <= MAX_SPELERS) {
                correctAantal = true;

            } else {
                throw new ParchisException("Er ging iets mis. Het cijfer moet tussen de 2 en de 4 liggen. Probeer opnieuw.");
            }
            keyboard.nextLine();
        } while (!correctAantal);
        //nextInt verwerkt blijkbaar de Enter niet. Dus terug leesbuffer leeg maken.
        return aantalSpelers;
    }

 */


    // maakt spelers aan voor het aantal dat je krijgt bij getAantalSpelers.
    public void voegSpelersToe(Map<Integer, String> gekozenKleuren, Map<Integer, String> gekozenNamen) {

        Map<String, String> spelersMap=new HashMap<>();
        if(gekozenKleuren!=null & gekozenNamen!=null) {
            for (Integer nrKleur : gekozenKleuren.keySet()) {
                for (Integer nrNaam : gekozenNamen.keySet()) {
                    if (nrNaam.equals(nrKleur)) {
                        spelersMap.put(gekozenKleuren.get(nrKleur), gekozenNamen.get(nrNaam));
                    }
                }
            }
            for (String kleur : spelersMap.keySet()) {
                Kleur kleurEnum = Kleur.valueOf(kleur);
                String naam = spelersMap.get(kleur);
                Speler speler = new Speler(kleurEnum, naam, speelbord);
                spelers.add(speler);
            }
            //vul lijst pionnen:
            for (Speler speler : spelers) {
                allePionnen.addAll(speler.getPionnen());
            }
        }
    }

    public String toonSpelers() {
        StringBuilder builder = new StringBuilder();
        for (Speler speler : getList()) {
            builder.append(speler);
        }
        return "Dit zijn de spelers: \n" + builder.toString();
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
