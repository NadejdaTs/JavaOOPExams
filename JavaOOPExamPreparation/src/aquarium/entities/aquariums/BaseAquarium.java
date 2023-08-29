package aquarium.entities.aquariums;

import aquarium.entities.decorations.BaseDecoration;
import  aquarium.entities.decorations.Decoration;
import aquarium.entities.fish.Fish;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static aquarium.common.ConstantMessages.*;
import static aquarium.common.ExceptionMessages.AQUARIUM_NAME_NULL_OR_EMPTY;

public abstract class BaseAquarium implements Aquarium{
    private String name;
    private int capacity;
    private List<Decoration> decorations;
    private List<Fish> fishes;

    protected BaseAquarium(String name, int capacity){
        setName(name);
        this.capacity = capacity;
        this.decorations = new ArrayList<>();
        this.fishes = new ArrayList<>();
    }

    private void setName(String name) {
        if(name == null || name.trim().isEmpty()){
            throw new NullPointerException(AQUARIUM_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    @Override
    public int calculateComfort() {
        return this.decorations.stream()
                .mapToInt(Decoration::getComfort)
                .sum();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void addFish(Fish fish) {
        if(capacity == this.fishes.size()){
            throw new IllegalStateException(NOT_ENOUGH_CAPACITY);
        }

        String fishWaterType = fish.getClass().getSimpleName().replaceAll("Fish", "");
        if(!this.getClass().getSimpleName().contains(fishWaterType)){
            throw new IllegalArgumentException(WATER_NOT_SUITABLE);
        }

        this.fishes.add(fish);
    }

    @Override
    public void removeFish(Fish fish) {
        this.fishes.remove(fish);
    }

    @Override
    public void addDecoration(Decoration decoration) {
        this.decorations.add(decoration);
    }

    @Override
    public void feed() {
        for (Fish fish : fishes) {
            fish.eat();
        }
    }

    @Override
    public String getInfo() {
        String fishesOutput = fishes.isEmpty()
                ? "none"
                : fishes.stream()
                .map(Fish::getName)
                .collect(Collectors.joining(" "));

        return String.format("%s (%s):%n" +
                "Fish: %s%n" +
                "Decorations: %d%n" +
                "Comfort: %d", this.name, this.getClass().getSimpleName(), fishesOutput, this.decorations.size(), this.calculateComfort());
    }

    @Override
    public Collection<Fish> getFish() {
        return fishes;
    }

    @Override
    public Collection<Decoration> getDecorations() {
        return decorations;
    }
}
