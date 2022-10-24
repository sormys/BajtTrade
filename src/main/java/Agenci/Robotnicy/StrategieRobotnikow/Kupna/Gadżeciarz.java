package Agenci.Robotnicy.StrategieRobotnikow.Kupna;

import Oferty.Oferta;
import Oferty.OfertaRobotnika;
import Produkty.Produkt;

import java.util.List;

public class Gadżeciarz extends Zmechanizowany {

    public Gadżeciarz(int liczba_narzedzi) {
        super(liczba_narzedzi);
    }

    @Override
    public List<Oferta> oferty(int liczbaUbrań, int liczbaWyprodukowanych, int id) {
        List<Oferta> tmp = super.oferty(liczbaUbrań, liczbaWyprodukowanych, id);
        tmp.add(new OfertaRobotnika(Produkt.Typ.PROGRAMY, id, liczbaWyprodukowanych, -1));
        return tmp;
    }
}
