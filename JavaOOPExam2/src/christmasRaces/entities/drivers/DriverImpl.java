package christmasRaces.entities.drivers;

import christmasRaces.entities.cars.BaseCar;
import christmasRaces.entities.cars.Car;

import java.util.ArrayList;
import java.util.List;

import static christmasRaces.common.ExceptionMessages.CAR_INVALID;
import static christmasRaces.common.ExceptionMessages.INVALID_NAME;

public class DriverImpl implements Driver{
    private String name;
    private Car car;
    private int numberOfWins;
    private boolean canParticipate;
    private static final int LENGTH = 5;
    //private List<Car> cars;

    public DriverImpl(String name){
        setName(name);
        //addCar(Car car);
        this.numberOfWins = numberOfWins;
        this.canParticipate = getCanParticipate();
    }

    private void setName(String name) {
        if(name == null || name .trim().isEmpty() || name.length() < LENGTH){
            throw new IllegalArgumentException(String.format(INVALID_NAME, name, LENGTH));
        }
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Car getCar() {
        return this.car;
    }

    @Override
    public int getNumberOfWins() {
        return this.numberOfWins;
    }

    //test
    public void setNumberOfWins(int numberOfWins) {
        this.numberOfWins = numberOfWins;
    }

    @Override
    public void addCar(Car car) {
        if(car == null){
            throw new IllegalArgumentException(CAR_INVALID);
        }
        this.car = car;
    }

    @Override
    public void winRace() {
        this.numberOfWins++;
    }

    @Override
    public boolean getCanParticipate() {
        if(this.car != null){
            return true;
        }
        return false;
    }
}
