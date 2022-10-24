package Giełda;

import Agenci.Robotnicy.Robotnik;
import Agenci.Spekulanci.Spekulant;
import Oferty.*;
import Produkty.Produkt;
import com.squareup.moshi.Json;

import java.util.*;

public class Giełda {


    protected List<Spekulant> spekulanci;
    protected List<Robotnik> robotnicy;

    protected Map<Produkt.Typ, List<OfertaSpekulanta>> sprzedażSpekulanci;
    protected Map<Produkt.Typ, List<OfertaRobotnika>> sprzedażRobotnicy;

    protected Map<Produkt.Typ, List<Integer>> sprzedanePrzezRobotników;

    private HistoriaGiełdy historia;
    protected int karaZaBrakUbrań;
    protected int dzieńSymulacji;
    protected RodzajGiełdy rodzajGiełdy;

    protected Integer[] sprzedaneDzisiaj;

    protected Double[] wartośćSprzedanych;

    protected Double[] najniższeCeny;

    private void posortujAgentów() {
        rodzajGiełdy.posortujAgentów(robotnicy, spekulanci, dzieńSymulacji);
    }

    public Giełda(List<Robotnik> robotnicy, List<Spekulant> spekulanci, int karaZaBrakUbrań,
                  Map<Produkt.Typ, Double> cenyProduktów, RodzajGiełdy rodzajGiełdy) {
        this.rodzajGiełdy = rodzajGiełdy;
        this.robotnicy = robotnicy;
        this.spekulanci = spekulanci;
        this.karaZaBrakUbrań = karaZaBrakUbrań;
        dzieńSymulacji = 0;
        Map<Produkt.Typ, List<Integer>> historiaSprzedaży = new HashMap<>();
        Map<Produkt.Typ, List<Double>> historiaŚrednichCen = new HashMap<>();
        Map<Produkt.Typ, List<Integer>> sprzedanePrzezRobotników = new HashMap<>();
        Map<Produkt.Typ, List<Double>> maksymalneCeny = new HashMap<>();
        Map<Produkt.Typ, List<Double>> minimalneCeny = new HashMap<>();
        for (Produkt.Typ produkt : Produkt.Typ.values()) {
            historiaŚrednichCen.put(produkt, new ArrayList<>());
            sprzedanePrzezRobotników.put(produkt, new ArrayList<>());
            historiaSprzedaży.put(produkt, new ArrayList<>());
            minimalneCeny.put(produkt, new ArrayList<>());
            maksymalneCeny.put(produkt, new ArrayList<>());
        }
        for (Produkt.Typ produkt : cenyProduktów.keySet()) {
            historiaŚrednichCen.get(produkt).add(cenyProduktów.get(produkt));
            minimalneCeny.get(produkt).add(cenyProduktów.get(produkt));
            maksymalneCeny.get(produkt).add(cenyProduktów.get(produkt));
        }
        for (Robotnik robotnik : robotnicy) {
            robotnik.inicjalizuj();
        }
        for (Spekulant spekulant : spekulanci) {
            spekulant.inicjalizuj();
        }
        historia = new HistoriaGiełdy(historiaŚrednichCen, maksymalneCeny, minimalneCeny, historiaSprzedaży,
                sprzedanePrzezRobotników, new ArrayList<>(), new ArrayList<>());
        aktualizujHistorieAgentów();
    }

    private void aktualizujHistorieAgentów() {
        List<Spekulant> kopiaSpekulanci = new ArrayList<>();
        for (int i = 0; i < spekulanci.size(); i++) {
            kopiaSpekulanci.add(null);
        }
        Collections.copy(kopiaSpekulanci, spekulanci);
        historia.spekulanci.add(kopiaSpekulanci);
        List<Robotnik> kopiaRobotnicy = new ArrayList<>();
        for (int i = 0; i < robotnicy.size(); i++) {
            kopiaRobotnicy.add(null);
        }
        Collections.copy(kopiaRobotnicy, robotnicy);
        historia.robotnicy.add(kopiaRobotnicy);
    }


    public List<Spekulant> getSpekulanci() {
        return spekulanci;
    }

