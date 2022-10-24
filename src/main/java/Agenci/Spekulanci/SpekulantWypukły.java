package Agenci.Spekulanci;

import Giełda.GiełdaWidok;
import Oferty.OfertaSpekulanta;
import Produkty.Produkt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpekulantWypukły extends StrategiaSpekulanta {

    public SpekulantWypukły() {
    }

    @Override
    public List<OfertaSpekulanta> wystawOfertyKupna(GiełdaWidok daneGiełdy,
                                                    Map<Produkt.Typ, List<Integer>> zasoby, double diamenty,
                                                    int jedzenie, int id) {
        if (daneGiełdy.dzieńSymulacji() > 2) {
            List<OfertaSpekulanta> oferty = new ArrayList<>();
            for (Produkt.Typ produkt : Produkt.Typ.values()) {
                if (produkt != Produkt.Typ.DIAMENTY) {
                    List<Double> cenyProduktu = daneGiełdy.historiaŚrednichCen().get(produkt);
                    if (cenyProduktu.get(cenyProduktu.size() - 3) - cenyProduktu.get(cenyProduktu.size() - 2) <
                            cenyProduktu.get(cenyProduktu.size() - 2) - cenyProduktu.get(cenyProduktu.size() - 1)) {
                        double cenaKupna = cenyProduktu.get(cenyProduktu.size() - 1) * 0.9;
                        oferty.add(new OfertaSpekulanta(produkt, id, 100, -1, cenaKupna));
                    }
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
        if (daneGiełdy.dzieńSymulacji() > 2) {
            List<OfertaSpekulanta> oferty = new ArrayList<>();
            for (Produkt.Typ produkt : Produkt.Typ.values()) {
                if (produkt != Produkt.Typ.DIAMENTY) {
                    List<Double> cenyProduktu = daneGiełdy.historiaŚrednichCen().get(produkt);
                    if (cenyProduktu.get(cenyProduktu.size() - 3) - cenyProduktu.get(cenyProduktu.size() - 2) >
                            cenyProduktu.get(cenyProduktu.size() - 2) - cenyProduktu.get(cenyProduktu.size() - 1)) {
                        double cenaSprzedaży = cenyProduktu.get(cenyProduktu.size() - 1) * 0.9;
                        oferty.addAll(przygotujProduktDoSprzedaży(produkt, cenaSprzedaży, zasoby, diamenty, jedzenie,
                                id));
                    }
                }
            }
            return oferty;
        }
        return new ArrayList<>();
    }
}
