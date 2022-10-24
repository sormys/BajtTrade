package Agenci.Robotnicy.StrategieRobotnikow.Pracy;

import Giełda.GiełdaWidok;
import com.squareup.moshi.Json;

public class Okresowy implements StrategiaPracy {

    @Json(name = "okresowosc_nauki")
    private final int okresowośćNauki;

    public Okresowy(int okresowośćNauki) {
        assert okresowośćNauki != 0;
        this.okresowośćNauki = okresowośćNauki;
    }

    public int okresowośćNauki() {
        return okresowośćNauki;
    }

    @Override
    public boolean czySieUczy(double diamenty, GiełdaWidok daneGiełdy) {
        return daneGiełdy.dzieńSymulacji() % okresowośćNauki == 0;
    }
}
