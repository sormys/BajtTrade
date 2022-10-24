package Giełda;

import Agenci.Agent;
import Agenci.Robotnicy.Robotnik;
import Agenci.Spekulanci.Spekulant;

import java.util.List;

public class Socjalistyczna implements RodzajGiełdy {
    @Override
    public void posortujAgentów(List<Robotnik> robotnicy, List<Spekulant> spekulanci, int dzieńSymulacji) {
        robotnicy.sort((a, b) -> Agent.komparator(b, a));
        spekulanci.sort((a, b) -> Agent.komparator(b, a));
    }
}
