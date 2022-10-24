package Agenci.Robotnicy.StrategieRobotnikow.Pracy;

import Giełda.GiełdaWidok;
import com.squareup.moshi.Json;

public class Oszczędny implements StrategiaPracy {

    @Json(name = "limit_diamentow")
    private final int limitDiamentów;

    public Oszczędny(int limitDiamentów) {
        this.limitDiamentów = limitDiamentów;
    }

    public int limitDiamentów() {
        return limitDiamentów;
    }

    @Override
    public boolean czySieUczy(double diamenty, GiełdaWidok daneGiełdy) {
        return diamenty > limitDiamentów;
    }
}
