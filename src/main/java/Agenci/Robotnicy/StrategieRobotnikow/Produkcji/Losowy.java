package Agenci.Robotnicy.StrategieRobotnikow.Produkcji;

import Agenci.Robotnicy.Robotnik;
import Giełda.GiełdaWidok;
import Produkty.Produkt;

import java.util.Map;
import java.util.Random;

public class Losowy implements StrategiaProdukcji {

    public Losowy() {
    }

    @Override
    public Produkt.Typ coProdukuje(GiełdaWidok daneGiełdy, Robotnik.Praca obecnaPraca, int poziomPracy,
                                   Map<Produkt.Typ, Integer> produktywość) {
        Random r = new Random();
        return Produkt.Typ.values()[r.nextInt(Produkt.Typ.values().length)];
    }
}
