package Agenci.Robotnicy.StrategieRobotnikow.Pracy;

import Giełda.GiełdaWidok;

import java.util.Random;

public class Rozkładowy implements StrategiaPracy {

    public Rozkładowy() {
    }

    @Override
    public boolean czySieUczy(double diamenty, GiełdaWidok daneGiełdy) {
        Random r = new Random();
        return r.nextInt(daneGiełdy.dzieńSymulacji() + 3) == 0;
    }
}
