package Giełda;

import Agenci.Robotnicy.Robotnik;
import Agenci.Spekulanci.Spekulant;

import java.util.List;

public interface RodzajGiełdy {
    void posortujAgentów(List<Robotnik> robotnicy, List<Spekulant> spekulanci, int dzieńSymulacji);
}
