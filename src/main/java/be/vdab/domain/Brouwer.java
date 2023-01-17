package be.vdab.domain;

public class Brouwer {
    private final long id;
    private final String naam;
    private final String adres;
    private final int postcode;
    private final String gemeente;
    private final int omzet;

    public Brouwer(long id, String naam, String adres, int postcode, String gemeente, int omzet) {
        this.id = id;
        this.naam = naam;
        this.adres = adres;
        this.postcode = postcode;
        this.gemeente = gemeente;
        this.omzet = omzet;
    }

    @Override
    public String toString() {
        return "Brouwer{" +
                "id=" + id +
                ", naam='" + naam + '\'' +
                ", adres='" + adres + '\'' +
                ", postcode=" + postcode +
                ", gemeente='" + gemeente + '\'' +
                ", omzet=" + omzet +
                '}';
    }
}
