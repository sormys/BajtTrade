package AdapteryMoshi;

import Agenci.Agent;
import Agenci.Robotnicy.Robotnik;
import Agenci.Spekulanci.Spekulant;
import Giełda.HistoriaGiełdy;
import Produkty.Produkt;
import Symulacja.Symulacja;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.ToJson;

import java.io.IOException;

public class AdapterHistoriiGiełdy {
    @FromJson
    public Symulacja fromJson(String string) throws IOException {
        Moshi moshi = new Moshi.Builder().build();
        return moshi.adapter(Symulacja.class).fromJson(string);
    }

    public static String interpreterProduktu(Produkt.Typ produkt) {
        switch (produkt) {
            case JEDZENIE:
                return "jedzenie";
            case DIAMENTY:
                return "diamenty";
            case NARZĘDZIA:
                return "narzedzia";
            case UBRANIA:
                return "ubrania";
            case PROGRAMY:
                return "programy";
            default:
                throw new IllegalArgumentException();
        }
    }


    private String interpreterPracy(Robotnik.Praca praca) {
        switch (praca) {
            case GÓRNIK:
                return "gornik";
            case PROGRAMISTA:
                return "programista";
            case ROLNIK:
                return "rolnik";
            case INŻYNIER:
                return "inzynier";
            case RZEMIEŚLNIK:
                return "rzemieslnik";
            default:
                throw new IllegalArgumentException();
        }
    }

    public static String zasobyToString(Agent agent) {
        StringBuilder wynik = new StringBuilder();
        wynik.append("\t\t\"zasoby\": ").append("\n\t\t");
        wynik.append("\t{\n\t\t");
        wynik.append("\t\t\"").append("diamenty").append("\": ").append(agent.diamenty()).append(",\n\t\t");
        wynik.append("\t\t\"").append("jedzenie").append("\": ").append((int) agent.jedzenie()).append(",");
        wynik.append(AdapterZasoby.toJson(agent.zasoby())).append("\n\t");
        wynik.append("\t\t}\n\t\t");
        return wynik.toString();
    }

