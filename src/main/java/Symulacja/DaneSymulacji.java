package Symulacja;

import Giełda.*;
import Produkty.Produkt;
import com.squareup.moshi.Json;

import java.util.Map;

public class DaneSymulacji {
    @Json(name = "dlugosc")
    public int długość;
    @Json(name = "gielda")
    public RodzajGiełdy rodzajGiełdy;
    @Json(name = "kara_za_brak_ubran")
    public int karaZaBrakUbrań;
    @Json(name = "ceny")
    public Map<Produkt.Typ, Double> cenyProduktów;

    public DaneSymulacji(int długość, RodzajGiełdy rodzajGiełdy, int karaZaBrakUbrań,
                         Map<Produkt.Typ, Double> cenyProduktów) {
        this.długość = długość;
        this.rodzajGiełdy = rodzajGiełdy;
        this.karaZaBrakUbrań = karaZaBrakUbrań;
        this.cenyProduktów = cenyProduktów;
    }
}
