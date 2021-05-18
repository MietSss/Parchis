package Iteratie13.Model;

public class Pion {
    private final Kleur kleur;
    private final int pionNummer;
    //via vaknummer kunnen we x en y positie oproepen van klasse Vak.
    private int vakNummer;
    private Vak vak;

// pionnen bij aanmaak standaard op niet verplaatsbaar zetten

    public Pion(Kleur kleur, int pionNummer, Vak vak) {
        this.kleur = kleur;
        this.pionNummer = pionNummer;
        this.vak = vak;
        vakNummer = vak.getVakNummer();
    }


    public int getPionNummer() {
        return pionNummer;
    }

    public Vak getVak() {
        return vak;
    }

    public void setVak(Vak vak) {
        this.vak = vak;
        this.vakNummer = vak.getVakNummer();
    }

    public int getVakNummer() {
        return vak.getVakNummer();
    }

    public Kleur getKleur() {
        return kleur;
    }

    //kijkt of pion in eindbestemming is geraakt:
    public boolean eindBestemmingBereikt() {
        return vak.isEindBestemming(kleur);

    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Pion nummer ").append(pionNummer);
        if (vak instanceof Nest) {
            builder.append(" : momenteel in zijn nest");
        }
        if (vak instanceof SpeelVak) {
            builder.append(" : momenteel in vak ").append(vakNummer);
        }
        if (vak instanceof LandingsBaan) {
            int nummer = vakNummer - LandingsBaan.getStartVakNummer(kleur);
            builder.append(" : momenteel op vak").append(nummer).append("van de landingsbaan");
        }
        return builder.toString();
    }
}
