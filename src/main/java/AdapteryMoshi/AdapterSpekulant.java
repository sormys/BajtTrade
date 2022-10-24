package AdapteryMoshi;


import Agenci.Spekulanci.*;
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory;

public class AdapterSpekulant {
    public static PolymorphicJsonAdapterFactory<StrategiaSpekulanta> twórz() {
        return PolymorphicJsonAdapterFactory.of(StrategiaSpekulanta.class, "typ")
                .withSubtype(SpekulantRegulujący.class, "regulujacy_rynek")
                .withSubtype(SpekulantWypukły.class, "wypukly")
                .withSubtype(SpekulantŚredni.class, "sredni");
    }

}
