package spaceStation.models.mission;

import spaceStation.models.astronauts.Astronaut;
import spaceStation.models.planets.Planet;

import java.util.Collection;
import java.util.List;

public class MissionImpl implements Mission{


    @Override
    public void explore(Planet planet, List<Astronaut> astronauts) {
        for (int a = 0; a < astronauts.size(); a++) {
            Astronaut currentAustronaut = astronauts.get(a);

            for (int item = 0; item < planet.getItems().size(); item++) {
                String currItem = planet.getItems().get(item);

                currentAustronaut.getBag().getItems().add(currItem);
                planet.getItems().remove(currItem);
                item--;
                currentAustronaut.breath();
                if (!currentAustronaut.canBreath()) {
                    astronauts.remove(currentAustronaut);
                    a--;
                    break;
                }
            }
        }

        /*for (Astronaut astronaut : astronauts) {

        }*/
    }
}
