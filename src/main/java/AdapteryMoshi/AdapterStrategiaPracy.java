package AdapteryMoshi;

import Agenci.Robotnicy.StrategieRobotnikow.Pracy.*;
import com.squareup.moshi.ToJson;
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory;

public class AdapterStrategiaPracy {
    public static PolymorphicJsonAdapterFactory<StrategiaPracy> twórz() {
        return PolymorphicJsonAdapterFactory.of(StrategiaPracy.class, "typ")
                .withSubtype(Student.class, "student")
                .withSubtype(Okresowy.class, "okresowy")
                .withSubtype(Oszczędny.class, "oszczedny")
                .withSubtype(Pracuś.class, "pracus")
                .withSubtype(Rozkładowy.class, "rozkladowy");
    }

    @ToJson
    public static String toJson(StrategiaPracy strategia) {
        if (strategia.getClass() == Student.class) {
            return "\"student\",\n\t\t\t\"zapas\": " + ((Student) strategia).zapas() + ",\n\t\t\t\"okres\": "
                    + ((Student) strategia).okres();
        } else if (strategia.getClass() == Okresowy.class) {
            return "\"oszczedny\",\n\t\t\t\"okresowosc_nauki\": " + ((Okresowy) strategia).okresowośćNauki();
        } else if (strategia.getClass() == Oszczędny.class) {
            return "\"oszczedny\",\n\t\t\t\"limit_diamentow\": " + ((Oszczędny) strategia).limitDiamentów();
        } else if (strategia.getClass() == Pracuś.class) {
            return "\"pracus\"";
        } else if (strategia.getClass() == Rozkładowy.class) {
            return "\"rozkladowy\"";
        }
        return null;
    }
}
