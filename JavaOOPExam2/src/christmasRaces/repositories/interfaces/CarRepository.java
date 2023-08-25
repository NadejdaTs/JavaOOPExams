package christmasRaces.repositories.interfaces;

import christmasRaces.entities.cars.Car;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CarRepository implements Repository<Car>{
    private List<Car> cars;

    public CarRepository(){
        //this.carRepository = new CarRepository();
        this.cars = new ArrayList<>();
    }

    @Override
    public Car getByName(String name) {
        return cars.stream()
                .filter(d -> d.getModel().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<Car> getAll() {
        return this.cars;
    }

    @Override
    public void add(Car model) {
        boolean result = this.cars.stream()
                .noneMatch(c -> c.getModel().equals(model.getModel()));

        if(result) {
            this.cars.add(model);
        }
    }

    @Override
    public boolean remove(Car model) {
        return this.cars.remove(model);
    }
}
