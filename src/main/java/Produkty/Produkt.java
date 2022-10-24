package Produkty;

import com.squareup.moshi.Json;

public abstract class Produkt {
    private int jakość;

    public Produkt(int jakość) {
        this.jakość = jakość;
    }

    public enum Typ {
        @Json(name = "jedzenie")
        JEDZENIE,
        @Json(name = "ubrania")
        UBRANIA,
        @Json(name = "programy")
        PROGRAMY,
        @Json(name = "narzedzia")
        NARZĘDZIA,
        @Json(name = "diamenty")
        DIAMENTY
    }

    public int jakość() {
        return jakość;
    }


    public boolean użyj(int liczbaUżyć) {
        if (liczbaUżyć > this.jakość)
            return false;
        this.jakość -= liczbaUżyć;
        return true;
    }

    public static Produkt nowyProdukt(Typ produkt, int jakosc) {
        switch (produkt) {
            case PROGRAMY:
                return new ProgramyKomputerowe(jakosc);
            case UBRANIA:
                return new Ubrania(jakosc);
            case JEDZENIE:
                return new Jedzenie(jakosc);
            case NARZĘDZIA:
                return new Narzędzia(jakosc);
            default:
                return null;
        }
    }

}
