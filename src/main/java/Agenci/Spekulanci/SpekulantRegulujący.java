package Agenci.Spekulanci;

import Giełda.GiełdaWidok;
import Oferty.OfertaSpekulanta;
import Produkty.Produkt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpekulantRegulujący extends StrategiaSpekulanta {

    public SpekulantRegulujący() {
    }

    private double wyznaczCenę(GiełdaWidok daneGiełdy, Produkt.Typ produkt) {
        double cena = daneGiełdy.historiaŚrednichCen().get(produkt).get(daneGiełdy.dzieńSymulacji() - 1);
        double współczynnik = daneGiełdy.sprzedanePrzezRobotoników().get(produkt).get(daneGiełdy.dzieńSymulacji() - 1);
        współczynnik /= Math.max(daneGiełdy.sprzedanePrzezRobotoników().get(produkt).get(
                daneGiełdy.dzieńSymulacji() - 2), 1);
        return cena * współczynnik;
    }


    @Override
    public List<OfertaSpekulanta> wystawOfertyKupna(GiełdaWidok daneGiełdy,
                                                    Map<Produkt.Typ, List<Integer>> zasoby, double diamenty,
                                                    int jedzenie, int id) {
        if (daneGiełdy.dzieńSymulacji() > 1) {
            List<OfertaSpekulanta> oferty = new ArrayList<>();
            for (Produkt.Typ produkt : Produkt.Typ.values()) {
                if (produkt != Produkt.Typ.DIAMENTY) {
                    double cenaKupna = wyznaczCenę(daneGiełdy, produkt) * 0.9;
                    if (cenaKupna != 0)
                        oferty.add(new OfertaSpekulanta(produkt, id, 100, -1, cenaKupna));
                }
            }
            return oferty;
        }
        return new ArrayList<>();
    }

    @Override
    public List<OfertaSpekulanta> wystawOfertySprzedaży(GiełdaWidok daneGiełdy,
                                                        Map<Produkt.Typ, List<Integer>> zasoby, double diamenty,
                                                        int jedzenie, int id) {
        List<OfertaSpekulanta> oferty = new ArrayList<>();
        if (daneGiełdy.dzieńSymulacji() > 1) {
            for (Produkt.Typ produkt : Produkt.Typ.values()) {
                if (produkt != Produkt.Typ.DIAMENTY) {
                    double cenaSprzedaży = wyznaczCenę(daneGiełdy, produkt) * 1.1;
                    if (cenaSprzedaży != 0)
                        oferty.addAll(przygotujProduktDoSprzedaży(produkt, cenaSprzedaży, zasoby, diamenty,
                                jedzenie, id));
                }
            }
            return oferty;
        }
        return new ArrayList<>();
    }
}
