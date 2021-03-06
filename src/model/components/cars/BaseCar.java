package model.components.cars;

import enumerates.CarDirection;
import exceptions.LocationErrorException;
import model.components.highway.CarPositionObserver;
import model.components.highway.CarTrack;
import model.components.passengers.CarInStationObserver;
import model.components.passengers.CarPassengerObserver;
import model.components.passengers.Passenger;

import java.util.ArrayList;



/**
 * @author wangmengxi
 * It's a super class of all kinds of cars
 *
 * It includes the basic imformation of the Car.
 * Also, it is observable for CarPositionObserver, CarPassengerObserver, CarInStationObserver.
 *
 * However, it does not comtain any position information of a car.
 *
 */
public abstract class BaseCar implements CarObservable {
    protected int ID;
    private CarTrack track;
    private final ArrayList<CarPositionObserver> positionObservers = new ArrayList<>();
    private final ArrayList<CarPassengerObserver> passengerObservers = new ArrayList<>();
    private final ArrayList<CarInStationObserver> inStationObservers = new ArrayList<>();
    private final ArrayList<CarInStationObserver> removedInStationObservers = new ArrayList<>();
    private double speed = 0;
    private final ArrayList<Passenger> passengers = new ArrayList<>();

    protected double MAX_SPEED = 0;
    protected int MAX_PASSENGERS = 0;
    protected long PULL_OFF_TIME = 0;

    public BaseCar(CarTrack track) {
        this.track = track;
    }

    public final int getID() {
        return ID;
    }

    public final double getMaxSpeed() {
        return MAX_SPEED / 60000;
    }

    public final int getMaxPassengers() {
        return MAX_PASSENGERS;
    }

    public final long getPullOffTime() {
        return PULL_OFF_TIME;
    }

    public CarTrack getTrack() {
        return track;
    }

    public void setTrack(CarTrack track) {
        this.track = track;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getNumberOfPassengers() {
        return passengers.size();
    }

    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
        registerObserver(passenger);
        notifyPassengerObservers();
    }

    public void removePassenger(Passenger passenger) {
        passengers.remove(passenger);
        removedInStationObservers.add(passenger);
    }

    public void clearRemovedObservers() {
        for (CarInStationObserver inStationObserver : removedInStationObservers) {
            inStationObservers.remove(inStationObserver);
        }

        removedInStationObservers.clear();
    }

    @Override
    public final void registerObserver(CarPositionObserver positionObserver) {
        positionObservers.add(positionObserver);
    }

    @Override
    public final void removeObserver(CarPositionObserver positionObserver) {
        positionObservers.remove(positionObserver);
    }

    @Override
    public final void notifyPositionObservers(double location, CarDirection direction) throws LocationErrorException {
        for (CarPositionObserver positionObserver : positionObservers) {
            positionObserver.updateCarPosition(this, location, direction);
        }
    }

    @Override
    public final void registerObserver(CarPassengerObserver passengerObserver) {
        passengerObservers.add(passengerObserver);
    }

    @Override
    public final void removeObserver(CarPassengerObserver passengerObserver) {
        passengerObservers.remove(passengerObserver);
    }

    @Override
    public final void notifyPassengerObservers() {
        for (CarPassengerObserver passengerObserver : passengerObservers) {
            passengerObserver.updateCarPassenger(this);
        }
    }

    @Override
    public void registerObserver(CarInStationObserver inStationObserver) {
        inStationObservers.add(inStationObserver);
    }

    @Override
    public void removeObserver(CarInStationObserver inStationObserver) {
        inStationObservers.remove(inStationObserver);
    }

    @Override
    public void notifyCarInStationObservers(String carStation) {
        for (CarInStationObserver inStationObserver : inStationObservers) {
            inStationObserver.updateCarInStation(this, carStation);
        }
        clearRemovedObservers();
        notifyPassengerObservers();
    }

    @Override
    public int hashCode() {
        return getID();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BaseCar) {
            return getID() == ((BaseCar) obj).getID();
        }
        return false;
    }
}


