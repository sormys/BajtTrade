package Agenci.Spekulanci;

import Giełda.GiełdaWidok;
import Oferty.OfertaSpekulanta;
import Produkty.Produkt;
import com.squareup.moshi.Json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpekulantŚredni extends StrategiaSpekulanta {

    @Json(name = "historia_spekulanta_sredniego")
    private final int historiaSpekulantaŚredniego;

    public SpekulantŚredni(int historiaSpekulantaŚredniego) {
        this.historiaSpekulantaŚredniego = historiaSpekulantaŚredniego;
    }

    public int historiaSpekulantaŚredniego() {
        return historiaSpekulantaŚredniego;
    }

    private double wyznaczŚredniąCenę(Produkt.Typ produkt, GiełdaWidok daneGiełdy) {
        double sumaCen = 0;
        int i = 0;
        List<Double> historiaProduktu = daneGiełdy.historiaŚrednichCen().get(produkt);
        for (i = 0; i < Math.min(historiaProduktu.size(), historiaSpekulantaŚredniego); i++) {
            sumaCen += historiaProduktu.get(historiaProduktu.size() - i - 1);
        }
        if (i == 0) {
            return 0;
        }
        return sumaCen / i;
    }


    @Override
    public List<OfertaSpekulanta> wystawOfertyKupna(GiełdaWidok daneGiełdy, Map<Produkt.Typ, List<Integer>> zasoby,
                                                    double diamenty, int jedzenie, int id) {
        List<OfertaSpekulanta> oferty = new ArrayList<>();
        for (Produkt.Typ produkt : Produkt.Typ.values()) {
            if (produkt != Produkt.Typ.DIAMENTY) {
                double cenaKupna = wyznaczŚredniąCenę(produkt, daneGiełdy);
                if (zasoby.get(produkt) == null || zasoby.get(produkt).isEmpty()
                        || (produkt == Produkt.Typ.JEDZENIE && jedzenie == 0)) {
                    cenaKupna *= 0.95;
                } else {
                    cenaKupna *= 0.9;
                }
                oferty.add(new OfertaSpekulanta(produkt, id, 100, -1, cenaKupna));
            }
        }
        return oferty;
    }

    @Override
    public List<OfertaSpekulanta> wystawOfertySprzedaży(GiełdaWidok daneGiełdy,
                                                        Map<Produkt.Typ, List<Integer>> zasoby, double diamenty,
                                                        int jedzenie, int id) {
        List<OfertaSpekulanta> oferty = new ArrayList<>();
        for (Produkt.Typ produkt : Produkt.Typ.values()) {
            double cenaSprzedaży = wyznaczŚredniąCenę(produkt, daneGiełdy) * 1.1;
            oferty.addAll(przygotujProduktDoSprzedaży(produkt, cenaSprzedaży, zasoby, diamenty, jedzenie, id));
        }
        return oferty;
    }
}
