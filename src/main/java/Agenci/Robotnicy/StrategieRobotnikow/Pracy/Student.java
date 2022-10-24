package Agenci.Robotnicy.StrategieRobotnikow.Pracy;

import Giełda.GiełdaWidok;
import Produkty.Produkt;

import java.util.List;

public class Student implements StrategiaPracy {
    private final int zapas;
    private final int okres;

    public Student(int zapas, int okres) {
        this.zapas = zapas;
        this.okres = okres;
    }

    public int zapas() {
        return zapas;
    }

    public int okres() {
        return okres;
    }

    private int sredniaCena(int dzienSymulacji, List<Double> srednieCenyJedzenia) {
        int i = 0;
        int suma = 0;
        for (i = 0; i < okres && dzienSymulacji - i - 1 >= 0; i++) {
            suma += srednieCenyJedzenia.get(dzienSymulacji - i - 1);
        }
        if (i != 0)
            return suma / i;
        else
            return 0;
    }

    @Override
    public boolean czySieUczy(double diamenty, GiełdaWidok daneGiełdy) {
        float średniaCena = sredniaCena(daneGiełdy.dzieńSymulacji(),
                daneGiełdy.historiaŚrednichCen().get(Produkt.Typ.JEDZENIE));
        return diamenty >= średniaCena * zapas * 100;
    }
}
