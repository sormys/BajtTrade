package AdapteryMoshi;


import Produkty.Produkt;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.ToJson;

import java.io.IOException;
import java.util.*;

public class AdapterZasoby {
    @FromJson
    public Map<Produkt.Typ, List<Integer>> fromJson(JsonReader reader) throws IOException {
        Map<Produkt.Typ, List<Integer>> zasoby = new HashMap<>();
        reader.beginObject();
        int liczba;
        while (reader.hasNext()) {
            switch (reader.nextName()) {
                case "ubrania":
                    liczba = reader.nextInt();
                    zasoby.put(Produkt.Typ.UBRANIA, new ArrayList<>());
                    for (int i = 0; i < liczba; i++) {
                        zasoby.get(Produkt.Typ.UBRANIA).add(1);
                    }
                    break;
                case "programy":
                    liczba = reader.nextInt();
                    zasoby.put(Produkt.Typ.PROGRAMY, new ArrayList<>());
                    for (int i = 0; i < liczba; i++) {
                        zasoby.get(Produkt.Typ.PROGRAMY).add(1);
                    }
                    break;
                case "narzedzia":
                    liczba = reader.nextInt();
                    zasoby.put(Produkt.Typ.NARZĘDZIA, new ArrayList<>());
                    for (int i = 0; i < liczba; i++) {
                        zasoby.get(Produkt.Typ.NARZĘDZIA).add(1);
                    }
                    break;
                case "diamenty":
                    liczba = reader.nextInt();
                    zasoby.put(Produkt.Typ.DIAMENTY, new ArrayList<>());
                    zasoby.get(Produkt.Typ.DIAMENTY).add(liczba);
                    break;
                case "jedzenie":
                    liczba = reader.nextInt();
                    zasoby.put(Produkt.Typ.JEDZENIE, new ArrayList<>());
                    zasoby.get(Produkt.Typ.JEDZENIE).add(liczba);
                    break;
                default:
                    throw new JsonDataException("Nieznany zasob!\n");
            }
        }
        reader.endObject();
        return zasoby;
    }

    @ToJson
    public static String toJson(Map<Produkt.Typ, List<Integer>> zasoby) {
        StringBuilder wynik = new StringBuilder();
        boolean pierwszyZasób = true;
        for (Produkt.Typ produkt : zasoby.keySet()) {
            if (produkt != Produkt.Typ.DIAMENTY && produkt != Produkt.Typ.JEDZENIE) {
                if (!pierwszyZasób)
                    wynik.append(",\n\t\t");
                else
                    wynik.append("\n\t\t");

                wynik.append("\t\t\"").append(AdapterHistoriiGiełdy.interpreterProduktu(produkt)).append("\": ");
                wynik.append("[");
                if (zasoby.get(produkt) != null && !zasoby.get(produkt).isEmpty()) {

                    int tejSamejJakości = 1;
                    int ostatniaJakość = 0;
                    int i = 0;
                    for (i = 0; i < zasoby.get(produkt).size() - 1; i++) {
                        if (zasoby.get(produkt).get(i).equals(zasoby.get(produkt).get(i + 1))) {
                            tejSamejJakości++;
                        } else {
                            if (ostatniaJakość == 0) {
                                wynik.append(tejSamejJakości);
                            } else {
                                wynik.append(", 0".repeat(Math.max(0, zasoby.get(produkt).get(i) - ostatniaJakość)));
                                wynik.append(", ").append(tejSamejJakości);
                            }
                            ostatniaJakość = zasoby.get(produkt).get(i);
                            tejSamejJakości = 1;
                        }
                    }
                    if (ostatniaJakość == 0) {
                        wynik.append(tejSamejJakości);
                    } else {
                        wynik.append(", 0".repeat(Math.max(0, zasoby.get(produkt).get(i) - ostatniaJakość)));
                        wynik.append(", ").append(tejSamejJakości);
                    }
                }
                wynik.append("]");
                pierwszyZasób = false;
            }
        }
        return wynik.toString();
    }
}
