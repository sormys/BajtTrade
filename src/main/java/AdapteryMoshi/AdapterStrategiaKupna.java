package AdapteryMoshi;

import Agenci.Robotnicy.StrategieRobotnikow.Kupna.*;
import com.squareup.moshi.ToJson;
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory;

public class AdapterStrategiaKupna {
    public static PolymorphicJsonAdapterFactory<StrategiaKupna> twórz() {
        return PolymorphicJsonAdapterFactory.of(StrategiaKupna.class, "typ")
                .withSubtype(Czyścioszek.class, "czyscioszek")
                .withSubtype(Gadżeciarz.class, "gadzeciarz")
                .withSubtype(Technofob.class, "technofob")
                .withSubtype(Zmechanizowany.class, "zmechanizowany");
    }

    @ToJson
    public static String toJson(StrategiaKupna strategia) {
        if (strategia.getClass() == Czyścioszek.class) {
            return "\"czyscioszek\"";
        } else if (strategia.getClass() == Technofob.class) {
            return "\"technofob\"";
        } else if (strategia.getClass() == Zmechanizowany.class) {
            return "\"zmechanizowany\",\n\t\t\t\t\"liczba_narzedzi\": " + ((Zmechanizowany) strategia).liczbaNarzedzi();
        } else if (strategia.getClass() == Gadżeciarz.class) {
            return "\"gadzeciarz\",\n\n\t\t\t\t\"liczba_narzedzi\": " + ((Gadżeciarz) strategia).liczbaNarzedzi();
        }
        return null;
    }
}
