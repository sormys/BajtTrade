package AdapteryMoshi;

import Agenci.Spekulanci.SpekulantRegulujący;
import Agenci.Spekulanci.SpekulantWypukły;
import Agenci.Spekulanci.SpekulantŚredni;
import Agenci.Spekulanci.StrategiaSpekulanta;
import com.squareup.moshi.ToJson;

public class AdapterStrategiaSpekulanta {
    @ToJson
    public static String toJson(StrategiaSpekulanta strategia) {
        if (strategia.getClass() == SpekulantRegulujący.class) {
            return "\"regulujacy\"";
        } else if (strategia.getClass() == SpekulantWypukły.class) {
            return "\"wypukly\"";
        } else if (strategia.getClass() == SpekulantŚredni.class) {
            return "\"sredni\",\n\t\t\t\"historia_spekulanta_sredniego\": " +
                    ((SpekulantŚredni) strategia).historiaSpekulantaŚredniego();
        }
        return null;
    }
}
