package Agenci.Robotnicy.StrategieRobotnikow.Produkcji;

import Agenci.Robotnicy.Robotnik;
import Giełda.GiełdaWidok;
import Produkty.Produkt;

import java.util.Map;
import java.util.List;

public class Krótkowzroczny implements StrategiaProdukcji {

    public Krótkowzroczny() {
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
                if (ostatnia > maks) {
                    maks = ostatnia;
                    wybranyProdukt = produkt;
                }
            }
        }
        return wybranyProdukt;
    }
}
