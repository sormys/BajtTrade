package Agenci.Robotnicy.StrategieRobotnikow.Kariery;

import Agenci.Robotnicy.Robotnik;
import Giełda.GiełdaWidok;
import Produkty.Produkt;

import java.util.List;

public class Rewolucjonista implements StrategiaKariery {
    public Rewolucjonista() {
    }

    @Override
    public Robotnik.Praca zmien(int id, Robotnik.Praca obecnaPraca, GiełdaWidok daneGiełdy) {
        if (daneGiełdy.dzieńSymulacji() % 7 != 0) {
            return obecnaPraca;
        }
        int maks = 0;
        Produkt.Typ rekordowyProdukt = Produkt.Typ.DIAMENTY;
        int suma = 0;
        int n = id % 17;
        for (Produkt.Typ produkt : Produkt.Typ.values()) {
            List<Integer> sprzedaneProdukty = daneGiełdy.historiaSprzedaży().get(produkt);
            if (sprzedaneProdukty != null) {
                suma = 0;
                for (int i = 0; i < n && sprzedaneProdukty.size() - i - 1 >= 0; i++) {
                    suma += sprzedaneProdukty.get(sprzedaneProdukty.size() - i - 1);
                }
                if (suma > maks) {
                    maks = suma;
                    rekordowyProdukt = produkt;
                }
            }
        }
        return Robotnik.dopasujPracę(rekordowyProdukt);
    }
}
