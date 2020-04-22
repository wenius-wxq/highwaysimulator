public interface CarObserable {
    void registerObserver(CarPositionObserver positionObserver);
    void removeObserver(CarPositionObserver positionObserver);
    void notifyPositionObservers();
    void registerObserver(CarPassengerObserver passengerObserver);
    void removeObserver(CarPassengerObserver passengerObserver);
    void notifyPassengerObservers();
}