    @ToJson
    public String toJson(HistoriaGiełdy historia) {
        Moshi moshi = new Moshi.Builder().build();
        StringBuilder wynik = new StringBuilder();
        wynik.append("[\n");
        boolean pierwszyDzień = true;
        boolean pierwszyRobotnik = true;
        for (int i = 0; i < historia.spekulanci.size(); i++) {
            if (!pierwszyDzień) {
                wynik.append(",\n");
            } else {
                pierwszyDzień = false;
            }
            wynik.append("\t{\n");
            wynik.append("\t\"info\":\n\t{\n\t");
            wynik.append("\t\"dzien\": ").append(i).append(",\n\t");

            wynik.append("\t\"ceny_srednie\":\n\t\t{");
            boolean pierwszy = true;
            for (Produkt.Typ produkt : Produkt.Typ.values()) {
                if (produkt != Produkt.Typ.DIAMENTY) {
                    if (!pierwszy)
                        wynik.append(",\n\t");
                    else
                        wynik.append("\n\t");
                    wynik.append("\t\t\"").append(interpreterProduktu(produkt)).append("\": ")
                            .append(historia.średnieCeny.get(produkt).get(i));
                    pierwszy = false;
                }
            }
            wynik.append("\n\t\t},\n\t");
            wynik.append("\t\"ceny_max\":\n\t\t{");
            pierwszy = true;
            for (Produkt.Typ produkt : Produkt.Typ.values()) {
                if (produkt != Produkt.Typ.DIAMENTY) {
                    if (!pierwszy)
                        wynik.append(",\n\t");
                    else
                        wynik.append("\n\t");
                    wynik.append("\t\t\"").append(interpreterProduktu(produkt)).append("\": ")
                            .append(historia.maksymalneCeny.get(produkt).get(i));
                    pierwszy = false;
                }
            }
            wynik.append("\n\t\t},\n\t");
            wynik.append("\t\"ceny_min\":\n\t\t{");
            pierwszy = true;
            for (Produkt.Typ produkt : Produkt.Typ.values()) {
                if (produkt != Produkt.Typ.DIAMENTY) {
                    if (!pierwszy)
                        wynik.append(",\n\t");
                    else
                        wynik.append("\n\t");
                    wynik.append("\t\t\"").append(interpreterProduktu(produkt)).append("\": ")
                            .append(historia.minimalneCeny.get(produkt).get(i));
                    pierwszy = false;
                }
            }
            wynik.append("\n\t\t}\n\t");
            wynik.append("},\n\t");
            wynik.append("\"robotnicy\": [\n\t\t");
            pierwszyRobotnik = true;
            for (Robotnik robotnik : historia.robotnicy.get(i)) {
                if (!pierwszyRobotnik) {
                    wynik.append(",\n\t\t{\n\t");
                } else {
                    wynik.append("{\n\t");
                    pierwszyRobotnik = false;
                }
                wynik.append("\t\t\"id\": ").append(robotnik.id()).append(",\n\t\t");
                wynik.append("\t\"poziom\": ").append(robotnik.poziomKariery()).append(",\n\t\t");
                wynik.append("\t\"kariera\": \"").append(interpreterPracy(robotnik.obecnaPraca())).append("\",\n\t\t");
                wynik.append("\t\"kupowanie\": ").append("\n\t\t");
                wynik.append("\t{\n\t\t");
                wynik.append("\t\t\"typ\": ").append(AdapterStrategiaKupna.toJson(robotnik.strategiaKupna()));
                wynik.append("\n\t\t\t},\n\t\t");
                wynik.append("\t\"produkcja\": ").append("\n\t\t");
                wynik.append("\t{\n\t\t");
                wynik.append("\t\t\"typ\": ").append(AdapterStrategiaProdukcji.toJson(robotnik.strategiaProdukcji()));
                wynik.append("\n\t\t\t},\n\t\t");
                wynik.append("\t\"uczenie\": ").append("\n\t\t");
                wynik.append("\t{\n\t\t");
                wynik.append("\t\t\"typ\": ").append(AdapterStrategiaPracy.toJson(robotnik.strategiaPracy()));
                wynik.append("\n\t\t\t},\n\t\t");
                wynik.append("\t\"zmiana\": ").append(AdapterStrategiaKariery.toJson(robotnik.strategiaKariery()));
                wynik.append(",\n\t\t");
                wynik.append("\t\"produktywnosc\": ").append("\n\t\t");
                wynik.append("\t{\n\t\t");
                pierwszy = true;
                for (Produkt.Typ produkt : Produkt.Typ.values()) {
                    if (!pierwszy)
                        wynik.append(",\n\t\t");
                    wynik.append("\t\t\"").append(interpreterProduktu(produkt)).append("\": ");
                    wynik.append(robotnik.produktywość().get(produkt));
                    pierwszy = false;
                }
                wynik.append("\n\t\t\t},\n\t");
                wynik.append(zasobyToString(robotnik));
                wynik.append("}");
            }
            wynik.append("\n\t],\n\t");
            wynik.append("\"spekulanci\": [\n\t\t");
            pierwszyRobotnik = true;
            for (Spekulant spekulant : historia.spekulanci.get(i)) {
                if (!pierwszyRobotnik) {
                    wynik.append(",\n\t\t{\n\t");
                } else {
                    wynik.append("{\n\t");
                    pierwszyRobotnik = false;
                }
                wynik.append("\t\t\"id\": ").append(spekulant.id()).append(",\n\t");
                wynik.append("\t\t\"kariera\": ").append(AdapterStrategiaSpekulanta.toJson(spekulant.strategia()))
                        .append(",\n\t");
                wynik.append(zasobyToString(spekulant)).append("}");
            }
            wynik.append("\n\t]\n\t");
            wynik.append("}");
        }
        wynik.append("]");
        return wynik.toString();
    }
}
