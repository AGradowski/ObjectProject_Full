package agh.cs.project;

import java.util.Random;
import javafx.application.Platform;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.Group;
import javafx.scene.shape.*;

public class Simulation
{
    private WorldMap map;
    private Text numberOfPlants;
    private Text numberOfAnimals;
    private Text AvgEnergy;
    private Text AvgLife;
    private Text AvgChild;


    public Simulation(int width, int height, float jungle_ratio,float start_energy, float move_energy,float plant_energy, int number_of_animals)
    {
        Random r = new Random();
        map = new WorldMap(width,height,jungle_ratio,start_energy,move_energy,plant_energy);
        for(int i =0; i<number_of_animals;i++)
        {
            int x = r.nextInt(width);
            int y = r.nextInt(height);
            Animal n = new Animal(map,MapDirection.random(),new Vector2d(x,y),start_energy,new DNA());
        }
    }

    public void nextDay()
    {
        map.cleanDeadBodies();
        map.run();
        map.eat();
        map.mate();
        map.growNewPlants();
        map.grow_animals();

    }

    public WorldMap getMap() {
        return map;
    }

    public Group getButtons(int transX, int transY,int scale)
    {
        numberOfPlants = new Text("Number of plants = "+map.numOfPlants);
        numberOfPlants.setX(transX);
        numberOfPlants.setY(transY+map.getHeight()*scale+3*scale);
        numberOfPlants.setFont(new Font(20));
        numberOfAnimals = new Text("Number of animals alive = "+map.getNumberOfAnimals());
        numberOfAnimals.setX(transX);
        numberOfAnimals.setY(transY+map.getHeight()*scale+5*scale);
        numberOfAnimals.setFont(new Font(20));
        AvgEnergy = new Text("Average alive animal energy= "+map.getAvgEnergy());
        AvgEnergy.setX(transX);
        AvgEnergy.setY(transY+map.getHeight()*scale+7*scale);
        AvgEnergy.setFont(new Font(20));
        AvgLife = new Text("Average dead animal lifespan= "+map.getAvgLifeSpan());
        AvgLife.setX(transX);
        AvgLife.setY(transY+map.getHeight()*scale+9*scale);
        AvgLife.setFont(new Font(20));
        AvgChild = new Text("Average number of children = "+map.getAvgChildren());
        AvgChild.setX(transX);
        AvgChild.setY(transY+map.getHeight()*scale+11*scale);
        AvgChild.setFont(new Font(20));
        Group buttons =new Group(numberOfPlants,numberOfAnimals,AvgEnergy,AvgLife,AvgChild);

        return buttons;
    }
    public Animal nextAnimal()
    {
        if(!SimulationStatus.running) {
            return map.chooseNextAnimal();
        }
        return null;
    }

   public Animal previousAnimal()
    {
        if(!SimulationStatus.running) {
            return map.choosePrevAnimal();
        }
        return null;
    }

}
