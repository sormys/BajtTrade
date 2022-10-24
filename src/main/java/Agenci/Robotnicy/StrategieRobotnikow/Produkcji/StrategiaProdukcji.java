package Agenci.Robotnicy.StrategieRobotnikow.Produkcji;

import Agenci.Robotnicy.Robotnik;
import Giełda.GiełdaWidok;
import Produkty.Produkt;

import java.util.Map;

public interface StrategiaProdukcji {

    Produkt.Typ coProdukuje(GiełdaWidok daneGiełdy, Robotnik.Praca obecnaPraca, int poziomPracy,
                            Map<Produkt.Typ, Integer> produktywość);


}
