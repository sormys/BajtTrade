package Agenci.Robotnicy.StrategieRobotnikow.Kupna;

import Oferty.Oferta;
import Oferty.OfertaRobotnika;
import Produkty.Produkt;

import java.util.List;

public class Czyścioszek extends StrategiaKupna {
    public Czyścioszek() {
    }

    @Override
    public List<Oferta> oferty(int liczbaUbrań, int liczbaWyprodukowanych, int id) {
        List<Oferta> tmp = super.oferty(liczbaUbrań, liczbaWyprodukowanych, id);
        if (liczbaUbrań < 100) {
            tmp.add(new OfertaRobotnika(Produkt.Typ.UBRANIA, id, 100 - liczbaUbrań, -1));
        }
        return tmp;
    }
}
