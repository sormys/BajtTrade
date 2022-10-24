package Agenci;

import java.util.List;
import java.util.Map;

import Produkty.Produkt;

public abstract class Agent {
    protected int id;
    protected double diamenty;
    protected int jedzenie;
    protected Map<Produkt.Typ, List<Integer>> zasoby;

    public int id() {
        return id;
    }

    public double diamenty() {
        return diamenty;
    }

    public double jedzenie() {
        return jedzenie;
    }

    public void zmieńDiamenty(double zmiana) {
        diamenty += zmiana;
    }

    public Map<Produkt.Typ, List<Integer>> zasoby() {
        return zasoby;
    }

    public static int komparator(Agent o1, Agent o2) {
        if (o1.diamenty() > o2.diamenty()) {
            return 1;
        } else if (o1.diamenty() < o2.diamenty()) {
            return -1;
        } else {
            return Integer.compare(o1.id(), o2.id());
        }
    }

    public void inicjalizuj() {
        if (zasoby.get(Produkt.Typ.DIAMENTY) != null) {
            diamenty = zasoby.get(Produkt.Typ.DIAMENTY).get(0);
            jedzenie = zasoby.get(Produkt.Typ.JEDZENIE).get(0);
            zasoby.put(Produkt.Typ.DIAMENTY, null);
            zasoby.put(Produkt.Typ.JEDZENIE, null);
        }
    }


    public void dodajZasób(Produkt.Typ produkt, int liczba, int jakość) {
        switch (produkt) {
            case DIAMENTY:
                diamenty += liczba;
                break;
            case JEDZENIE:
                jedzenie += liczba;
                break;
            default:
                for (int i = 0; i < liczba; i++) {
                    zasoby.get(produkt).add(Produkt.nowyProdukt(produkt, jakość).jakość());
                }
        }
    }

    @Override
    public String toString() {
        StringBuilder wynik = new StringBuilder();
        wynik.append("ID: ").append(id);
        wynik.append(" Zasoby: ");
        for (Produkt.Typ produkt : zasoby.keySet()) {
            wynik.append(produkt).append(": ");
            if (produkt == Produkt.Typ.DIAMENTY)
                wynik.append(diamenty);
            else if (produkt == Produkt.Typ.JEDZENIE)
                wynik.append(jedzenie);
            else
                wynik.append(zasoby.get(produkt));
            wynik.append(" ");
        }
        return wynik.toString();
    }
}
