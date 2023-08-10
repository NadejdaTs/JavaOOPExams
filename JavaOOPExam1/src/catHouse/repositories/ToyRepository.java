package catHouse.repositories;

import catHouse.entities.toys.Toy;

import java.util.Collection;

public class ToyRepository implements Repository{
    private Collection<Toy> toys;

    @Override
    public void buyToy(Toy toy) {
        this.toys.add(toy);
    }

    @Override
    public boolean removeToy(Toy toy) {
        if(toys.contains(toy)){
            toys.remove(toy);
            return true;
        }
        return false;
    }

    @Override
    public Toy findFirst(String type) {
        return toys.stream()
                .filter(t -> t.getClass().getSimpleName().equals(type))
                .findFirst()
                .orElse(null);
    }
}
