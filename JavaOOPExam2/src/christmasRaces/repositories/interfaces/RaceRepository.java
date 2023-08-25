package christmasRaces.repositories.interfaces;

import christmasRaces.entities.races.Race;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RaceRepository implements Repository<Race>{
    private List<Race> races;

    public RaceRepository(){
        //this.raceRepository = new RaceRepository();
        this.races = new ArrayList<>();
    }

    @Override
    public Race getByName(String name) {
        return races.stream()
                .filter(d -> d.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<Race> getAll() {
        return this.races;
    }

    @Override
    public void add(Race model) {
        boolean result = this.races.stream()
                .noneMatch(r -> r.getName().equals(model.getName()));

        if(result) {
            this.races.add(model);
        }
    }

    @Override
    public boolean remove(Race model) {
        return this.races.remove(model);
    }

}
