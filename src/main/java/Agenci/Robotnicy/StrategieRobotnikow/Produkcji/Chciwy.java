package Agenci.Robotnicy.StrategieRobotnikow.Produkcji;

import Agenci.Robotnicy.Robotnik;
import Giełda.GiełdaWidok;
import Produkty.Produkt;

import java.util.List;
import java.util.Map;

public class Chciwy implements StrategiaProdukcji {

    public Chciwy() {
    }

    @Override
    public Produkt.Typ coProdukuje(GiełdaWidok daneGiełdy, Robotnik.Praca obecnaPraca, int poziomPracy,
                                   Map<Produkt.Typ, Integer> produktywość) {
        double maks = 0;
        double ostatnia;
        Produkt.Typ wybranyProdukt = Produkt.Typ.DIAMENTY;
        List<Double> historiaProduktu;
        for (Produkt.Typ produkt : Produkt.Typ.values()) {
            historiaProduktu = daneGiełdy.historiaŚrednichCen().get(produkt);
            if (historiaProduktu != null && !historiaProduktu.isEmpty()) {
                ostatnia = historiaProduktu.get(historiaProduktu.size() - 1);
                ostatnia *= produktywość.get(produkt);
                if (Robotnik.dopasujPracę(produkt) == obecnaPraca) {
                    ostatnia *= (1 + Robotnik.bonusKariery(poziomPracy) / 100);
                }
                if (ostatnia > maks) {
                    maks = ostatnia;
                    wybranyProdukt = produkt;
                }
            }
        }
        return wybranyProdukt;
    }
}
