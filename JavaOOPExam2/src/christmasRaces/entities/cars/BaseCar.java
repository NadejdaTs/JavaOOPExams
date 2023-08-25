package christmasRaces.entities.cars;

import static christmasRaces.common.ExceptionMessages.INVALID_MODEL;

public abstract class BaseCar implements Car{
    private String model;
    protected int horsePower;
    protected double cubicCentimeters;
    private static final int LENGTH = 4;

    protected BaseCar(String model, int horsePower, double cubicCentimeters){
        setModel(model);
        setHorsePower(horsePower);
        this.cubicCentimeters = cubicCentimeters;
    }

    private void setModel(String model) {
        if(model == null || model.trim().isEmpty() || model.length() < LENGTH){
            throw new IllegalArgumentException(String.format(INVALID_MODEL, model, LENGTH));
        }
        this.model = model;
    }

    protected abstract void setHorsePower(int horsePower);

    @Override
    public String getModel() {
        return this.model;
    }

    @Override
    public int getHorsePower() {
        return this.horsePower;
    }

    @Override
    public double getCubicCentimeters() {
        return this.cubicCentimeters;
    }

    @Override
    public double calculateRacePoints(int laps) {
        return this.cubicCentimeters / horsePower * laps;
    }
}
