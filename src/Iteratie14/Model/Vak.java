package Iteratie14.Model;



public abstract  class Vak {
    private Kleur kleur;
    private int vakNummer;
    private int x;
    private int y;
    private boolean isSafePoint;
    private boolean isEindBestemming;

    public Vak() {
    }

    public Vak(int vakNummer, int y, int x) {
        this.vakNummer=vakNummer;
        this.x=x;
        this.y=y;
        if(SpeelVak.isStartVakNummer(vakNummer)){
            this.kleur=SpeelVak.kleurStartVakNummer(vakNummer);
        }else{
            this.kleur=Kleur.BLANCO;
        }
    }


    public Vak(Kleur kleur, int vakNummer, int y, int x){
        this.kleur=kleur;
        this.vakNummer=vakNummer;
        this.x=x;
        this.y=y;
        this.isSafePoint=false;
        this.isEindBestemming=false;
    }


    public Kleur getKleur() {
        return kleur;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public int getVakNummer() {
        return vakNummer;
    }


    public boolean isSafePoint() {
        return isSafePoint;
    }

    public boolean isEindBestemming(Kleur kleur) {
        return isEindBestemming;
    }

    @Override
    public String toString() {
        return "Vak{" +
                "kleur=" + kleur +
                ", vakNummer=" + vakNummer +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

}
