package AdapteryMoshi;

import Agenci.Robotnicy.StrategieRobotnikow.Kariery.Konserwatysta;
import Agenci.Robotnicy.StrategieRobotnikow.Kariery.Rewolucjonista;
import Agenci.Robotnicy.StrategieRobotnikow.Kariery.StrategiaKariery;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.ToJson;

public class AdapterStrategiaKariery {

    @FromJson
    public StrategiaKariery fromJson(String string) {
        switch (string) {
            case "konserwatysta":
                return new Konserwatysta();
            case "rewolucjonista":
                return new Rewolucjonista();
            default:
                throw new JsonDataException("Zla nazwa strategi zmiany kariery!\n");
        }
    }

    @ToJson
    public static String toJson(StrategiaKariery strategia) {
        if (strategia instanceof Konserwatysta) {
            return "\"konserwatysta\"";
        } else {
            return "\"rewolucjonista\"";
        }
    }
}
