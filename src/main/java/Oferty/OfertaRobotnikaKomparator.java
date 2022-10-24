package Oferty;

import java.util.Comparator;

public class OfertaRobotnikaKomparator implements Comparator<OfertaRobotnika> {

    @Override
    public int compare(OfertaRobotnika o1, OfertaRobotnika o2) {
        return Integer.compare(o2.jakość(), o1.jakość());
    }
}
