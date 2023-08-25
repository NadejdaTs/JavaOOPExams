package christmasRaces.entities.races;

import christmasRaces.entities.cars.Car;
import christmasRaces.entities.drivers.Driver;
import christmasRaces.entities.drivers.DriverImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static christmasRaces.common.ExceptionMessages.*;

public class RaceImpl implements Race{
    private String name;
    private int laps;
    private List<Driver> drivers;
    //private List<Race> races;
    private static final int LENGTH = 5;
    private static final int LENGTH_LAPS = 1;

    public RaceImpl(String name, int laps){
        setName(name);
        setLaps(laps);
        this.drivers = new ArrayList<>();
    }

    private void setLaps(int laps) {
        if(laps < LENGTH_LAPS){
            throw new IllegalArgumentException(String.format(INVALID_NUMBER_OF_LAPS, LENGTH_LAPS));
        }
        this.laps = laps;
    }

    private void setName(String name) {
        if(name == null || name .trim().isEmpty() || name.length() < LENGTH){
            throw new IllegalArgumentException(String.format(INVALID_NAME, LENGTH));
        }
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getLaps() {
        return this.laps;
    }

    @Override
    public Collection<Driver> getDrivers() {
        return this.drivers;
    }

    @Override
    public void addDriver(Driver driver) {
        boolean result = false;
        for (Driver d : drivers) {
            if(d.getName().equals(driver.getName())){
                result = true;
            }
        }

        if(driver == null){
            throw new IllegalArgumentException(DRIVER_INVALID);
        }else if(!driver.getCanParticipate()){
            throw new IllegalArgumentException(String.format(DRIVER_NOT_PARTICIPATE, driver.getName()));
        }else if(!this.drivers.isEmpty() && result){
            throw new IllegalArgumentException(String.format(DRIVER_ALREADY_ADDED, driver.getName(), "I don`t know the name of the race"));
        }

        drivers.add(driver);

        //how to add it in race
        //race.add(this.name, this.laps);
    }
}
