package agh.cs.project;

public interface IPositionChangedPublisher {
    void addObserver(IPositionChangeObserver observer);
    //Adding an observr to the list of observers

    void removeObserver(IPositionChangeObserver observer);
    //removing an observer from the list of obervers
}