    public List<Robotnik> getRobotnicy() {
        return robotnicy;
    }

    public enum Typ {
        @Json(name = "kapitalistycza")
        KAPITALISTYCZNA,
        @Json(name = "socjalistyczna")
        SOCJALISTYCZNA,
        @Json(name = "zrowanowazona")
        ZRÓWNOWAŻONA
    }

    public int dzieńSymulacji() {
        return dzieńSymulacji;
    }

    public Map<Produkt.Typ, List<Double>> historiaŚrednichCen() {
        return historia.średnieCeny;
    }

    public int karaZaBrakUbrań() {
        return karaZaBrakUbrań;
    }

    public HistoriaGiełdy historiaGiełdy() {
        return historia;
    }

    public Map<Produkt.Typ, List<Integer>> historiaSprzedaży() {
        return historia.sprzedano;
    }

    public Map<Produkt.Typ, List<Integer>> sprzedanePrzezRobotników() {
        return historia.sprzedanePrzezRobotników;
    }

    public void dodajOfertyRobotników() {
        List<OfertaRobotnika> ofertyRobotników = new ArrayList<>();
        sprzedażRobotnicy = new HashMap<>();
        for (Robotnik robotnik : robotnicy) {
            ofertyRobotników.addAll(robotnik.wystawOferty(new GiełdaWidok(this)));
        }
        for (Produkt.Typ produkt : Produkt.Typ.values()) {
            sprzedażRobotnicy.put(produkt, new ArrayList<>());
            historia.sprzedanePrzezRobotników.get(produkt).add(0);
        }
        for (OfertaRobotnika oferta : ofertyRobotników) {
            sprzedażRobotnicy.get(oferta.produkt()).add(oferta);
            historia.sprzedanePrzezRobotników.get(oferta.produkt()).set(dzieńSymulacji - 1,
                    historia.sprzedanePrzezRobotników.get(oferta.produkt()).get(dzieńSymulacji - 1) + oferta.liczba());
        }
        for (Produkt.Typ produkt : Produkt.Typ.values()) {
            sprzedażRobotnicy.get(produkt).sort(new OfertaRobotnikaKomparator());
        }
    }

    public void zakupZasobyDlaRobotników() {
        List<Oferta> doKupienia;
        for (Robotnik robotnik : robotnicy) {
            doKupienia = robotnik.wystawOfertyKupna();
            List<OfertaSpekulanta> ofertyProduktu;
            int kupione;
            for (Oferta kupowane : doKupienia) {
                ofertyProduktu = sprzedażSpekulanci.get(kupowane.produkt());
                if (ofertyProduktu != null) {
                    for (int i = 0; i < ofertyProduktu.size() && kupowane.liczba() > 0; i++) {
                        if (ofertyProduktu.get(i).cenaZaSztuke() < robotnik.diamenty()) {
                            kupione = Math.min((int) Math.floor(robotnik.diamenty() /
                                            ofertyProduktu.get(i).cenaZaSztuke()),
                                    Math.min(ofertyProduktu.get(i).liczba(), kupowane.liczba()));
                            robotnik.dodajZasób(kupowane.produkt(), kupione, ofertyProduktu.get(i).jakość());
                            kupowane.kup(kupione);
                            for (Spekulant spekulant : spekulanci) {
                                if (spekulant.id() == ofertyProduktu.get(i).idWystawiajacego()) {
                                    spekulant.zmieńDiamenty(ofertyProduktu.get(i).cenaZaSztuke() * kupione);
                                }
                            }
                            robotnik.zmieńDiamenty(-(ofertyProduktu.get(i).cenaZaSztuke() * kupione));
                            // Aktualizacja statystyk dnia
                            aktualizujRekordyDnia(kupowane.produkt(), ofertyProduktu.get(i).cenaZaSztuke());
                            wartośćSprzedanych[kupowane.produkt().ordinal()] +=
                                    kupione * ofertyProduktu.get(i).cenaZaSztuke();
                            sprzedaneDzisiaj[kupowane.produkt().ordinal()] += kupione;
                            ofertyProduktu.get(i).kup(kupione);
                        }
                    }
                    ofertyProduktu.removeIf(oferta -> oferta.liczba() == 0);
                }
            }
        }
    }


