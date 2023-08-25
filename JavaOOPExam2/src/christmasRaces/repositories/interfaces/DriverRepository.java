package christmasRaces.repositories.interfaces;

import christmasRaces.entities.drivers.Driver;
import christmasRaces.entities.drivers.DriverImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DriverRepository implements Repository<Driver>{
    //private List<Driver> drivers;
    private List<Driver> drivers;

    public DriverRepository(){
        //this.driverRepository = new DriverRepository();
        this.drivers = new ArrayList<>();
    }

    @Override
    public Driver getByName(String name) {
        return drivers.stream()
                .filter(d -> d.getName().equals(name))
                .findFirst()
                .orElse(null);

    }

    @Override
    public Collection<Driver> getAll() {
        return this.drivers;
    }

    @Override
    public void add(Driver model) {
        boolean result = this.drivers.stream()
                .noneMatch(d -> d.getName().equals(model.getName()));

        if(result) {
            this.drivers.add(model);
        }
    }

    @Override
    public boolean remove(Driver model) {
        return this.drivers.remove(model);
    }
}
