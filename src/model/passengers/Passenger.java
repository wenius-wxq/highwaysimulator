package model.passengers;

import model.cars.BaseCar;
import model.highway.CarTrack;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * Passenger is to present the passenger in our simulation.
 * <p>
 * it contains two features that indicate the boarding station and station that it gets off.
 * it's constructor calculates the station it gets off in a average probability
 */
public class Passenger implements CarInStationObserver {
    private String boardingStation;
    private String destination;
    private TreeMap<Double, String> stationsDistributions;

    public Passenger(String boardingStation, Double location, CarTrack track) {
        this.boardingStation = boardingStation;
        stationsDistributions = track.getStationsDistributions();
        int sumOfDistances = 0;
        double lengthOfTrack = 2 * (stationsDistributions.lastKey() - stationsDistributions.firstKey());
        for (Map.Entry<Double, String> station : stationsDistributions.entrySet()) {
            if (!station.getValue().equals(boardingStation)) {
                sumOfDistances += (lengthOfTrack - Math.abs(location - station.getKey())) * 100;
            }
        }

        int randomValue = (new Random()).nextInt(sumOfDistances);
        sumOfDistances = 0;
        for (Map.Entry<Double, String> station : stationsDistributions.entrySet()) {
            if (!station.getValue().equals(boardingStation)) {
                sumOfDistances += (lengthOfTrack - Math.abs(location - station.getKey())) * 100;
                if (randomValue < sumOfDistances) {
                    destination = station.getValue();
                    break;
                }
            }
        }
    }

    public String getDestination() {
        return destination;
    }

    @Override
    public void updateCarInStation(BaseCar car, String carStation) {
        if (carStation.equals(destination)) {
            car.removePassenger(this);
        }
    }
}
