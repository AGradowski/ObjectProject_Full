package agh.cs.project;

public interface IPositionChangeObserver {
    void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal);

    void addedNewPosition(Vector2d newPosition, Animal animal);
}
