package christmasRaces.core.interfaces;

import christmasRaces.entities.cars.BaseCar;
import christmasRaces.entities.cars.Car;
import christmasRaces.entities.cars.MuscleCar;
import christmasRaces.entities.cars.SportsCar;
import christmasRaces.entities.drivers.Driver;
import christmasRaces.entities.drivers.DriverImpl;
import christmasRaces.entities.races.Race;
import christmasRaces.entities.races.RaceImpl;
import christmasRaces.repositories.interfaces.CarRepository;
import christmasRaces.repositories.interfaces.DriverRepository;
import christmasRaces.repositories.interfaces.RaceRepository;
import christmasRaces.repositories.interfaces.Repository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static christmasRaces.common.ExceptionMessages.*;
import static christmasRaces.common.OutputMessages.*;

public class ControllerImpl implements Controller{
    private DriverRepository driverRepository;
    private CarRepository carRepository;
    private RaceRepository raceRepository;

    public ControllerImpl(Repository<Driver> driverRepository, Repository<Car> carRepository, Repository<Race> raceRepository){
        this.driverRepository = new DriverRepository();
        this.carRepository = new CarRepository();
        this.raceRepository = new RaceRepository();
    }

    @Override
    public String createDriver(String driverName) {
        if(driverRepository.getByName(driverName) != null){
            throw new IllegalArgumentException(String.format(DRIVER_EXISTS, driverName));
        }
        Driver driver = new DriverImpl(driverName);
        driverRepository.add(driver);
        return String.format(DRIVER_CREATED, driverName);
    }

    @Override
    public String createCar(String type, String model, int horsePower) {
        //Car car;
        Car byName = carRepository.getByName(model);
        BaseCar car;

        if(byName != null){
            throw new IllegalArgumentException(String.format(CAR_EXISTS, model));
        }
        if(type.equals("Muscle")){
            car = new MuscleCar(model, horsePower);
            this.carRepository.add(car);
        }else if (type.equals("Sports")){
            car = new SportsCar(model, horsePower);
            this.carRepository.add(car);
        }

        return String.format(CAR_CREATED, type + "Car", model);
    }

    @Override
    public String addCarToDriver(String driverName, String carModel) {
        Driver driver = driverRepository.getByName(driverName);
        if(driver == null){
            throw new IllegalArgumentException(String.format(DRIVER_NOT_FOUND, driverName));
        }
        Car car = carRepository.getAll().stream()
                .filter(c -> c.getModel().equals(carModel))
                .findFirst()
                .orElse(null);

        if(car == null){
            throw new IllegalArgumentException(String.format(CAR_NOT_FOUND, carModel));
        }
        driver.addCar(car);
        this.driverRepository.add(driver);
        this.carRepository.add(car);

        return String.format(CAR_ADDED, driverName, carModel);
    }

    @Override
    public String addDriverToRace(String raceName, String driverName) {
        Race race = this.raceRepository.getByName(raceName);
        if(race == null){
            throw new IllegalArgumentException(String.format(RACE_NOT_FOUND, raceName));
        }
        Driver driver = this.driverRepository.getByName(driverName);
        if(driver == null){
            throw new IllegalArgumentException(String.format(DRIVER_NOT_FOUND, driverName));
        }

        race.addDriver(driver);
        driverRepository.add(driver);
        raceRepository.add(race);
        return String.format(DRIVER_ADDED, driverName, raceName);
    }

    @Override
    public String startRace(String raceName) {
        if(this.raceRepository.getByName(raceName) == null){
            throw new IllegalArgumentException(String.format(RACE_NOT_FOUND, raceName));
        }
        if(this.raceRepository.getByName(raceName).getDrivers().size() < 3){
            throw new IllegalArgumentException(String.format(RACE_INVALID, raceName, 3));
        }


        Race race = raceRepository.getAll().stream()
                .filter(r -> r.getName().equals(raceName))
                .findFirst()
                .orElse(null);

        int laps = race.getLaps();
        Map<Double, Driver> winDrivers = new TreeMap<>();
        List<Double> doubles = new ArrayList<>();
        for (Driver d : race.getDrivers()) {
            double lapsOfEveryPerson = d.getCar().calculateRacePoints(laps);
            //d.getNumberOfWins();
            winDrivers.put(lapsOfEveryPerson, d);
            doubles.add(lapsOfEveryPerson);
        }

        Collections.sort(doubles, Comparator.reverseOrder());

        StringBuilder sb = new StringBuilder();
        String position = "wins";
        int cnt = 0;
        /*for (int i = 0; i < 3; i++) {
            if(i == 1) {
                position = DRIVER_FIRST_POSITION;
            }else if(i == 2){
                position = DRIVER_SECOND_POSITION;
            }else {
                position = DRIVER_THIRD_POSITION;
            }
            sb.append(String.format("Driver %s %s %s race.%n", position, winDrivers.get(i), position, raceName));
        }*/
        /*winDrivers.keySet().stream();
        winDrivers.keySet().stream().sorted((d1, d2) -> Double.compare(d2, d1))
                .forEach(k -> winDrivers.values().stream()
                        .forEach(e -> {
                            int cnt = 0;
                            String position = "wins";
                            if(cnt == 1){
                                position = "is second in";
                            }else if(cnt == 2){
                                position = "is third in";
                            }
                            sb.append(String.format("Driver %s %s %s race.%n", e.getName(), position, raceName));
                            cnt++;
                        }));*/
        /*for (int i = 0; i < 3; i++) {
            if(i == 1){
                position = "is second in";
            }else if(i == 2){
                position = "is third in";
            }
            sb.append(String.format("Driver %s %s %s race.%n", winDrivers, position, raceName));
        }*/
        for (var winDriver : winDrivers.values()) {
            if(cnt == 1){
                position = "is second in";
            }else if(cnt == 2){
                position = "is third in";
            }
            sb.append(String.format("Driver %s %s %s race.%n", winDriver.getName(), position, raceName));
            cnt++;
        }

        raceRepository.remove(race);

        return sb.toString().trim();
    }

    @Override
    public String createRace(String name, int laps) {
        Race race = new RaceImpl(name ,laps);
        if(this.raceRepository.getByName(name) != null) {
            throw new IllegalArgumentException(String.format(RACE_EXISTS, name));
        }
        raceRepository.add(race);
        return String.format(RACE_CREATED, name);
    }
}