    public void dodajOfertySpekulantów() {
        List<OfertaSpekulanta> ofertySpekulantów = new ArrayList<>();
        sprzedażSpekulanci = new HashMap<>();
        for (Spekulant spekulant : spekulanci) {
            ofertySpekulantów.addAll(spekulant.wystawOfertySprzedaży(new GiełdaWidok(this)));
        }
        for (Produkt.Typ produkt : Produkt.Typ.values()) {
            sprzedażSpekulanci.put(produkt, new ArrayList<>());
        }
        for (OfertaSpekulanta oferta : ofertySpekulantów) {
            sprzedażSpekulanci.get(oferta.produkt()).add(oferta);
        }
        for (Produkt.Typ produkt : Produkt.Typ.values()) {
            sprzedażSpekulanci.get(produkt).sort(new OfertaSpekulantaKomparator());
        }
    }

    private void aktualizujRekordyDnia(Produkt.Typ produkt, double cena) {
        if (najniższeCeny[produkt.ordinal()] > cena ||
                najniższeCeny[produkt.ordinal()] == -1) {
            najniższeCeny[produkt.ordinal()] = cena;
            historia.minimalneCeny.get(produkt).set(dzieńSymulacji(), cena);
        }
        if (historia.maksymalneCeny.get(produkt).get(dzieńSymulacji()) < cena ||
                historia.maksymalneCeny.get(produkt).get(dzieńSymulacji()) == -1) {
            historia.maksymalneCeny.get(produkt).set(dzieńSymulacji(), cena);
        }
    }

    public void zakupZasobyDlaSpekulantów() {
        List<OfertaSpekulanta> doKupienia;
        for (Spekulant spekulant : spekulanci) {
            doKupienia = spekulant.wystawOfertyKupna(new GiełdaWidok(this));
            List<OfertaRobotnika> ofertyProduktu;
            int kupione;
            for (OfertaSpekulanta kupowane : doKupienia) {
                ofertyProduktu = sprzedażRobotnicy.get(kupowane.produkt());
                for (int i = 0; i < ofertyProduktu.size() - 1 && kupowane.liczba() > 0; i--) {
                    if (kupowane.cenaZaSztuke() < spekulant.diamenty()) {
                        kupione = Math.min((int) Math.floor(spekulant.diamenty() / kupowane.cenaZaSztuke()),
                                Math.min(ofertyProduktu.get(i).liczba(), kupowane.liczba()));
                        spekulant.dodajZasób(kupowane.produkt(), kupione, ofertyProduktu.get(i).jakość());
                        kupowane.kup(kupione);
                        for (Robotnik robotnik : robotnicy) {
                            if (robotnik.id() == ofertyProduktu.get(i).idWystawiajacego()) {
                                robotnik.zmieńDiamenty(kupowane.cenaZaSztuke() * kupione);
                                break;
                            }
                        }
                        // Aktualizacja statystyk dnia
                        aktualizujRekordyDnia(kupowane.produkt(), kupowane.cenaZaSztuke());
                        sprzedaneDzisiaj[kupowane.produkt().ordinal()] += kupione;
                        wartośćSprzedanych[kupowane.produkt().ordinal()] += kupione * kupowane.cenaZaSztuke();
                        ofertyProduktu.get(i).kup(kupione);
                        if (ofertyProduktu.get(i).liczba() == 0) {
                            ofertyProduktu.remove(i);
                        }
                        spekulant.zmieńDiamenty(-(kupowane.cenaZaSztuke() * kupione));
                    }
                }
            }
        }
    }


    public void kupOdRobotnika(OfertaRobotnika oferta) {
        double cena = najniższeCeny[oferta.produkt().ordinal()];
        if (cena == -1) {
            cena = historia.średnieCeny.get(oferta.produkt()).get(0);
        }
        for (Robotnik robotnik : robotnicy) {
            if (robotnik.id() == oferta.idWystawiajacego()) {
                robotnik.zmieńDiamenty(cena * oferta.liczba());
                break;
            }
        }
        sprzedaneDzisiaj[oferta.produkt().ordinal()] += oferta.liczba();
        wartośćSprzedanych[oferta.produkt().ordinal()] += oferta.liczba() * cena;
    }

