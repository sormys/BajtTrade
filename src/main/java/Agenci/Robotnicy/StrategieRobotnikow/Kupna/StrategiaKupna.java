package Agenci.Robotnicy.StrategieRobotnikow.Kupna;

import Oferty.Oferta;
import Oferty.OfertaRobotnika;
import Produkty.Produkt;

import java.util.ArrayList;
import java.util.List;

public abstract class StrategiaKupna {

    public StrategiaKupna() {
    }

    public List<Oferta> oferty(int liczbaUbrań, int liczbaWyprodukowanych, int id) {
        List<Oferta> tmp = new ArrayList<>();
        tmp.add(new OfertaRobotnika(Produkt.Typ.JEDZENIE, id, 100, -1));
        return tmp;
    }
}
