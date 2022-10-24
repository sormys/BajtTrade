package Oferty;

import Produkty.Produkt;

public class OfertaSpekulanta extends Oferta {
    private double cenaZaSztuke;

    public OfertaSpekulanta(Produkt.Typ produkt, int id, int liczba, int jakość, double cenaZaSztuke) {
        super(produkt, id, liczba, jakość);
        this.cenaZaSztuke = cenaZaSztuke;
    }

    public double cenaZaSztuke() {
        return cenaZaSztuke;
    }

    @Override
    public String toString() {
        return super.toString() + ", {CENA: " + cenaZaSztuke + "}";
    }
}