    public void wykupPozostałeOferty() {
        for (Produkt.Typ produkt : Produkt.Typ.values()) {
            List<OfertaRobotnika> oferty = sprzedażRobotnicy.get(produkt);
            for (OfertaRobotnika oferta : oferty) {
                kupOdRobotnika(oferta);
            }
        }
    }

    public void zwróćSpekulantowi(OfertaSpekulanta oferta) {
        for (Spekulant spekulant : spekulanci) {
            if (spekulant.id() == oferta.idWystawiajacego()) {
                spekulant.dodajZasób(oferta.produkt(), oferta.liczba(), oferta.jakość());
                break;
            }
        }
    }

    public void zwróćNiesprzedaneZasoby() {
        for (Produkt.Typ produkt : Produkt.Typ.values()) {
            List<OfertaSpekulanta> oferty = sprzedażSpekulanci.get(produkt);
            for (OfertaSpekulanta oferta : oferty) {
                zwróćSpekulantowi(oferta);
            }
        }
    }

    public void zakończDzień() {
        for (Produkt.Typ produkt : Produkt.Typ.values()) {
            if (produkt != Produkt.Typ.DIAMENTY) {
                if (sprzedaneDzisiaj[produkt.ordinal()] == 0) {
                    historia.średnieCeny.get(produkt).add(historia.średnieCeny.get(produkt).get(0));
                } else {
                    historia.średnieCeny.get(produkt).add(
                            wartośćSprzedanych[produkt.ordinal()] / sprzedaneDzisiaj[produkt.ordinal()]);
                }
                historia.sprzedano.get(produkt).add(sprzedaneDzisiaj[produkt.ordinal()]);
                if (historia.maksymalneCeny.get(produkt).get(dzieńSymulacji()) == -1.0) {
                    historia.maksymalneCeny.get(produkt).set(dzieńSymulacji(),
                            historia.maksymalneCeny.get(produkt).get(0));
                }
                if (historia.minimalneCeny.get(produkt).get(dzieńSymulacji()) == -1.0) {
                    historia.minimalneCeny.get(produkt).set(dzieńSymulacji(),
                            historia.minimalneCeny.get(produkt).get(0));
                }
            }
        }
    }

    public void rozpocznijDzień() {
        dzieńSymulacji++;
        posortujAgentów();
        sprzedażSpekulanci = new HashMap<>();
        sprzedażRobotnicy = new HashMap<>();
        for (Produkt.Typ produkt : Produkt.Typ.values()) {
            sprzedażSpekulanci.put(produkt, new ArrayList<>());
            sprzedażRobotnicy.put(produkt, new ArrayList<>());
            historia.maksymalneCeny.get(produkt).add(-1.0);
            historia.minimalneCeny.get(produkt).add(-1.0);
        }
        sprzedaneDzisiaj = new Integer[Produkt.Typ.values().length];
        Arrays.fill(sprzedaneDzisiaj, 0);
        wartośćSprzedanych = new Double[Produkt.Typ.values().length];
        Arrays.fill(wartośćSprzedanych, 0.0);
        najniższeCeny = new Double[Produkt.Typ.values().length];
        Arrays.fill(najniższeCeny, -1.0);
    }

    public void wykorzystajZasobyRobotników() {
        robotnicy.removeIf(robotnik -> !robotnik.zakończDzień());
    }

    public void symulujDzień() {
        rozpocznijDzień();
        dodajOfertyRobotników();
        dodajOfertySpekulantów();
        zakupZasobyDlaRobotników();
        zakupZasobyDlaSpekulantów();
        wykupPozostałeOferty();
        zwróćNiesprzedaneZasoby();
        wykorzystajZasobyRobotników();
        zakończDzień();
        aktualizujHistorieAgentów();
    }


}
