package Agenci.Robotnicy.StrategieRobotnikow.Produkcji;

import Agenci.Robotnicy.Robotnik;
import Giełda.GiełdaWidok;
import Produkty.Produkt;
import com.squareup.moshi.Json;

import java.util.List;
import java.util.Map;

public class Średniak implements StrategiaProdukcji {

    @Json(name = "historia_sredniej_produkcji")
    private final int historiaŚredniejProdukcji;

    public Średniak(int historiaŚredniejProdukcji) {
        this.historiaŚredniejProdukcji = historiaŚredniejProdukcji;
    }

    public int historiaŚredniejProdukcji() {
        return historiaŚredniejProdukcji;
    }

    @Override
    public Produkt.Typ coProdukuje(GiełdaWidok daneGiełdy, Robotnik.Praca obecnaPraca, int poziomPracy,
                                   Map<Produkt.Typ, Integer> produktywość) {
        double maksProduktu;
        double maks = 0;
        double obecnie;
        Produkt.Typ wybranyProdukt = Produkt.Typ.DIAMENTY;
        List<Double> historiaProduktu;
        for (Produkt.Typ produkt : Produkt.Typ.values()) {
            historiaProduktu = daneGiełdy.historiaŚrednichCen().get(produkt);
            if (historiaProduktu != null && !historiaProduktu.isEmpty()) {
                maksProduktu = 0;
                for (int i = 0; i < historiaŚredniejProdukcji && historiaProduktu.size() - i - 1 >= 0; i++) {
                    obecnie = historiaProduktu.get(historiaProduktu.size() - i - 1);
                    if (obecnie > maksProduktu) {
                        maksProduktu = obecnie;
                    }
                }
                if (maksProduktu > maks) {
                    maks = maksProduktu;
                    wybranyProdukt = produkt;
                }
            }
        }
        return wybranyProdukt;
    }
}
