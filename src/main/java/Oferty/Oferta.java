package Oferty;

import Produkty.Produkt;

public abstract class Oferta {
    protected int idWystawiajacego;
    protected Produkt.Typ produkt;
    protected final int jakość;
    protected int liczba;

    public Oferta(Produkt.Typ produkt, int id, int liczba, int jakość) {
        this.produkt = produkt;
        this.liczba = liczba;
        this.idWystawiajacego = id;
        this.jakość = jakość;
    }

    public int idWystawiajacego() {
        return idWystawiajacego;
    }

    public Produkt.Typ produkt() {
        return produkt;
    }

    public int jakość() {
        return jakość;
    }

    public int liczba() {
        return liczba;
    }

    public void kup(int ile) {
        if (ile <= liczba)
            liczba -= ile;
    }

    @Override
    public String toString() {
        StringBuilder wynik = new StringBuilder();
        wynik.append("{ID: ").append(idWystawiajacego).append("}, ");
        wynik.append("{PRODUKT: ").append(produkt).append("}, ");
        wynik.append("{JAKOŚĆ: ").append(jakość).append("}, ");
        wynik.append("{LICZBA: ").append(liczba).append("}");
        return wynik.toString();
    }
}
