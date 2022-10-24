package Giełda;

import Agenci.Agent;
import Agenci.Robotnicy.Robotnik;
import Agenci.Spekulanci.Spekulant;

import java.util.List;

public class Kapitalistyczna implements RodzajGiełdy {

    @Override
    public void posortujAgentów(List<Robotnik> robotnicy, List<Spekulant> spekulanci, int dzieńSymulacji) {
        robotnicy.sort(Agent::komparator);
        spekulanci.sort(Agent::komparator);
    }
}
