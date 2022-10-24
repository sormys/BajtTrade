package Giełda;

import Produkty.Produkt;

import java.util.List;
import java.util.Map;

public class GiełdaWidok {
    private final Giełda giełda;

    public GiełdaWidok(Giełda giełda) {
        this.giełda = giełda;
    }

    public int dzieńSymulacji() {
        return giełda.dzieńSymulacji();
    }

    public int karaZaBrakUbrań() {
        return giełda.karaZaBrakUbrań();
    }

    public Map<Produkt.Typ, List<Double>> historiaŚrednichCen() {
        return giełda.historiaŚrednichCen();
    }

    public Map<Produkt.Typ, List<Integer>> historiaSprzedaży() {
        return giełda.historiaSprzedaży();
    }

    public Map<Produkt.Typ, List<Integer>> sprzedanePrzezRobotoników() {
        return giełda.sprzedanePrzezRobotników();
    }

}
