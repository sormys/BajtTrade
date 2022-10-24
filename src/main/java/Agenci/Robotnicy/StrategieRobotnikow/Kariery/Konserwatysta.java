package Agenci.Robotnicy.StrategieRobotnikow.Kariery;

import Agenci.Robotnicy.Robotnik;
import Giełda.GiełdaWidok;

public class Konserwatysta implements StrategiaKariery {

    public Konserwatysta() {
    }

    @Override
    public Robotnik.Praca zmien(int id, Robotnik.Praca obecnaPraca, GiełdaWidok daneGiełdy) {
        return obecnaPraca;
    }
}
