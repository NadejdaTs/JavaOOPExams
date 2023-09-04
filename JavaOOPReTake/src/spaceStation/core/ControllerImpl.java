package spaceStation.core;

import spaceStation.models.astronauts.Astronaut;
import spaceStation.models.astronauts.Biologist;
import spaceStation.models.astronauts.Geodesist;
import spaceStation.models.astronauts.Meteorologist;
import spaceStation.models.mission.Mission;
import spaceStation.models.mission.MissionImpl;
import spaceStation.models.planets.Planet;
import spaceStation.models.planets.PlanetImpl;
import spaceStation.repositories.AstronautRepository;
import spaceStation.repositories.PlanetRepository;

import java.awt.desktop.SystemEventListener;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static spaceStation.common.ConstantMessages.*;
import static spaceStation.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {
    private AstronautRepository astronautRepository;
    private PlanetRepository planetRepository;
    private int countExplorePlanets;

    public ControllerImpl(){
        this.astronautRepository = new AstronautRepository();
        this.planetRepository = new PlanetRepository();
    }

    @Override
    public String addAstronaut(String type, String astronautName) {
        Astronaut astronaut;
        switch(type){
            case "Biologist":
                astronaut = new Biologist(astronautName);
                break;
            case "Geodesist":
                astronaut = new Geodesist(astronautName);
                break;
            case "Meteorologist":
                astronaut = new Meteorologist(astronautName);
                break;
            default:
                throw new IllegalArgumentException(ASTRONAUT_INVALID_TYPE);
        }
        this.astronautRepository.add(astronaut);
        return String.format(ASTRONAUT_ADDED, type, astronautName);
    }

    @Override
    public String addPlanet(String planetName, String... items) {
        Planet planet = new PlanetImpl(planetName);
        List<String> itemList = Arrays.asList(items);

        planet.getItems().addAll(itemList);
        return String.format(PLANET_ADDED, planetName);
    }

    @Override
    public String retireAstronaut(String astronautName) {
        boolean result = this.astronautRepository.getModels().stream()
                .noneMatch(a -> a.getName().equals(astronautName));

        if(result){
            throw new IllegalArgumentException(String.format(ASTRONAUT_DOES_NOT_EXIST, astronautName));
        }
        Astronaut astronaut = this.astronautRepository.findByName(astronautName);
        this.astronautRepository.remove(astronaut);
        return String.format(ASTRONAUT_RETIRED, astronautName);
    }

    @Override
    public String explorePlanet(String planetName) {
        List<Astronaut> suitableAstronauts = this.astronautRepository.getModels().stream()
                .filter(a -> a.getOxygen() > 60)
                .collect(Collectors.toList());

        if(suitableAstronauts.isEmpty()){
            throw new IllegalArgumentException(PLANET_ASTRONAUTS_DOES_NOT_EXISTS);
        }
        int countBeforeMission = suitableAstronauts.size();

        Mission mission = new MissionImpl();
        Planet planet = this.planetRepository.findByName(planetName);
        mission.explore(planet, suitableAstronauts);

        countExplorePlanets++;
        int countAfterMission = suitableAstronauts.size();

        return String.format(PLANET_EXPLORED, planetName, countBeforeMission - countAfterMission);
    }

    @Override
    public String report() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(REPORT_PLANET_EXPLORED, countExplorePlanets)).append(System.lineSeparator());
        sb.append(String.format(REPORT_ASTRONAUT_INFO)).append(System.lineSeparator());

        this.astronautRepository.getModels().forEach(a -> {
            sb.append(String.format(REPORT_ASTRONAUT_NAME, a.getName())).append(System.lineSeparator());
            sb.append(String.format(REPORT_ASTRONAUT_OXYGEN, a.getOxygen())).append(System.lineSeparator());
            if (a.getBag().getItems().size() == 0) {
                sb.append(String.format(REPORT_ASTRONAUT_BAG_ITEMS, "none")).append(System.lineSeparator());
            } else{
                Collection<String> items = a.getBag().getItems();
                sb.append(String.format(REPORT_ASTRONAUT_BAG_ITEMS, String.join(REPORT_ASTRONAUT_BAG_ITEMS_DELIMITER, items))).append(System.lineSeparator());
            }
        });


        return sb.toString().trim();
    }
}
