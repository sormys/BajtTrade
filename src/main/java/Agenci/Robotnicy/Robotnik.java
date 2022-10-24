package Agenci.Robotnicy;

import Agenci.Agent;
import Agenci.Robotnicy.StrategieRobotnikow.Kariery.StrategiaKariery;
import Agenci.Robotnicy.StrategieRobotnikow.Kupna.StrategiaKupna;
import Agenci.Robotnicy.StrategieRobotnikow.Pracy.StrategiaPracy;
import Agenci.Robotnicy.StrategieRobotnikow.Produkcji.StrategiaProdukcji;
import Giełda.GiełdaWidok;
import Oferty.Oferta;
import Oferty.OfertaRobotnika;
import Produkty.Produkt;
import com.squareup.moshi.Json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Robotnik extends Agent {

    public enum Praca {
        @Json(name = "rolnik")
        ROLNIK,
        @Json(name = "rzemieslnik")
        RZEMIEŚLNIK,
        @Json(name = "inzynier")
        INŻYNIER,
        @Json(name = "gornik")
        GÓRNIK,
        @Json(name = "programista")
        PROGRAMISTA
    }


    @Json(name = "produktywnosc")
    private Map<Produkt.Typ, Integer> produktywość;

    private int dniGłodówki;

    @Json(name = "poziom")
    public int poziomPoczątkowy;
    private int wyprodukowane;
    private boolean pracuje;
    @Json(name = "kupowanie")
    private StrategiaKupna strategiaKupna;
    @Json(name = "zmiana")
    private StrategiaKariery strategiaKariery;
    @Json(name = "uczenie")
    private StrategiaPracy strategiaPracy;
    @Json(name = "produkcja")
    private StrategiaProdukcji strategiaProdukcji;

    private List<Integer> poziomyKariery;

    @Json(name = "kariera")
    private Praca obecnaPraca;

    public static double bonusKariery(int poziom) {
        if (poziom < 1)
            return 0;
        switch (poziom) {
            case 1:
                return 50;
            case 2:
                return 150;
            default:
                return 300 + (poziom - 3) * 25;
        }
    }

    public static Praca dopasujPracę(Produkt.Typ produkt) {
        switch (produkt) {
            case UBRANIA:
                return Praca.RZEMIEŚLNIK;
            case JEDZENIE:
                return Praca.ROLNIK;
            case NARZĘDZIA:
                return Praca.INŻYNIER;
            case PROGRAMY:
                return Praca.PROGRAMISTA;
            default:
                return Praca.GÓRNIK;
        }
    }

    public Robotnik(int id, Map<Produkt.Typ, List<Integer>> zasoby,
                    Map<Produkt.Typ, Integer> produktywość, double diamenty, int jedzenie,
                    StrategiaKupna strategiaKupna, StrategiaKariery strategiaKariery,
                    StrategiaPracy strategiaPracy, StrategiaProdukcji strategiaProdukcji,
                    Praca pracaPoczątkowa, int poziom) {
        this.id = id;
        this.zasoby = zasoby;
        this.produktywość = produktywość;
        this.diamenty = diamenty;
        this.jedzenie = jedzenie;
        this.strategiaKariery = strategiaKariery;
        this.strategiaKupna = strategiaKupna;
        this.strategiaPracy = strategiaPracy;
        this.strategiaProdukcji = strategiaProdukcji;
        this.dniGłodówki = 0;
        this.wyprodukowane = 0;
        this.poziomyKariery = new ArrayList<>();
        for (Praca praca : Praca.values()) {
            poziomyKariery.add(1);
        }
        this.obecnaPraca = pracaPoczątkowa;
        this.poziomPoczątkowy = poziom;
        poziomyKariery.set(obecnaPraca.ordinal(), poziom);
        this.pracuje = true;
    }


    @Override
    public void inicjalizuj() {
        super.inicjalizuj();
        dniGłodówki = 0;
        pracuje = true;
        poziomyKariery = new ArrayList<>();
        for (Praca praca : Praca.values()) {
            poziomyKariery.add(1);
        }
        poziomyKariery.set(obecnaPraca.ordinal(), poziomPoczątkowy);
    }

    public boolean czyPracuje(GiełdaWidok daneGiełdy) {
        if (strategiaPracy.czySieUczy(diamenty, daneGiełdy)) {
            Praca nowaPraca = strategiaKariery.zmien(id, obecnaPraca, daneGiełdy);
            if (nowaPraca == obecnaPraca) {
                poziomyKariery.set(obecnaPraca.ordinal(), poziomyKariery.get(obecnaPraca.ordinal()) + 1);
            } else {
                obecnaPraca = nowaPraca;
            }
            dniGłodówki = 0;
            this.pracuje = false;
            return false;
        } else {
            this.pracuje = true;
            return true;
        }
    }

    public int poziomKariery() {
        return poziomyKariery.get(obecnaPraca.ordinal());
    }

    public Praca obecnaPraca() {
        return obecnaPraca;
    }

    public StrategiaKariery strategiaKariery() {
        return strategiaKariery;
    }

    public StrategiaKupna strategiaKupna() {
        return strategiaKupna;
    }

    public StrategiaProdukcji strategiaProdukcji() {
        return strategiaProdukcji;
    }

    public StrategiaPracy strategiaPracy() {
        return strategiaPracy;
    }

    public Map<Produkt.Typ, Integer> produktywość() {
        return produktywość;
    }

    public int wyznaczPremie(GiełdaWidok daneGiełdy, Produkt.Typ produkowany) {
        int premia = 0;
        if (dopasujPracę(produkowany) == obecnaPraca) {
            premia += bonusKariery(poziomyKariery.get(obecnaPraca.ordinal()));
        }
        switch (dniGłodówki) {
            case 1:
                premia -= 100;
                break;
            case 2:
                premia -= 300;
                break;
            default:
                break;
        }
        if (zasoby.get(Produkt.Typ.UBRANIA).size() < 100) {
            premia -= daneGiełdy.karaZaBrakUbrań();
        }
        for (Integer jakoscNarzedzia : zasoby.get(Produkt.Typ.NARZĘDZIA)) {
            premia += jakoscNarzedzia;
        }
        zasoby.put(Produkt.Typ.NARZĘDZIA, new ArrayList<>());
        return premia;
    }

    public List<OfertaRobotnika> wystawOferty(GiełdaWidok daneGiełdy) {
        if (!czyPracuje(daneGiełdy)) {
            return new ArrayList<>();
        }

        Produkt.Typ produkowany = strategiaProdukcji.coProdukuje(daneGiełdy, obecnaPraca,
                poziomyKariery.get(obecnaPraca.ordinal()), produktywość);

        int premia = wyznaczPremie(daneGiełdy, produkowany);

        int liczbaWyprodukowanych = Math.max(0, (produktywość.get(produkowany) * (100 + premia)) / 100);

        if (produkowany == Produkt.Typ.DIAMENTY || liczbaWyprodukowanych == 0) {
            diamenty += liczbaWyprodukowanych;
            return new ArrayList<>();
        }

        wyprodukowane = liczbaWyprodukowanych;
        List<Integer> programy = zasoby.get(Produkt.Typ.PROGRAMY);
        programy.sort((a, b) -> Integer.compare(a, b) * (-1));
        List<Integer> wyprodukowane = new ArrayList<>();

        for (int i = 0; i < liczbaWyprodukowanych; i++) {
            if (!programy.isEmpty() && (produkowany == Produkt.Typ.NARZĘDZIA || produkowany == Produkt.Typ.UBRANIA)) {
                wyprodukowane.add(programy.get(0));
                programy.remove(0);
            } else if (produkowany == Produkt.Typ.PROGRAMY) {
                wyprodukowane.add(poziomyKariery.get(Produkt.Typ.PROGRAMY.ordinal()));
            } else {
                wyprodukowane.add(1);
            }
        }
        wyprodukowane.sort(Integer::compareTo);
        List<OfertaRobotnika> oferty = new ArrayList<>();
        int tejSamejJakości = 1;
        for (int i = 0; i < liczbaWyprodukowanych - 1; i++) {
            if (wyprodukowane.get(i).equals(wyprodukowane.get(i + 1))) {
                tejSamejJakości++;
            } else {
                oferty.add(new OfertaRobotnika(produkowany, id, tejSamejJakości, wyprodukowane.get(i)));
                tejSamejJakości = 0;
            }
        }
        oferty.add(new OfertaRobotnika(produkowany, id, tejSamejJakości, wyprodukowane.get(wyprodukowane.size() - 1)));
        return oferty;
    }

    public List<Oferta> wystawOfertyKupna() {
        if (pracuje)
            return strategiaKupna.oferty(zasoby.get(Produkt.Typ.UBRANIA).size(), wyprodukowane, id);
        else
            return new ArrayList<>();
    }


    // Zwraca informacje czy Robotnik przeżył dany dzień
    public boolean zakończDzień() {
        if (pracuje) {
            if (jedzenie < 100) {
                jedzenie = 0;
                dniGłodówki++;
                if (dniGłodówki == 3) {
                    return false;
                }
            } else {
                jedzenie -= 100;
                dniGłodówki = 0;
            }
            zasoby.put(Produkt.Typ.NARZĘDZIA, new ArrayList<>());
            for (int i = 0; i < 100 && i < zasoby.get(Produkt.Typ.UBRANIA).size(); i++) {
                zasoby.get(Produkt.Typ.UBRANIA).set(i, zasoby.get(Produkt.Typ.UBRANIA).get(i) - 1);
            }
            zasoby.get(Produkt.Typ.UBRANIA).removeIf(ubranie -> ubranie.equals(0));
        }
        pracuje = true;
        return true;
    }

}
