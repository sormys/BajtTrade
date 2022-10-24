package Giełda;

import Agenci.Agent;
import Agenci.Robotnicy.Robotnik;
import Agenci.Spekulanci.Spekulant;

import java.util.List;

public class Zrównoważona implements RodzajGiełdy {

    @Override
    public void posortujAgentów(List<Robotnik> robotnicy, List<Spekulant> spekulanci, int dzieńSymulacji) {
        if (dzieńSymulacji % 2 == 0) {
            robotnicy.sort(Agent::komparator);
            spekulanci.sort(Agent::komparator);
        } else {
            robotnicy.sort((a, b) -> Agent.komparator(b, a));
            spekulanci.sort((a, b) -> Agent.komparator(b, a));
        }
    }
}
