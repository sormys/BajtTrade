package AdapteryMoshi;

import Agenci.Robotnicy.StrategieRobotnikow.Kariery.Konserwatysta;
import Giełda.Kapitalistyczna;
import Giełda.RodzajGiełdy;
import Giełda.Socjalistyczna;
import Giełda.Zrównoważona;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.ToJson;

public class AdapterRodzajGiełdy {
    @FromJson
    public RodzajGiełdy fromJson(String string) {
        switch (string) {
            case "socjalistyczna":
                return new Socjalistyczna();
            case "kapitalistyczna":
                return new Kapitalistyczna();
            case "zrownowazona":
                return new Zrównoważona();
            default:
                throw new JsonDataException("Zla nazwa strategi zmiany kariery!\n");
        }
    }

    @ToJson
    public String toJson(RodzajGiełdy strategia) {
        if (strategia instanceof Konserwatysta) {
            return "konserwatysta";
        } else {
            return "rewolucjonista";
        }
    }
}
