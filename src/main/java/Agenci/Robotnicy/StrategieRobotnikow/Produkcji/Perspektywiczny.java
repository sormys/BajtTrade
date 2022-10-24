package Agenci.Robotnicy.StrategieRobotnikow.Produkcji;

import Agenci.Robotnicy.Robotnik;
import Giełda.GiełdaWidok;
import Produkty.Produkt;
import com.squareup.moshi.Json;


import java.util.List;
import java.util.Map;

public class Perspektywiczny implements StrategiaProdukcji {

    @Json(name = "historia_perspektywy")
    private int historiaPerspektywy;

    public Perspektywiczny(int historiaPerspektywy) {
        this.historiaPerspektywy = historiaPerspektywy;
    }

    public int historiaPerspektywy() {
        return historiaPerspektywy;
    }

    @Override
    public Produkt.Typ coProdukuje(GiełdaWidok daneGiełdy, Robotnik.Praca obecnaPraca, int poziomPracy,
                                   Map<Produkt.Typ, Integer> produktywość) {
        double obecnie;
        double ostatnio;
        double maks = 0;
        boolean pierwszy = true;
        Produkt.Typ wybranyProdukt = Produkt.Typ.DIAMENTY;
        List<Double> historiaProduktu;
        for (Produkt.Typ produkt : Produkt.Typ.values()) {
            historiaProduktu = daneGiełdy.historiaŚrednichCen().get(produkt);
            if (historiaProduktu != null && !historiaProduktu.isEmpty()) {
                obecnie = historiaProduktu.get(historiaProduktu.size() - 1);
                if (historiaProduktu.size() > historiaPerspektywy) {
                    ostatnio = historiaProduktu.get(historiaProduktu.size() - 1 - historiaPerspektywy);
                } else {
                    ostatnio = historiaProduktu.get(0);
                }
                if (pierwszy || obecnie - ostatnio > maks) {
                    pierwszy = false;
                    maks = obecnie - ostatnio;
                    wybranyProdukt = produkt;
                }
            }
        }
        return wybranyProdukt;
    }

}

