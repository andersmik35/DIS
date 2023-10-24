package lektion17.lowTransparancy;

public class Person {
    String cpr;
    String navn;
    String bynavn;
    int loen;
    int skatteprocent;

    public String getCpr() {
        return cpr;
    }

    public String getNavn() {
        return navn;
    }

    public String getBynavn() {
        return bynavn;
    }

    public int getLoen() {
        return loen;
    }

    public int getSkatteprocent() {
        return skatteprocent;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public void setBynavn(String bynavn) {
        this.bynavn = bynavn;
    }

    public void setLoen(int loen) {
        this.loen = loen;
    }

    public void setSkatteprocent(int skatteprocent) {
        this.skatteprocent = skatteprocent;
    }
}


