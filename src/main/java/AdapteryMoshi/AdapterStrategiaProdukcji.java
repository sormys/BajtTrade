package AdapteryMoshi;

import Agenci.Robotnicy.StrategieRobotnikow.Produkcji.*;
import com.squareup.moshi.ToJson;
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory;

public class AdapterStrategiaProdukcji {

    public static PolymorphicJsonAdapterFactory<StrategiaProdukcji> twórz() {
        return PolymorphicJsonAdapterFactory.of(StrategiaProdukcji.class, "typ")
                .withSubtype(Chciwy.class, "chciwy")
                .withSubtype(Krótkowzroczny.class, "krotkowzroczny")
                .withSubtype(Losowy.class, "losowy")
                .withSubtype(Perspektywiczny.class, "perspektywiczny")
                .withSubtype(Średniak.class, "sredniak");
    }

    @ToJson
    public static String toJson(StrategiaProdukcji strategia) {
        if (strategia.getClass() == Chciwy.class) {
            return "\"chciwy\"";
        } else if (strategia.getClass() == Krótkowzroczny.class) {
            return "\"krotkowzroczny\"";
        } else if (strategia.getClass() == Losowy.class) {
            return "\"losowy\"";
        } else if (strategia.getClass() == Perspektywiczny.class) {
            return "\"perspektywiczny\",\n\t\t\t\"historia_perspektywy\": " +
                    ((Perspektywiczny) strategia).historiaPerspektywy();
        } else if (strategia.getClass() == Średniak.class) {
            return "\"sredniak\",\n\t\t\t\"historia_sredniej_produkcji\": " +
                    ((Średniak) strategia).historiaŚredniejProdukcji();
        }
        return null;
    }
}
