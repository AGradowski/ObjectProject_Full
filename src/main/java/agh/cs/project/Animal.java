package agh.cs.project;

import java.util.ArrayList;
import java.util.List;

public class Animal implements IPositionChangedPublisher
{
    private MapDirection orientation;
    private Vector2d position;
    private float energy;
    private final DNA dna;
    private final WorldMap map;

    private int days=1;
    private int children=0;

    private final List<IPositionChangeObserver> observers = new ArrayList<>();

    public Animal(WorldMap map,MapDirection orient, Vector2d pos, float energy, DNA dna )
    {
        this.map = map;
        orientation = orient;
        position = pos;
        this.energy = energy;
        this.dna = dna;
        addObserver(map);
        addedNewPosition(position);

    }

    public void move(float move_energy)
    {

        int rot = dna.getRotationNumber();

        for(int i =0 ; i<rot; i++)
        {
            orientation = orientation.next();
        }
        Vector2d npos = map.nextMove(position.add(orientation.toUnitVector()));
        positionChanged(position,npos);
        position = map.nextMove(npos);//?
        deplete_energy(move_energy);

    }

    public void deplete_energy(float dep_energy)
    {
        energy-=dep_energy;
    }

    public void eat(float given_plant_energy)
    {
        energy += given_plant_energy;
    }

    public Vector2d getPosition() {//this function helps me with tests
        return position;
    }

    public float getEnergy() {        return energy;    }

    public MapDirection getOrientation() {// this function helps me with tests
        return orientation;
    }

    public int getDnaDigit(int i) {
        return dna.get(i);
    }

    @Override
    public void addObserver(IPositionChangeObserver observer)
    {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IPositionChangeObserver observer)
    {
        observers.remove(observer);
    }

    private void positionChanged(Vector2d oldPosition, Vector2d newPosition)
    {
        for(IPositionChangeObserver obs:observers)
        {
            obs.positionChanged(oldPosition, newPosition,this);
        }
    }

    private void addedNewPosition(Vector2d newPosition)
    {
        for(IPositionChangeObserver obs:observers)
        {
            obs.addedNewPosition(newPosition,this);
        }
    }

    public int getDays() {
        return days;
    }

    public DNA getDna() {
        return dna;
    }

    public void grow_older()
    {
        days+=1;
    }

    public void addChild()
    {
        children+=1;
    }
    public int getNumChildren()
    {
        return children;
    }

}
