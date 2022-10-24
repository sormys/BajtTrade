package Giełda;

import Agenci.Robotnicy.Robotnik;
import Agenci.Spekulanci.Spekulant;
import Produkty.Produkt;

import java.util.List;
import java.util.Map;

public class HistoriaGiełdy {

    public List<List<Spekulant>> spekulanci;
    public List<List<Robotnik>> robotnicy;
    public Map<Produkt.Typ, List<Double>> średnieCeny;
    public Map<Produkt.Typ, List<Double>> maksymalneCeny;
    public Map<Produkt.Typ, List<Double>> minimalneCeny;

    public Map<Produkt.Typ, List<Integer>> sprzedano;

    public Map<Produkt.Typ, List<Integer>> sprzedanePrzezRobotników;

    public HistoriaGiełdy(Map<Produkt.Typ, List<Double>> średnieCeny,
                          Map<Produkt.Typ, List<Double>> maksymalneCeny,
                          Map<Produkt.Typ, List<Double>> minimalneCeny,
                          Map<Produkt.Typ, List<Integer>> sprzedano,
                          Map<Produkt.Typ, List<Integer>> sprzedanePrzezRobotników,
                          List<List<Spekulant>> spekulanci,
                          List<List<Robotnik>> robotnicy) {
        this.średnieCeny = średnieCeny;
        this.maksymalneCeny = maksymalneCeny;
        this.minimalneCeny = minimalneCeny;
        this.sprzedano = sprzedano;
        this.sprzedanePrzezRobotników = sprzedanePrzezRobotników;
        this.spekulanci = spekulanci;
        this.robotnicy = robotnicy;
    }

}
