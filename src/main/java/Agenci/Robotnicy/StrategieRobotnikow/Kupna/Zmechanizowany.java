package Agenci.Robotnicy.StrategieRobotnikow.Kupna;

import Oferty.Oferta;
import Oferty.OfertaRobotnika;
import Produkty.Produkt;
import com.squareup.moshi.Json;

import java.util.List;

public class Zmechanizowany extends Czyścioszek {
    @Json(name = "liczba_narzedzi")
    protected int liczbaNarzedzi;

    public Zmechanizowany(int liczbaNarzedzi) {
        this.liczbaNarzedzi = liczbaNarzedzi;
    }

    public int liczbaNarzedzi() {
        return liczbaNarzedzi;
    }

    @Override
    public List<Oferta> oferty(int liczbaUbrań, int liczbaWyprodukowanych, int id) {
        List<Oferta> tmp = super.oferty(liczbaUbrań, liczbaWyprodukowanych, id);
        tmp.add(new OfertaRobotnika(Produkt.Typ.NARZĘDZIA, id, liczbaNarzedzi, -1));
        return tmp;
    }
}
