package catHouse.core;

import catHouse.entities.cat.BaseCat;
import catHouse.entities.cat.Cat;
import catHouse.entities.cat.LonghairCat;
import catHouse.entities.cat.ShorthairCat;
import catHouse.entities.houses.BaseHouse;
import catHouse.entities.houses.House;
import catHouse.entities.houses.LongHouse;
import catHouse.entities.houses.ShortHouse;
import catHouse.entities.toys.Ball;
import catHouse.entities.toys.BaseToy;
import catHouse.entities.toys.Mouse;
import catHouse.entities.toys.Toy;
import catHouse.repositories.ToyRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static catHouse.common.ConstantMessages.*;
import static catHouse.common.ExceptionMessages.*;

public class ControllerImpl implements Controller{
    private List<Toy> toys;
    private List<House> houses;
    //private List<Cat> cats;

    public ControllerImpl(){
        this.toys = new ArrayList<>();
        this.houses = new ArrayList<>();
        //this.cats = new ArrayList<>();
    }

    @Override
    public String addHouse(String type, String name) {
        House house;
        if(type.equals("ShortHouse")){
            house = new ShortHouse(name);
        }else if(type.equals("LongHouse")){
            house = new LongHouse(name);
        }else{
            throw new IllegalArgumentException(INVALID_HOUSE_TYPE);
        }
        houses.add(house);
        return String.format(SUCCESSFULLY_ADDED_HOUSE_TYPE, type);
    }

    @Override
    public String buyToy(String type) {
        BaseToy toy;
        if(type.equals("Ball")){
            toy = new Ball();
        }else if(type.equals("Mouse")){
            toy = new Mouse();
        }else{
            throw new IllegalArgumentException(INVALID_TOY_TYPE);
        }
        for (House house : houses) {
            house.buyToy(toy);
            //this.toys.add(toy);
        }

        this.toys.add(toy);
        return String.format(SUCCESSFULLY_ADDED_TOY_TYPE, type);
    }

    @Override
    public String toyForHouse(String houseName, String toyType) {
        BaseToy toy = null;
        if(toyType.equals("Ball")){
            toy = new Ball();
        }else if(toyType.equals("Mouse")) {
            toy = new Mouse();
        }
        if(toy == null){
            throw new IllegalArgumentException(String.format(NO_TOY_FOUND, toyType));
        }

        for (House house : houses) {
            if(house.getName().equals(houseName)){
                //house.buyToy(toy);
                toys.remove(toy);
                break;
            }
        }
        return String.format(SUCCESSFULLY_ADDED_TOY_IN_HOUSE, toyType, houseName);
    }

    @Override
    public String addCat(String houseName, String catType, String catName, String catBreed, double price) {
        BaseCat cat;
        if(catType.equals("ShorthairCat")){
            cat = new ShorthairCat(catName, catBreed, price);
        }else if(catType.equals("LonghairCat")){
            cat = new LonghairCat(catName, catBreed, price);
        }else{
            throw new IllegalArgumentException(INVALID_CAT_TYPE);
        }

        House house = null;
        for (House h : houses) {
            if(h.getName().equals(houseName)){
                house = h;
                break;
            }
        }

        String catStr = catType.substring(0,5);
        String houseStr = house.getClass().getSimpleName().substring(0, 5);
        if(!catStr.equals(houseStr)){
            throw new IllegalArgumentException(UNSUITABLE_HOUSE);
        }
        house.addCat(cat);
        return String.format(SUCCESSFULLY_ADDED_CAT_IN_HOUSE, catType, houseName);
    }

    @Override
    public String feedingCat(String houseName) {
        int feededCats = 0;
        for (House house : houses) {
            String test = house.getName();
            if(house.getName().equals(houseName)) {
                house.feeding();
                feededCats = house.getCats().size();
                break;
            }
        }
        return String.format(FEEDING_CAT, feededCats);
    }

    @Override
    public String sumOfAll(String houseName) {
        List<Collection<Cat>> cats = this.houses.stream()
                .filter(h -> h.getName().equals(houseName))
                .map(House::getCats)
                .collect(Collectors.toList());
        double catPrice = 0;
        for (Collection<Cat> cat : cats) {
            for (Cat c : cat) {
                catPrice += c.getPrice();
            }
        }

        double toyPrice = 0;
        for (Toy toy : toys) {
            toyPrice += toy.getPrice();
        }

        return String.format(VALUE_HOUSE, houseName, catPrice + toyPrice);
    }

    @Override
    public String getStatistics() {
        /*return this.houses.stream()
                .map(House::getStatistics)
                .collect(Collectors.joining(System.lineSeparator()));*/
        StringBuilder sb = new StringBuilder();
        for (House house : houses) {
            sb.append(house.getStatistics()).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
