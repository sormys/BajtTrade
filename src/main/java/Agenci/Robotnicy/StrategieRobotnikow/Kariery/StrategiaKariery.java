package Agenci.Robotnicy.StrategieRobotnikow.Kariery;

import Agenci.Robotnicy.Robotnik;
import Giełda.GiełdaWidok;


public interface StrategiaKariery {

    public Robotnik.Praca zmien(int id, Robotnik.Praca obecnaPraca, GiełdaWidok daneGiełdy);
}
