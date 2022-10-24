package Agenci.Spekulanci;

import Agenci.Agent;
import Giełda.GiełdaWidok;
import Oferty.OfertaSpekulanta;
import com.squareup.moshi.Json;

import java.util.List;


public class Spekulant extends Agent {

    @Json(name = "kariera")
    private StrategiaSpekulanta strategia;

    public StrategiaSpekulanta strategia() {
        return strategia;
    }

    public List<OfertaSpekulanta> wystawOfertyKupna(GiełdaWidok daneGiełdy) {
        return strategia.wystawOfertyKupna(daneGiełdy, zasoby, diamenty, jedzenie, id);
    }

    ;

    public List<OfertaSpekulanta> wystawOfertySprzedaży(GiełdaWidok daneGiełdy) {
        return strategia.wystawOfertySprzedaży(daneGiełdy, zasoby, diamenty, jedzenie, id);
    }

}
