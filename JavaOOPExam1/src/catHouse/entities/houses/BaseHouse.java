package catHouse.entities.houses;

import catHouse.entities.cat.Cat;
import catHouse.entities.toys.Toy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static catHouse.common.ConstantMessages.*;
import static catHouse.common.ExceptionMessages.*;

public abstract class BaseHouse implements House{
    private String name;
    private int capacity;
    private List<Toy> toys;
    private List<Cat> cats;

    protected BaseHouse(String name, int capacity){
        setName(name);
        this.capacity = capacity;
        this.toys = new ArrayList<>();
        this.cats = new ArrayList<>();
    }

    @Override
    public int sumSoftness() {
        int sum = 0;
        for (Toy toy : toys) {
            sum += toy.getSoftness();
        }
        return sum;
    }

    @Override
    public void addCat(Cat cat) {
        if(cats.size() >= this.capacity){
            throw new IllegalArgumentException(NOT_ENOUGH_CAPACITY_FOR_CAT);
        }
        this.cats.add(cat);
    }

    @Override
    public void removeCat(Cat cat) {
        cats.remove(cat);
    }

    @Override
    public void buyToy(Toy toy) {
        this.toys.add(toy);
    }

    @Override
    public void feeding() {
        for (Cat cat : cats) {
            cat.eating();
        }
    }

    @Override
    public String getStatistics() {
        String catOutput = cats.isEmpty()
            ? "none"
            : cats.stream()
                .map(Cat::getName)
                .collect(Collectors.joining(" "));

        /*House house;
        if(this.getClass().getSimpleName().equals("LongHouse")){
            house = new LongHouse(this.name);
        }else{
            house = new ShortHouse(this.name);
        }
        int test = house.getToys().size(); //this.toys.size()*/

        return String.format("%s %s:%n" +
                "Cats: %s%n" +
                "Toys: %d Softness: %d", this.name, this.getClass().getSimpleName(), catOutput, this.getToys().size(), sumSoftness());
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        if(name == null || name.trim().isEmpty()){
            throw new NullPointerException(HOUSE_NAME_CANNOT_BE_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    @Override
    public List<Cat> getCats() {
        return this.cats;
    }

    @Override
    public List<Toy> getToys() {
        return this.toys;
    }
}
