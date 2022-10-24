import AdapteryMoshi.*;

import JSON.JsonStandard;

import Symulacja.Symulacja;
import com.squareup.moshi.*;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;


public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            return;
        }
        File wejście = new File(args[0]);
        File wyjście = new File(args[1]);

        String plik = new String(Files.readAllBytes(Path.of(wejście.getPath())));

        Moshi moshi = new Moshi.Builder()
                .add(AdapterStrategiaKupna.twórz())
                .add(new AdapterStrategiaKariery())
                .add(AdapterStrategiaPracy.twórz())
                .add(AdapterStrategiaProdukcji.twórz())
                .add(AdapterSpekulant.twórz())
                .add(new AdapterZasoby())
                .add(new AdapterRodzajGiełdy())
                .build();

        JsonAdapter<JsonStandard> jsonAdapter = moshi.adapter(JsonStandard.class);
        JsonStandard dane = jsonAdapter.fromJson(plik);

        assert dane != null;
        Symulacja symulacja = new Symulacja(dane.info.długość, dane.info.karaZaBrakUbrań, dane.info.cenyProduktów,
                dane.robotnicy, dane.spekulanci, dane.info.rodzajGiełdy);
        symulacja.symuluj();


        JsonAdapter<Symulacja> jsonAdapterSymulacja = moshi.adapter(Symulacja.class);
        AdapterHistoriiGiełdy adapter = new AdapterHistoriiGiełdy();
        Files.writeString(Path.of(wyjście.getPath()), adapter.toJson(symulacja.historiaGiełdy()));
    }
}
