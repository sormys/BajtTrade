package Oferty;

import java.util.Comparator;

public class OfertaSpekulantaKomparator implements Comparator<OfertaSpekulanta> {

    @Override
    public int compare(OfertaSpekulanta o1, OfertaSpekulanta o2) {
        if (o1.jakość > o2.jakość) {
            return 1;
        } else if (o1.jakość < o2.jakość) {
            return -1;
        } else {
            return Double.compare(o2.cenaZaSztuke(), o1.cenaZaSztuke());
        }
    }
}
