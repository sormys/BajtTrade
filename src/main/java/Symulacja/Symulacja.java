package Symulacja;

import Agenci.Robotnicy.Robotnik;
import Agenci.Spekulanci.Spekulant;
import Giełda.*;
import Produkty.Produkt;
import com.squareup.moshi.Json;

import java.util.List;
import java.util.Map;

public class Symulacja {

    @Json(name = "dlugosc")
    private final int długośćSymulacji;
    private Giełda giełda;


    public Symulacja(int długośćSymulacji, int karaZaBrakUbrań, Map<Produkt.Typ, Double> cenyProduktów,
                     List<Robotnik> robotnicy, List<Spekulant> spekulanci, RodzajGiełdy rodzajGiełdy) {
        this.długośćSymulacji = długośćSymulacji;
        this.giełda = new Giełda(robotnicy, spekulanci, karaZaBrakUbrań, cenyProduktów, rodzajGiełdy);
    }

    public void symuluj() {
        for (int i = 0; i < długośćSymulacji; i++) {
            giełda.symulujDzień();
        }
    }

    public HistoriaGiełdy historiaGiełdy() {
        return giełda.historiaGiełdy();
    }
}
