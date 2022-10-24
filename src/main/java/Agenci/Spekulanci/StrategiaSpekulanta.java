package Agenci.Spekulanci;

import Giełda.GiełdaWidok;
import Oferty.OfertaSpekulanta;
import Produkty.Produkt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class StrategiaSpekulanta {

    public abstract List<OfertaSpekulanta> wystawOfertyKupna(GiełdaWidok daneGiełdy,
                                                             Map<Produkt.Typ, List<Integer>> zasoby, double diamenty,
                                                             int jedzenie, int id);

    public abstract List<OfertaSpekulanta> wystawOfertySprzedaży(GiełdaWidok daneGiełdy,
                                                                 Map<Produkt.Typ, List<Integer>> zasoby,
                                                                 double diamenty, int jedzenie, int id);


    protected List<OfertaSpekulanta> stwórzOfertyWedługJakości(Produkt.Typ produkt, double cena,
                                                               Map<Produkt.Typ, List<Integer>> zasoby, double diamenty,
                                                               int jedzenie, int id) {
        List<OfertaSpekulanta> oferty = new ArrayList<>();
        int tejSamejJakości = 1;
        int i = 0;
        for (i = 0; i < zasoby.get(produkt).size() - 1; i++) {
            if (zasoby.get(produkt).get(i).equals(zasoby.get(produkt).get(i + 1))) {
                tejSamejJakości++;
            } else {
                oferty.add(new OfertaSpekulanta(produkt, id, tejSamejJakości,
                        zasoby.get(produkt).get(i), cena));
                tejSamejJakości = 1;
            }
        }
        if (zasoby.get(produkt).size() > 0) {
            oferty.add(new OfertaSpekulanta(produkt, id, tejSamejJakości,
                    zasoby.get(produkt).get(i), cena));
        }
        return oferty;
    }


    protected List<OfertaSpekulanta> przygotujProduktDoSprzedaży(Produkt.Typ produkt, double cenaSprzedaży,
                                                                 Map<Produkt.Typ, List<Integer>> zasoby,
                                                                 double diamenty, int jedzenie, int id) {
        List<OfertaSpekulanta> oferty = new ArrayList<>();
        if (produkt == Produkt.Typ.JEDZENIE && jedzenie > 0) {
            oferty.add(new OfertaSpekulanta(produkt, id, jedzenie, -1, cenaSprzedaży));
            jedzenie = 0;
        } else if (zasoby.get(produkt) != null && !zasoby.get(produkt).isEmpty()) {
            oferty.addAll(stwórzOfertyWedługJakości(produkt, cenaSprzedaży, zasoby, diamenty, jedzenie, id));
            zasoby.put(produkt, new ArrayList<>());
        }
        return oferty;
    }
}
